# Guía de Despliegue — RegistraduriaBack

Este documento describe **todo lo necesario** para desplegar RegistraduriaBack sin problemas, incluyendo los errores conocidos que ya ocurrieron y cómo resolverlos.

> **RegistraduriaBack** es una aplicación Spring Boot que corre en el VM de backends (`10.43.100.131`) en el **puerto 8080**.

---

## Infraestructura del Sistema

| Servicio               | VM Producción    | Puerto |
|------------------------|------------------|--------|
| RegistraduriaFront     | `10.43.97.237`   | `8090` |
| VotacionFront          | `10.43.97.237`   | `4201` |
| **RegistraduriaBack**  | `10.43.100.131`  | `8080` |
| VotacionBack           | `10.43.100.131`  | `8081` |
| PostgreSQL             | `10.43.101.13`   | `5432` |
| CouchDB                | `10.43.101.13`   | `5984` |

---

## Ejecución Local

> Para probar el backend sin acceso a los VMs de producción.

### Opción A — Full Docker con `docker-compose.yml` raíz (recomendado)

El `docker-compose.yml` de la **raíz del workspace** levanta toda la infraestructura local:

```bash
# Desde la carpeta raíz ("Arquitectura de Software")
docker compose up -d
```

Este backend queda disponible en **`http://localhost:8082`** (el compose raíz mapea el puerto interno 8080 al externo 8082 para evitar conflictos).

Las credenciales de BD usadas por el compose raíz son:

```
DB_URL:      jdbc:postgresql://postgres:5432/vote4tech
DB_USER:     postgres
DB_PASSWORD: postgres123
CORS_ALLOWED_ORIGINS: http://localhost:4200,http://localhost:4201
```

> Distintas a producción. Spring Boot con `ddl-auto=update` crea el schema automáticamente al iniciar. El `SeedConfig` puebla las tablas si están vacías.

Para ver los logs:

```bash
docker compose logs -f registraduria-back
```

Para reconstruir solo este servicio (si hubo cambios de código Java):

```bash
docker compose up -d --build registraduria-back
```

---

### Opción B — `mvn spring-boot:run` directo (para desarrollo Java)

Útil cuando se quiere ejecutar el backend directamente con Maven para usar el debugger de IDE o ver stack traces sin Docker.

**Paso 1 — Levantar solo la base de datos:**

```bash
# Desde la carpeta raíz del workspace
docker compose up -d postgres
```

PostgreSQL queda en `localhost:5432`, DB: `vote4tech`, user: `postgres`, pass: `postgres123`.

**Paso 2 — Exportar variables de entorno y arrancar:**

En Linux/Mac:

```bash
cd Vote4TechRegistraduriaBack
export DB_URL="jdbc:postgresql://localhost:5432/vote4tech"
export DB_USER="postgres"
export DB_PASSWORD="postgres123"
export CORS_ALLOWED_ORIGINS="http://localhost:4200"
mvn spring-boot:run
```

En Windows (PowerShell):

```powershell
cd Vote4TechRegistraduriaBack
$env:DB_URL = "jdbc:postgresql://localhost:5432/vote4tech"
$env:DB_USER = "postgres"
$env:DB_PASSWORD = "postgres123"
$env:CORS_ALLOWED_ORIGINS = "http://localhost:4200"
mvn spring-boot:run
```

El backend arranca en `http://localhost:8080` (puerto por defecto de `application.properties`).

> ⚠ Si usas el backend local con `ng serve` de RegistraduriaFront, ajusta el proxy:
> - Con `mvn spring-boot:run` → el backend está en **8080** → usa `proxy.conf.local.json` cambiando `target` a `http://localhost:8080`
> - Con `docker compose up` → el backend está en **8082** → usa `proxy.conf.local.json` tal como está

**Paso 3 — Verificar que arrancó correctamente:**

```bash
curl http://localhost:8080/eleccion/elecciones
# Debe devolver un array JSON (puede estar vacío si el seed aún no corrió)
```

---

## Variables de Entorno Críticas

El archivo `docker/docker-compose.prod.yml` contiene todas las variables de entorno. Las más importantes:

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | URL de PostgreSQL | `jdbc:postgresql://10.43.101.13:5432/registraduria` |
| `SPRING_DATASOURCE_USERNAME` | Usuario de BD | (ver archivo) |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de BD | (ver archivo) |
| `CORS_ALLOWED_ORIGINS` | Orígenes permitidos | `http://10.43.97.237:8090,https://xxxx.trycloudflare.com` |
| `JWT_SECRET` | Clave para firmar JWT | (ver archivo) |

