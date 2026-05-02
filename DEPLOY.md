# Guía de Despliegue — RegistraduriaBack

Este documento describe el despliegue de **RegistraduriaBack** y la configuración del VM de bases de datos compartido por todo el sistema Vote4Tech.

---

## Infraestructura Completa del Sistema

| Servicio              | VM Producción    | VM QA            | Puerto |
|-----------------------|------------------|------------------|--------|
| RegistraduriaFront    | `10.43.97.237`   | `10.43.97.232`   | `80`   |
| VotacionFront         | `10.43.97.237`   | `10.43.97.232`   | `4201` |
| **RegistraduriaBack** | `10.43.100.131`  | `10.43.99.3`     | `8080` |
| VotacionBack          | `10.43.100.131`  | `10.43.99.3`     | `8081` |
| PostgreSQL            | `10.43.101.13`   | `10.43.98.254`   | `5432` |
| CouchDB               | `10.43.101.13`   | `10.43.98.254`   | `5984` |

> Todos los servicios corren como **contenedores Docker**. Los dos frontends comparten la VM de frontends en puertos distintos; igual los backends; y las bases de datos comparten un tercer VM.

El acceso externo se realiza mediante **Cloudflare Quick Tunnel** (URL temporal generada automáticamente en cada arranque).

---

## Acceso a los VMs

### SSH

```bash
ssh estudiante@10.43.101.13    # VM Bases de datos
ssh estudiante@10.43.100.131   # VM Backends
ssh estudiante@10.43.97.237    # VM Frontends
```

En Windows, abrir **PowerShell** o **CMD** para usar `ssh` (ya viene instalado en Windows 10/11).

### Escritorio Remoto (RDP / xrdp)

1. `Win + R` → `mstsc` → ingresar la IP del VM deseado
2. Usuario: `estudiante`, contraseña del VM
3. Abrir una terminal desde el escritorio

---

## Pre-requisitos

**Docker y Docker Compose** deben estar instalados en los tres VMs. Verificar en cada uno:

```bash
docker --version
docker compose version
```

---

## Paso 1 — VM de Bases de Datos (`10.43.101.13`)

> Este paso es compartido con VotacionBack. Si el VM de BDs ya está levantado y los contenedores corren, pasar directamente al Paso 2.

Conectarse por SSH:

```bash
ssh estudiante@10.43.101.13
```

### 1.1 Crear el directorio y archivo de configuración

```bash
mkdir -p ~/vote4tech-db
cat > ~/vote4tech-db/docker-compose.db.yml << 'EOF'
services:
  postgres:
    image: postgres:16-alpine
    container_name: vote4tech-postgres
    environment:
      POSTGRES_DB: bd_nacional_vote4tech
      POSTGRES_USER: admin_db_nacional
      POSTGRES_PASSWORD: "12345"
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    restart: unless-stopped

  couchdb:
    image: couchdb:3
    container_name: vote4tech-couchdb
    environment:
      COUCHDB_USER: admin
      COUCHDB_PASSWORD: admin123
    ports:
      - "5984:5984"
    volumes:
      - couchdb_data:/opt/couchdb/data
    restart: unless-stopped

volumes:
  postgres_data:
  couchdb_data:
EOF
```

### 1.2 Levantar las bases de datos

```bash
cd ~/vote4tech-db
docker compose -f docker-compose.db.yml up -d
```

Verificar que ambos contenedores están corriendo:

```bash
docker ps
```

Deben aparecer `vote4tech-postgres` y `vote4tech-couchdb`.

Verificar CouchDB:

```bash
curl http://localhost:5984/
# debe devolver: {"couchdb":"Welcome",...}
```

### 1.3 Crear bases de datos en CouchDB (solo la primera vez)

```bash
curl -X PUT http://admin:admin123@localhost:5984/votos_urna
curl -X PUT http://admin:admin123@localhost:5984/votos_domicilio
```

---

