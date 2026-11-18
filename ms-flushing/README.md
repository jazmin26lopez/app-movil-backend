# ms-flushing

Microservicio Spring Boot que expone el CRUD de registros de flushing (limpieza de líneas de bebedero): crear, listar, editar la observación y eliminar.

Responsable: **Jazmin López**.

## Qué expone

Base: `http://localhost:8082`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/flushings` | Lista todos los registros (opcionalmente filtrados por `?lineaId=`) |
| GET | `/api/flushings/{id}` | Un registro por id |
| POST | `/api/flushings` | Crea un registro nuevo |
| PUT | `/api/flushings/{id}` | Edita la observación de un registro |
| DELETE | `/api/flushings/{id}` | Elimina un registro |

**Body de `POST /api/flushings`:**
```json
{
  "lineaId": 6,
  "usuarioId": 1,
  "fechaHora": "29/09/2026 16:10",
  "observacion": "Limpieza realizada correctamente",
  "fotoUri": null
}
```

**Body de `PUT /api/flushings/{id}`:**
```json
{
  "observacion": "Observación corregida"
}
```

Documentación interactiva (Swagger UI): `http://localhost:8082/swagger-ui.html`

## Base de datos

Oracle (Database Free / XE, según la instalación de cada quien), en un esquema dedicado:

- Usuario: `BEBEDERO_FLUSHING`
- Service name: depende de la instalación local (`XEPDB1` en Oracle XE 21c, `FREEPDB1` en Oracle Database Free) — ajustar en `application.properties` según corresponda.
- La contraseña **no está en el código**: se lee desde la variable de entorno `DB_PASSWORD`.

Las tablas las crea Hibernate automáticamente (`spring.jpa.hibernate.ddl-auto=update`), no hace falta escribir SQL a mano.

## Cómo correrlo

1. Crear el usuario de Oracle (una sola vez), conectado al service name correcto:
   ```sql
   CREATE USER BEBEDERO_FLUSHING IDENTIFIED BY "tu_password";
   GRANT CONNECT, RESOURCE TO BEBEDERO_FLUSHING;
   ALTER USER BEBEDERO_FLUSHING QUOTA UNLIMITED ON USERS;
   ```
2. Definir la variable de entorno con esa contraseña:
   ```powershell
   [System.Environment]::SetEnvironmentVariable('DB_PASSWORD', 'tu_password', 'User')
   ```
   (cerrar y volver a abrir la terminal/IDE para que la tome)
3. Correr el microservicio:
   ```bash
   ./mvnw.cmd spring-boot:run
   ```
4. Verificar: `http://localhost:8082/api/flushings` debería responder con JSON (lista vacía si no hay registros).

## Tecnologías

- Spring Boot 4.1.2 (Web, Data JPA, Validation)
- Oracle JDBC (`ojdbc11`)
- Springdoc OpenAPI (Swagger UI)
