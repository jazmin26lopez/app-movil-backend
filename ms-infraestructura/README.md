# ms-infraestructura — Microservicio de Monitoreo de Bebederos

Microservicio desarrollado con **Spring Boot y Oracle Database** para administrar las granjas, galpones, líneas de bebederos y mediciones de temperatura del proyecto Bebedero.

Proyecto desarrollado para la asignatura **DSY1105 — Desarrollo de Aplicaciones Móviles**, Duoc UC.

**Responsable:** Bryam Arciniegas.

## Descripción

El microservicio de infraestructura administra la información de las granjas avícolas y sus líneas de bebederos.

Permite consultar y mantener los registros de granjas, galpones, líneas, temperaturas actuales e historial de mediciones.

Los datos se almacenan en Oracle y se exponen mediante una API REST consumida por la aplicación Android con Retrofit.

## Funcionalidades implementadas

- Registro y consulta de granjas.
- Administración de galpones.
- Administración de líneas de bebederos.
- Consulta de temperaturas actuales.
- Clasificación del estado de temperatura.
- Actualización de temperatura de líneas.
- Registro de mediciones en el historial.
- Consulta del historial de temperaturas.
- Persistencia con Spring Data JPA y Oracle.
- Documentación de endpoints mediante Swagger UI.
- Integración con la aplicación Android.

## Tecnologías utilizadas

| Tecnología | Función |
|---|---|
| Java | Lenguaje de programación |
| Spring Boot 4.1.2 | Desarrollo del microservicio |
| Spring Web | API REST |
| Spring Data JPA | Acceso a datos |
| Hibernate | Persistencia de entidades |
| Oracle Database | Base de datos |
| Oracle JDBC (`ojdbc11`) | Conexión a Oracle |
| Springdoc OpenAPI | Documentación Swagger |

## Arquitectura

El microservicio utiliza una arquitectura organizada en capas:

- **Controller:** expone los endpoints REST.
- **Service:** administra la lógica de negocio.
- **Repository:** realiza las operaciones de persistencia.
- **Entity/Model:** representa las entidades almacenadas en Oracle.

La información se organiza jerárquicamente:

**Granja → Galpón → Línea de bebedero → Mediciones de temperatura.**

## Endpoints REST

**Dirección base:** `http://localhost:8081`

### Granjas

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/granjas` | Lista todas las granjas |

### Galpones

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/galpones` | Lista galpones (filtrable por `?granjaId=`) |

### Líneas de bebederos

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/lineas` | Lista líneas (filtrable por `?galponId=`) |
| PUT | `/api/lineas/{id}` | Actualiza una línea y su temperatura |
| GET | `/api/lineas/{id}/mediciones` | Consulta el historial de temperaturas |

**Nota:** solo se exponen los endpoints que la app Android realmente consume. Granjas, Galpones y Líneas se consultan de solo lectura desde la app (nunca se crean, editan ni eliminan desde la interfaz); por eso no existen endpoints `POST`/`DELETE` ni `GET /{id}` para esas tres entidades — se retiraron por ser código sin uso.

## Monitoreo de temperaturas

Cada línea de bebedero mantiene información sobre su temperatura actual, fecha de actualización y estado.

La clasificación utilizada en la aplicación es:

| Estado | Temperatura |
|---|---|
| Normal | 15 °C a 21 °C |
| Advertencia | Superior a 21 °C y hasta 30 °C |
| Crítico | Menor a 15 °C o mayor a 30 °C |

Estos estados permiten identificar líneas que requieren atención.

### Actualización de temperatura

El endpoint `PUT /api/lineas/{id}` permite actualizar la información de una línea.

Cuando se actualiza su temperatura, el microservicio recalcula el estado correspondiente y registra una medición en el historial.

### Historial de mediciones

El endpoint:

`GET /api/lineas/{id}/mediciones`

Permite consultar las mediciones almacenadas para una línea determinada.

Esta información es utilizada por la aplicación Android para presentar el historial de temperaturas.

## Integración con flushing

El microservicio de infraestructura trabaja junto con `ms-flushing`.

La aplicación Android coordina las operaciones de ambos servicios.

### Flujo de registro de limpieza

1. El operario selecciona una línea de bebedero.
2. Registra la limpieza mediante el formulario Android.
3. La aplicación envía el registro a `ms-flushing`, que lo almacena en Oracle.
4. Después del registro, Android solicita una actualización de temperatura al microservicio de infraestructura.
5. Infraestructura gestiona el nuevo estado de la línea y su historial de mediciones.

Esta integración reemplaza la actualización de temperatura que anteriormente se realizaba únicamente mediante la simulación local.

**Importante:** infraestructura y flushing son microservicios independientes y utilizan esquemas Oracle separados.

## Base de datos Oracle

El microservicio utiliza su propio esquema en Oracle.

| Configuración | Valor |
|---|---|
| Motor | Oracle Database Free / XE |
| Usuario | `BEBEDERO_INFRA` |
| Contraseña | Variable de entorno `DB_INFRA_PASSWORD` |
| Persistencia | Spring Data JPA / Hibernate |

El nombre del servicio depende de la instalación:

- `FREEPDB1`: Oracle Database Free.
- `XEPDB1`: Oracle XE 21c.

### Creación del usuario

Conectarse a Oracle con los permisos administrativos correspondientes y ejecutar:

```sql
CREATE USER BEBEDERO_INFRA
IDENTIFIED BY "tu_password";