## Paso 2 — RegistraduriaBack (`10.43.100.131`, puerto 8080)

Conectarse por SSH al VM de backends:

```bash
ssh estudiante@10.43.100.131
```

Si hay un servicio systemd antiguo que ocupa el puerto 8080, deshabilitarlo primero:

```bash
sudo systemctl stop vote4tech-back.service
sudo systemctl disable vote4tech-back.service
```

Verificar que el puerto quedó libre:

```bash
sudo ss -tlnp | grep 8080
```

### 2.1 Subir el código

**Opción A — git:**

```bash
cd ~
git clone <URL_DEL_REPOSITORIO> Vote4TechRegistraduriaBack
# o si ya existe:
cd ~/Vote4TechRegistraduriaBack && git pull
```

**Opción B — PowerShell local (Windows):**

```powershell
robocopy "C:\ruta\al\Vote4TechRegistraduriaBack" "$env:TEMP\rback-deploy" /E /XD target .git
scp -r "$env:TEMP\rback-deploy" estudiante@10.43.100.131:~/Vote4TechRegistraduriaBack
```

### 2.2 Configurar variables de entorno

Editar `docker/docker-compose.prod.yml`. El único valor que cambia en cada despliegue es `CORS_ALLOWED_ORIGINS` (se actualiza en el Paso 3 con el URL real de Cloudflare):

```bash
nano ~/Vote4TechRegistraduriaBack/docker/docker-compose.prod.yml
```

Variables pre-configuradas para producción:

```yaml
DB_URL: jdbc:postgresql://10.43.101.13:5432/bd_nacional_vote4tech
DB_USER: admin_db_nacional
DB_PASSWORD: "12345"
CORS_ALLOWED_ORIGINS: "http://10.43.97.237,https://TU_URL.trycloudflare.com"
```

> **Primera vez:** dejar `CORS_ALLOWED_ORIGINS` con un valor temporal; se actualiza después del Paso 3.

### 2.3 Levantar el contenedor

```bash
cd ~/Vote4TechRegistraduriaBack
docker compose -f docker/docker-compose.prod.yml up -d --build
```

Verificar que levantó correctamente:

```bash
docker ps
docker logs vote4tech-registraduria-back --tail=20
```

Debe aparecer al final: `Started PortalRegistraduriaBackApplication in X.X seconds`

---

## Paso 3 — Cloudflare URL y CORS

El túnel de Cloudflare lo gestiona el contenedor `vote4tech-cloudflared` de RegistraduriaFront. Después de desplegarlo (ver `DEPLOY.md` en `Vote4TechRegistraduriaFront`), obtener el URL desde el VM Frontend:

```bash
# Ejecutar en el VM 10.43.97.237:
docker logs vote4tech-cloudflared 2>&1 | grep trycloudflare
```

El URL tiene la forma `https://xxxx-xxxx-xxxx-xxxx.trycloudflare.com`.

### 3.1 Actualizar CORS

De vuelta en el VM Backend (`10.43.100.131`), editar y reconstruir:

```bash
nano ~/Vote4TechRegistraduriaBack/docker/docker-compose.prod.yml
# Actualizar CORS_ALLOWED_ORIGINS con el URL real

cd ~/Vote4TechRegistraduriaBack
docker compose -f docker/docker-compose.prod.yml up -d --build
```

---

## Paso 4 — Verificar el despliegue

Desde el VM Backend (verifica que el API responde directamente):

```bash
curl http://localhost:8080/eleccion/elecciones
```

Desde el VM Frontend (verifica el API gateway de nginx):

```bash
curl http://localhost/api/eleccion/elecciones
```

Ambos deben devolver un JSON con las elecciones.

---

## Actualizar el Despliegue con Nuevos Cambios

Conectarse al VM Backend (`10.43.100.131`):

**Opción A — git:**

```bash
cd ~/Vote4TechRegistraduriaBack
git pull
docker compose -f docker/docker-compose.prod.yml up -d --build
```

