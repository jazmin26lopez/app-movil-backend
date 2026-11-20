# ms-flushing — Microservicio de Limpieza de Bebederos

Microservicio desarrollado con **Spring Boot y Oracle Database** para gestionar los registros de limpieza (*flushing*) de las líneas de bebederos de granjas avícolas.

Forma parte del proyecto **Bebedero**, desarrollado para la asignatura **DSY1105 — Desarrollo de Aplicaciones Móviles**, Duoc UC.

**Responsable:** Jazmin López.

## Descripción

El microservicio permite registrar, consultar, actualizar y eliminar las limpiezas realizadas por los operarios.

Cada flushing contiene información sobre la línea intervenida, el usuario responsable, la fecha y hora, una observación y una fotografía opcional.

La información se almacena de forma persistente en una base de datos Oracle.

## Funcionalidades implementadas

- Registro de nuevos flushings.
- Consulta de todos los registros.
- Consulta de un registro mediante su identificador.
- Filtrado de registros por línea de bebedero.
- Actualización de observaciones.
- Eliminación de registros.
- Persistencia mediante Spring Data JPA.
- Validación de datos de entrada.
- Documentación de endpoints con Swagger UI.
- Integración con la aplicación Android mediante Retrofit.

## Tecnologías utilizadas

| Tecnología | Función |
|---|---|
| Java | Lenguaje de programación |
| Spring Boot 4.1.2 | Desarrollo del microservicio |
| Spring Web | Exposición de API REST |
| Spring Data JPA | Acceso a datos |
| Hibernate | Mapeo y persistencia de entidades |
| Oracle Database | Base de datos |
| Oracle JDBC (`ojdbc11`) | Conexión con Oracle |
| Spring Validation | Validación de solicitudes |
| Springdoc OpenAPI | Documentación Swagger |

## Arquitectura

El microservicio utiliza una arquitectura REST organizada en capas:

- **Controller:** recibe las peticiones HTTP.
- **Service:** ejecuta la lógica de negocio.
- **Repository:** administra las operaciones de persistencia.
- **Entity/Model:** representa los registros almacenados en Oracle.

La aplicación Android consume los endpoints mediante Retrofit.

## Endpoints REST

**Dirección base:** `http://localhost:8082`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/flushings` | Lista todos los registros |
| GET | `/api/flushings?lineaId=6` | Filtra registros por línea |
| GET | `/api/flushings/{id}` | Consulta un registro por identificador |
| POST | `/api/flushings` | Crea un nuevo flushing |
| PUT | `/api/flushings/{id}` | Actualiza la observación |
| DELETE | `/api/flushings/{id}` | Elimina un registro |

### Registrar flushing

**Petición:**

`POST /api/flushings`

```json
{
  "lineaId": 6,
  "usuarioId": 1,
  "fechaHora": "29/09/2026 16:10",
  "observacion": "Limpieza realizada correctamente",
  "fotoUri": null
}
```

**Descripción de los campos:**

| Campo | Descripción |
|---|---|
| `lineaId` | Identificador de la línea intervenida |
| `usuarioId` | Identificador del operario |
| `fechaHora` | Fecha y hora del flushing |
| `observacion` | Descripción de la limpieza realizada |
| `fotoUri` | Referencia opcional a una fotografía |

El microservicio almacena el registro en Oracle y devuelve la información correspondiente.

### Consultar registros

**Petición:**

`GET /api/flushings`

Devuelve los registros disponibles.

También permite filtrar por línea:

`GET /api/flushings?lineaId=6`

### Actualizar observación

**Petición:**

`PUT /api/flushings/{id}`

```json
{
  "observacion": "Limpieza revisada y confirmada"
}
```

Permite modificar la observación de un flushing existente.

### Eliminar flushing

**Petición:**

`DELETE /api/flushings/{id}`

Elimina el registro identificado por su ID.

## Base de datos Oracle

El microservicio utiliza un esquema independiente:

| Configuración | Valor |
|---|---|
| Motor | Oracle Database Free / XE |
| Usuario | `BEBEDERO_FLUSHING` |
| Contraseña | Variable de entorno `DB_PASSWORD` |
| Persistencia | Spring Data JPA / Hibernate |

El nombre del servicio Oracle depende de la instalación:

- `FREEPDB1`: Oracle Database Free.
- `XEPDB1`: Oracle XE 21c.

### Creación del usuario

Conectado a Oracle con los permisos administrativos correspondientes:

```sql
CREATE USER BEBEDERO_FLUSHING
IDENTIFIED BY "tu_password";

GRANT CONNECT, RESOURCE TO BEBEDERO_FLUSHING;

ALTER USER BEBEDERO_FLUSHING
QUOTA UNLIMITED ON USERS;
```

La contraseña debe coincidir con la utilizada en la configuración del microservicio.

### Creación de tablas

Hibernate administra las tablas correspondientes a las entidades del servicio cuando se utiliza:

```properties
spring.jpa.hibernate.ddl-auto=update
```

Esta configuración permite crear y actualizar las estructuras necesarias durante el desarrollo.

## Configuración y ejecución

### 1. Requisitos previos

- JDK compatible con el proyecto.
- Oracle Database funcionando.
- Usuario `BEBEDERO_FLUSHING` creado.
- Maven Wrapper incluido en el proyecto.
- Puerto 8082 disponible.

### 2. Configurar la contraseña

En PowerShell:

```powershell
[System.Environment]::SetEnvironmentVariable('DB_PASSWORD', 'tu_password', 'User')
```

Cerrar y volver a abrir la terminal o el IDE para que reconozca la variable.

### 3. Ejecutar el microservicio

Desde la carpeta `ms-flushing`:

```powershell
.\mvnw.cmd spring-boot:run
```

También puede ejecutarse desde IntelliJ IDEA utilizando la clase principal `MsFlushingApplication`.

### 4. Verificar funcionamiento

Abrir:

`http://localhost:8082/api/flushings`

El servicio debe responder con una lista JSON, que puede estar vacía si todavía no existen registros.

## Documentación Swagger

El microservicio incluye documentación interactiva mediante Springdoc OpenAPI.

**Swagger UI:**

http://localhost:8082/swagger-ui.html

Desde esta interfaz es posible consultar los endpoints y probar las operaciones disponibles.

## Integración con Android e infraestructura

La aplicación Android utiliza `BebederoRepository` y Retrofit para comunicarse con `ms-flushing`.

El flujo de registro es:

1. El operario selecciona una línea de bebedero.
2. Ingresa la observación y, opcionalmente, toma una fotografía.
3. Android envía el registro mediante `POST /api/flushings`.
4. El microservicio almacena el flushing en Oracle.
5. Después del registro, la aplicación Android solicita al microservicio de infraestructura la actualización de temperatura correspondiente.

**Importante:** `ms-flushing` administra la persistencia de los registros de limpieza. La actualización de temperatura pertenece a `ms-infraestructura` y se coordina desde la aplicación Android.

## Manejo de errores

La aplicación contempla situaciones como:

- Fall