> **`CORS_ALLOWED_ORIGINS` debe incluir siempre:**
> 1. `http://10.43.97.237:8090` — acceso directo por IP (para desarrollo y QA)
> 2. La URL de Cloudflare actual de RegistraduriaFront (para acceso externo)

---

## Despliegue Completo (Primera Vez)

### Prerequisitos

- PostgreSQL corriendo en `10.43.101.13:5432` con la base de datos `registraduria` creada y el schema inicializado.
- Acceso SSH al VM `10.43.100.131`.

### Paso 1 — Limpiar contenedores anteriores (VM `10.43.100.131`)

```bash
docker rm -f vote4tech-registraduria-back
```

### Paso 2 — Clonar el repositorio (VM `10.43.100.131`)

```bash
cd ~
git clone https://github.com/Zyntech-PUJ/Vote4TechRegistraduriaBack.git
```

Si la carpeta ya existe:

```bash
cd ~/Vote4TechRegistraduriaBack
git pull
```

### Paso 3 — Configurar variables de entorno

**Desde tu PC (PowerShell)**, editar `Vote4TechRegistraduriaBack\docker\docker-compose.prod.yml`:

```yaml
environment:
  SPRING_DATASOURCE_URL: "jdbc:postgresql://10.43.101.13:5432/registraduria"
  SPRING_DATASOURCE_USERNAME: "postgres"
  SPRING_DATASOURCE_PASSWORD: "tu_password"
  CORS_ALLOWED_ORIGINS: "http://10.43.97.237:8090,https://TU_URL_CLOUDFLARE.trycloudflare.com"
  JWT_SECRET: "tu_jwt_secret"
```

> La URL de Cloudflare de RegistraduriaFront se obtiene con:
> ```bash
> docker logs vote4tech-cloudflared 2>&1 | grep trycloudflare
> # (en el VM 10.43.97.237)
> ```

### Paso 4 — Copiar el docker-compose al VM (desde tu PC, PowerShell)

```powershell
$base = "C:\Users\javie\OneDrive\Documentos\unijaveriana\SEMESTRE 7\Arquitectura de Software\Vote4TechRegistraduriaBack"
scp "$base\docker\docker-compose.prod.yml" estudiante@10.43.100.131:~/Vote4TechRegistraduriaBack/docker/docker-compose.prod.yml
```

### Paso 5 — Construir y levantar (VM `10.43.100.131`)

**Primera vez o cuando hay cambios de código Java:**

```bash
cd ~/Vote4TechRegistraduriaBack
docker compose -f docker/docker-compose.prod.yml up -d --build
```

La primera vez tarda varios minutos (descarga Maven, compila). Para ver el progreso:

```bash
docker logs -f vote4tech-registraduria-back
```

Esperar hasta ver:
```
Started RegistraduriaBackApplication in X.XXX seconds
```

Verificar que está corriendo:

```bash
docker ps | grep registraduria-back
curl http://localhost:8080/eleccion/elecciones
```

---

## Actualizar solo Variables de Entorno (sin recompilar)

Cuando solo cambia el `CORS_ALLOWED_ORIGINS` u otra variable de entorno (no cambia código Java):

1. Editar `docker-compose.prod.yml` localmente
2. Subir con `scp`
3. En el VM:

```bash
cd ~/Vote4TechRegistraduriaBack
docker compose -f docker/docker-compose.prod.yml up -d
```

> **No hace falta `--build`** — Docker Compose simplemente recrea el contenedor con las nuevas variables sin recompilar.

---

## Actualizar con Nuevos Cambios de Código Java

Cuando hay cambios en el código Java (`.java`, `pom.xml`, etc.):

```bash
# En el VM 10.43.100.131
cd ~/Vote4TechRegistraduriaBack
git pull
docker compose -f docker/docker-compose.prod.yml up -d --build
```

> **`--build` es obligatorio** cuando hay cambios de código — Docker compila el proyecto Maven dentro del contenedor.

---

## Actualizar la URL de Cloudflare en CORS (flujo habitual)

Esta es la operación más frecuente. Ocurre cada vez que el contenedor de cloudflared de RegistraduriaFront se reinicia.