**Opción B — código manual (desde PowerShell local):**

```powershell
robocopy "C:\ruta\al\Vote4TechRegistraduriaBack" "$env:TEMP\rback-deploy" /E /XD target .git
scp -r "$env:TEMP\rback-deploy" estudiante@10.43.100.131:~/Vote4TechRegistraduriaBack
```

Luego en el VM:

```bash
cd ~/Vote4TechRegistraduriaBack
docker compose -f docker/docker-compose.prod.yml up -d --build
```

> `--build` es obligatorio. Sin él, Docker usa la imagen cacheada y los cambios no se aplican.

---

## Ambiente QA

El ambiente QA usa VMs distintas. Los pasos son idénticos, cambiando solo las IPs.

| Servicio              | VM Producción    | VM QA            | Puerto |
|-----------------------|------------------|------------------|--------|
| RegistraduriaFront    | `10.43.97.237`   | `10.43.97.232`   | `80`   |
| VotacionFront         | `10.43.97.237`   | `10.43.97.232`   | `4201` |
| RegistraduriaBack     | `10.43.100.131`  | `10.43.99.3`     | `8080` |
| VotacionBack          | `10.43.100.131`  | `10.43.99.3`     | `8081` |
| PostgreSQL            | `10.43.101.13`   | `10.43.98.254`   | `5432` |
| CouchDB               | `10.43.101.13`   | `10.43.98.254`   | `5984` |

### Cambios en VM de BDs QA (`10.43.98.254`)

Seguir el Paso 1 con esta IP en lugar de `10.43.101.13`.

### Cambios en VM Backend QA (`10.43.99.3`)

En `docker/docker-compose.prod.yml`, cambiar:

```yaml
DB_URL: jdbc:postgresql://10.43.98.254:5432/bd_nacional_vote4tech
CORS_ALLOWED_ORIGINS: "http://10.43.97.232,https://URL_QA.trycloudflare.com"
```

Luego reconstruir:

```bash
docker compose -f docker/docker-compose.prod.yml up -d --build
```

### Cambios en VM Frontend QA (`10.43.97.232`)

En `docker/nginx.conf` de RegistraduriaFront, cambiar el `proxy_pass`:

```nginx
# De:
proxy_pass http://10.43.100.131:8080/;
# A:
proxy_pass http://10.43.99.3:8080/;
```

Reconstruir:

```bash
cd ~/Vote4TechRegistraduriaFront
docker compose -f docker/docker-compose.prod.yml up -d --build
```

> Obtener el nuevo URL de Cloudflare del VM Frontend QA y actualizar `CORS_ALLOWED_ORIGINS` en el backend QA.

---

## Troubleshooting

### El contenedor no levanta / se reinicia constantemente

```bash
docker logs vote4tech-registraduria-back --tail=50
```

Causas comunes:
- `DB_URL` incorrecto → verificar que PostgreSQL está corriendo en `10.43.101.13:5432`
- `CORS_ALLOWED_ORIGINS` con formato incorrecto

### Puerto 8080 ocupado

```bash
sudo ss -tlnp | grep 8080
sudo kill -9 $(sudo lsof -t -i:8080)
```

### No conecta a PostgreSQL

Verificar desde el VM Backend:

```bash
nc -zv 10.43.101.13 5432
```

Si no conecta, verificar que el contenedor PostgreSQL está corriendo en `10.43.101.13`.

### El URL de Cloudflare cambió

1. Obtener el nuevo URL: `docker logs vote4tech-cloudflared 2>&1 | grep trycloudflare` (en VM Frontend)
2. Actualizar `CORS_ALLOWED_ORIGINS` en `docker/docker-compose.prod.yml`
3. `docker compose -f docker/docker-compose.prod.yml up -d --build`

### Reconstruir completamente

```bash
cd ~/Vote4TechRegistraduriaBack
docker compose -f docker/docker-compose.prod.yml down
docker compose -f docker/docker-compose.prod.yml up -d --build
```
