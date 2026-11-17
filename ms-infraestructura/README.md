# ms-infraestructura

Microservicio Spring Boot que expone Granjas, Galpones, Líneas de bebedero y sus mediciones de temperatura. Reemplaza lo que antes simulaba `ApiSimulada` en la app Android, ahora con datos reales persistidos en Oracle.

Responsable: **Bray Arciniegas**.

## Qué expone

Base: `http://localhost:8081`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/granjas` | Lista todas las granjas |
| GET | `/api/granjas/{id}` | Una granja por id |
| POST | `/api/granjas` | Crea una granja |
| PUT | `/api/granjas/{id}` | Actualiza una granja |
| DELETE | `/api/granjas/{id}` | Elimina una granja |
| GET | `/api/galpones` | Lista todos los galpones |
| GET | `/api/galpones/{id}` | Un galpón por id |
| POST | `/api/galpones` | Crea un galpón |
| PUT | `/api/galpones/{id}` | Actualiza un galpón |
| DELETE | `/api/galpones/{id}` | Elimina un galpón |
| GET | `/api/lineas` | Lista todas las líneas de bebedero |
| GET | `/api/lineas/{id}` | Una línea por id |
| POST | `/api/lineas` | Crea una línea |
| PUT | `/api/lineas/{id}` | Actualiza la temperatura de una línea (recalcula el estado y guarda una medición en el historial) |
| DELETE | `/api/lineas/{id}` | Elimina una línea |
| GET | `/api/lineas/{id}/mediciones` | Historial de mediciones de temperatura de una línea |

Documentación interactiva (Swagger UI): `http://localhost:8081/swagger-ui.html`

## Base de datos

Oracle (Database Free / XE, según la instalación de cada quien), en un esquema dedicado:

- Usuario: `BEBEDERO_INFRA`
- Service name: depende de la instalación local (`XEPDB1` en Oracle XE 21c, `FREEPDB1` en Oracle Database Free) — ajustar en `application.properties` según corresponda.
- La contraseña **no está en el código**: se lee desde la variable de entorno `DB_INFRA_PASSWORD`.

No hace falta escribir ningún script `CREATE TABLE`: con `spring.jpa.hibernate.ddl-auto=update`, Hibernate crea las tablas automáticamente la primera vez que el microservicio se conecta, a partir de las clases `@Entity` de Java.

## Cómo correrlo

1. Crear el usuario de Oracle (una sola vez), conectado al service name correcto:
   ```sql
   CREATE USER BEBEDERO_INFRA IDENTIFIED BY "tu_password";
   GRANT CONNECT, RESOURCE TO BEBEDERO_INFRA;
   ALTER USER BEBEDERO_INFRA QUOTA UNLIMITED ON USERS;
   ```
2. Definir la variable de entorno con esa contraseña:
   ```powershell
   [System.Environment]::SetEnvironmentVariable('DB_INFRA_PASSWORD', 'tu_password', 'User')
   ```
   (cerrar y volver a abrir la terminal/IDE para que la tome)
3. Correr el microservicio:
   ```bash
   ./mvnw.cmd spring-boot:run
   ```
4. Verificar: `http://localhost:8081/api/granjas` debería responder con JSON.

## Tecnologías

- Spring Boot 4.1.2 (Web, Data JPA)
- Oracle JDBC (`ojdbc11`)
- Springdoc OpenAPI (Swagger UI)