GRANT CONNECT, RESOURCE TO BEBEDERO_INFRA;

ALTER USER BEBEDERO_INFRA
QUOTA UNLIMITED ON USERS;
```

### Creación de tablas

Hibernate administra las estructuras de las entidades cuando está configurado:

```properties
spring.jpa.hibernate.ddl-auto=update
```

Esta configuración permite crear y actualizar las tablas durante el desarrollo.

## Configuración y ejecución

### Requisitos previos

- JDK compatible con el proyecto.
- Oracle Database instalado y funcionando.
- Usuario `BEBEDERO_INFRA` creado.
- Maven Wrapper del proyecto.
- Puerto 8081 disponible.

### Configurar la contraseña

En PowerShell:

```powershell
[System.Environment]::SetEnvironmentVariable('DB_INFRA_PASSWORD', 'tu_password', 'User')
```

Cerrar y volver a abrir la terminal o el IDE para reconocer la variable.

### Ejecutar el microservicio

Desde la carpeta `ms-infraestructura`:

```powershell
.\mvnw.cmd spring-boot:run
```

También puede iniciarse desde IntelliJ IDEA utilizando la clase principal `MsInfraestructuraApplication`.

### Verificar funcionamiento

Con el servicio iniciado, abrir:

`http://localhost:8081/api/granjas`

La respuesta debe corresponder a un listado JSON de granjas, que puede estar vacío si todavía no existen registros.

También se puede consultar:

`http://localhost:8081/api/lineas`

## Documentación Swagger

Los endpoints se documentan mediante Springdoc OpenAPI.

**Swagger UI:**

http://localhost:8081/swagger-ui.html

Desde Swagger es posible revisar las operaciones y realizar solicitudes de prueba.

## Integración con Android

La aplicación Bebedero consume el microservicio mediante Retrofit.

El acceso a los datos se centraliza en `BebederoRepository`, utilizado por los ViewModels.

La aplicación permite:

- Consultar granjas y galpones.
- Consultar líneas y temperaturas.
- Identificar estados normales, advertencias y condiciones críticas.
- Mostrar alertas de temperatura.
- Consultar el historial de mediciones.
- Actualizar la información después de registrar un flushing.

### Respaldo local

La aplicación utiliza Room para conservar información consultada previamente.

Si el microservicio no está disponible, determinadas consultas pueden utilizar los datos locales almacenados.

Room funciona como respaldo de consulta y no sustituye la base de datos Oracle del microservicio.

## Manejo de errores

Se contemplan situaciones como:

- Fallos de comunicación HTTP.
- Indisponibilidad temporal de Oracle.
- Solicitudes de recursos inexistentes.
- Problemas al consultar temperaturas.
- Errores durante operaciones de actualización.

La aplicación Android implementa el manejo correspondiente de errores y, para determinadas consultas, utiliza el respaldo local disponible.

## Pruebas y cobertura

La aplicación Android incorpora pruebas unitarias de consultas, procesamiento de información, manejo de errores y comunicación con el Repository.

Se utilizan JUnit4, MockK, kotlinx-coroutines-test y JaCoCo.

En la ejecución local verificada de Android se obtuvieron los siguientes resultados:

| Componente Android | Instrucciones | Ramas |
|---|---:|---:|
| ViewModels | 87 % | 100 % |
| Repository | 97 % | 84 % |
| **Total analizado** | **96 %** | **86 %** |

Estos porcentajes corresponden a las clases Android incluidas en el informe JaCoCo. **No representan una medición independiente de cobertura del microservicio Java.**

## Seguridad

- La contraseña Oracle se configura mediante variable de entorno.
- No almacenar credenciales privadas en el repositorio.
- Mantener separados los esquemas de infraestructura y flushing.
- No publicar archivos de configuración con contraseñas personales.
- Verificar la configuración de red antes de conectar dispositivos Android.

## Documentación relacionada

- [README principal del proyecto](../README.md)
- [Aplicación Android](https://github.com/jazmin26lopez/Bebedero)
- [Microservicio de flushing](../ms-flushing/README.md)

## Repositorio oficial

**Repositorio:** https://github.com/jazmin26lopez/app-movil-backend

**Rama de entrega:** `integracion-oracle`

**Proyecto:** Bebedero — DSY1105, Duoc UC.