1. **Obtener la nueva URL** (VM `10.43.97.237`):
   ```bash
   docker logs vote4tech-cloudflared 2>&1 | grep trycloudflare
   ```

2. **Editar localmente** `Vote4TechRegistraduriaBack\docker\docker-compose.prod.yml`:
   ```yaml
   CORS_ALLOWED_ORIGINS: "http://10.43.97.237:8090,https://NUEVA_URL.trycloudflare.com"
   ```

3. **Subir al VM** (PowerShell local):
   ```powershell
   $base = "C:\Users\javie\OneDrive\Documentos\unijaveriana\SEMESTRE 7\Arquitectura de Software\Vote4TechRegistraduriaBack"
   scp "$base\docker\docker-compose.prod.yml" estudiante@10.43.100.131:~/Vote4TechRegistraduriaBack/docker/docker-compose.prod.yml
   ```

4. **Reiniciar contenedor** (VM `10.43.100.131`):
   ```bash
   cd ~/Vote4TechRegistraduriaBack
   docker compose -f docker/docker-compose.prod.yml up -d
   ```

---

## Problemas Conocidos y Soluciones

### Problema: Error CORS en el navegador desde Cloudflare URL

**Síntoma:** `Access to XMLHttpRequest ... has been blocked by CORS policy`.

**Causa:** La URL de Cloudflare de RegistraduriaFront cambió (el contenedor `vote4tech-cloudflared` fue reiniciado) y no se actualizó `CORS_ALLOWED_ORIGINS`.

**Solución:** Seguir el flujo de "Actualizar la URL de Cloudflare en CORS" descrito arriba.

**Nota importante:** El valor `http://10.43.97.237:8090` debe estar siempre en la lista, incluso cuando se agrega la URL de Cloudflare. Sin él, el acceso directo por IP al frontend también da CORS.

---

### Problema: Login devuelve 401 o 403 en vez de JWT

**Síntoma:** POST a `/registrador/login` devuelve 401 o 403 con body vacío.

**Causa posible 1:** Las credenciales no existen en la BD. Verificar con:

```bash
# Conectar a PostgreSQL en 10.43.101.13
psql -h 10.43.101.13 -U postgres -d registraduria -c "SELECT usuario FROM registrador;"
```

Credenciales de prueba que deben existir (creadas por el seed):
- Registrador: usuario `test123`, contraseña `12345`
- Admin Electoral: usuario `adminElectoral`, contraseña `admin2026`
- Consejo Nacional: usuario `consejoNacional`, contraseña `consejo2026`

**Causa posible 2:** JWT_SECRET no está configurado. Verificar en los logs:

```bash
docker logs vote4tech-registraduria-back | grep -i "jwt\|secret\|error" | tail -20
```

---

### Problema: Contenedor reinicia en bucle (`Restarting`)

**Síntoma:** `docker ps` muestra el contenedor con estado `Restarting`.

**Diagnóstico:**

```bash
docker logs vote4tech-registraduria-back --tail=30
```

**Causas comunes:**
- No puede conectar a PostgreSQL (`Connection refused` o `Unknown host`)
- Variable de entorno mal configurada (contraseña incorrecta, URL mal formada)
- Puerto 8080 ya ocupado por otro proceso

**Solución para cada caso:**

```bash
# Verificar conectividad a la BD
curl -v telnet://10.43.101.13:5432

# Ver si el puerto está ocupado
sudo lsof -i :8080

# Liberar el puerto si es necesario
sudo fuser -k 8080/tcp
```

---

### Problema: `git pull` sobreescribe el `docker-compose.prod.yml` con variables vacías

**Causa:** El archivo con las credenciales reales no está en el repositorio (está en `.gitignore` o tiene variables de template). El `git pull` restaura la versión template.

**Solución:** Siempre copiar el archivo correcto con `scp` **después** de hacer `git pull`, no antes.

Orden correcto:
1. `git pull`
2. `scp docker-compose.prod.yml` (copia el tuyo con las credenciales reales)
3. `docker compose up -d --build` (o sin `--build` si solo cambiaron variables)

---

### Problema: La app construye pero no responde en el puerto 8080

**Síntoma:** `docker ps` muestra el contenedor `Up`, pero `curl http://localhost:8080/...` da `Connection refused`.

**Causa:** Spring Boot todavía está arrancando. Puede tardar 30-60 segundos en estar listo.

**Verificar:**

```bash
docker logs vote4tech-registraduria-back | grep "Started"
# Debe aparecer: Started RegistraduriaBackApplication in X.XXX seconds
```

---

## Comandos de Diagnóstico Rápido

```bash
# Ver todos los contenedores
docker ps

# Ver logs del backend
docker logs vote4tech-registraduria-back --tail=50

# Ver logs en tiempo real
docker logs -f vote4tech-registraduria-back

# Test rápido del endpoint de elecciones (sin auth)
curl http://localhost:8080/eleccion/elecciones

# Test del endpoint de login
curl -X POST http://localhost:8080/registrador/login \
  -H "Content-Type: application/json" \
  -d '{"usuario":"test123","password":"12345"}'

# Ver variables de entorno del contenedor
docker inspect vote4tech-registraduria-back | grep -A 30 '"Env"'

# Reiniciar el contenedor (sin recompilar)
docker compose -f docker/docker-compose.prod.yml restart

# Reconstruir desde cero (con recompilación)
docker compose -f docker/docker-compose.prod.yml down
docker compose -f docker/docker-compose.prod.yml up -d --build
```

---

## Configuración del Dockerfile (multi-stage)

El `Dockerfile` tiene **2 etapas**. La primera compila el proyecto Java; la segunda crea la imagen final mínima.

```
┌─────────────────────────────────────────────────────────────┐
│  ETAPA 1: builder  (imagen maven:3.9-eclipse-temurin-21)    │
│                                                             │
│  1. COPY pom.xml .                                          │
│  2. RUN mvn dependency:go-offline  ← descarga deps Maven   │
│     (cacheado si pom.xml no cambió)                         │
│  3. COPY src ./src                 ← copia el código Java   │
│  4. RUN mvn package -DskipTests    ← compila el .jar        │
│                                                             │
│  Resultado: /app/target/PortalRegistraduriaBack-0.0.1-SNAPSHOT.jar │
└──────────────────────┬──────────────────────────────────────┘
                       │ solo se copia el .jar compilado
┌──────────────────────▼──────────────────────────────────────┐
│  ETAPA 2: production  (imagen eclipse-temurin:21-jre-alpine) │
│                                                             │
│  1. COPY app.jar .                                          │
│  2. EXPOSE 8080                                             │
│  3. ENTRYPOINT ["java", "-jar", "app.jar"]                  │
│                                                             │
│  Imagen final: ~200 MB (solo JRE + .jar)                    │
│  El Maven + código fuente nunca llega a la imagen final     │
└─────────────────────────────────────────────────────────────┘
```

**¿Por qué el primer build tarda tanto?**
- Docker descarga `maven:3.9-eclipse-temurin-21` (~500 MB) y `temurin:21-jre-alpine` (~200 MB)
- `mvn dependency:go-offline` descarga todas las dependencias de Maven (~100-300 MB según el proyecto)

**¿Por qué los builds siguientes son más rápidos?**
- Si `pom.xml` no cambió, Docker reutiliza la capa cacheada de `dependency:go-offline`
- Solo recompila desde `COPY src` en adelante

**¿Cuándo es obligatorio `--build`?**
- Cualquier cambio en archivos `.java`
- Cualquier cambio en `pom.xml`
- Cuando se agrega `SecurityConfig.java` u otro archivo Java nuevo

**¿Cuándo NO hace falta `--build`?**
- Cuando solo cambian variables de entorno en `docker-compose.prod.yml` (CORS, contraseñas, etc.)
- En ese caso `docker compose up -d` recrea el contenedor con los nuevos env vars sin recompilar

---

## Gestión de Imágenes Docker

```bash
# Ver todas las imágenes locales
docker images

# Ver solo la imagen de este proyecto
docker images | grep vote4tech-registraduria

# Eliminar imágenes intermedias sin usar (dangling)
docker image prune

# Ver cuánto espacio usa Docker
docker system df

# Limpieza completa (cuidado: elimina imágenes cacheadas)
docker system prune
```

Si el VM se queda sin espacio en disco:

```bash
# Verificar espacio disponible
df -h

# Ver qué ocupa más en Docker
docker system df -v

# Liberar imágenes intermedias (seguro, no afecta contenedores activos)
docker image prune -f
```

> La imagen de build (`maven:3.9-eclipse-temurin-21`) es la más pesada. Si el disco lo permite, dejarla en caché para que futuros `--build` no descarguen todo desde cero.

---

## Ambiente QA
