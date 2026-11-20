# Bebedero — Monitoreo de Bebederos

Aplicación Android para el monitoreo de temperatura del agua en bebederos de granjas avícolas. Permite al **Operario** consultar temperaturas, detectar alertas y registrar limpiezas (*flushing*), mientras que el **Supervisor** puede revisar indicadores generales, eventos críticos e historiales.

Proyecto desarrollado para la asignatura **DSY1105 — Desarrollo de Aplicaciones Móviles**, Duoc UC.

## Integrantes

- Bray Arciniegas — GitHub: [BrayArciniegas](https://github.com/BrayArciniegas)
- Jazmin López — GitHub: [jazmin26lopez](https://github.com/jazmin26lopez)

## Descripción del proyecto

Cada granja contiene galpones y cada galpón tiene líneas de bebederos. Cada línea registra su temperatura y estado.

Los rangos utilizados son:

| Estado | Temperatura |
|---|---|
| Normal | 15 °C a 21 °C |
| Advertencia | Superior a 21 °C y hasta 30 °C |
| Crítico | Inferior a 15 °C o superior a 30 °C |

La información de infraestructura y los registros de flushing se almacenan en una base de datos **Oracle**, mediante dos microservicios independientes desarrollados con Spring Boot.

La aplicación utiliza **Retrofit** para consumir los servicios y **Room** como respaldo local.

El registro de flushing permite guardar una limpieza y solicitar la actualización de temperatura de la línea correspondiente mediante el microservicio de infraestructura.

## Funcionalidades implementadas

### Rol Operario

- Selección de rol al iniciar la aplicación.
- Consulta de granjas, galpones y líneas de bebederos.
- Visualización de temperatura, estado y última actualización.
- Consulta de temperatura ambiente mediante Open-Meteo.
- Visualización de alertas en estado Advertencia o Crítico.
- Acceso directo desde las alertas al detalle de la línea.
- Registro de flushing con observación obligatoria.
- Fotografía opcional mediante la cámara del dispositivo.
- Edición y eliminación de registros de flushing.
- Pantalla de confirmación del registro.
- Notificaciones locales de temperatura mediante WorkManager.

### Rol Supervisor

- Panel general con indicadores de granjas y líneas.
- Cantidad de líneas normales, en advertencia y críticas.
- Consulta de eventos críticos.
- Acceso al detalle de las líneas.
- Historial de temperaturas.
- Historial de registros de flushing.
- Filtros de historial por día, mes y año.
- Notificación con el conteo de flushings realizados durante el día.

### Funcionalidades transversales

- Navegación mediante Navigation Compose.
- Interfaz desarrollada con Jetpack Compose y Material 3.
- Consumo de microservicios mediante Retrofit y Gson.
- Persistencia y respaldo local mediante Room.
- Manejo de errores de comunicación.
- Animaciones y retroalimentación visual.
- Integración con API externa de temperatura ambiente.

## Arquitectura

La aplicación utiliza el patrón **MVVM (Model–View–ViewModel)**.

```text
Bebedero-Android/
├── app/
│   └── src/
│       ├── main/
│       │   └── java/com/example/bebedero/
│       │       ├── model/
│       │       ├── repository/
│       │       │   ├── local/
│       │       │   └── remote/
│       │       ├── viewmodel/
│       │       └── ui/
│       └── test/
│           └── java/com/example/bebedero/
├── build.gradle.kts
└── README.md
```

**Responsabilidades:**

- **Model:** representa granjas, galpones, líneas, usuarios, temperaturas y flushing.
- **View:** pantallas e interfaces con Jetpack Compose.
- **ViewModel:** gestiona el estado y la lógica de presentación.
- **Repository:** centraliza el acceso a microservicios y almacenamiento local.
- **Retrofit:** permite la comunicación HTTP con las APIs.
- **Room:** proporciona almacenamiento local de respaldo.

## Arquitectura de servicios

El sistema contiene tres componentes principales:

| Componente | Tecnología | Función |
|---|---|---|
| Aplicación móvil | Kotlin, Jetpack Compose | Interfaz para Operario y Supervisor |
| ms-infraestructura | Spring Boot, Oracle | Granjas, galpones, líneas y temperaturas |
| ms-flushing | Spring Boot, Oracle | Registro y gestión de limpiezas |

Ambos microservicios utilizan esquemas independientes en Oracle.

### Microservicio de infraestructura

**Puerto:** 8081

Administra granjas, galpones, líneas de bebederos, temperaturas actuales e historial de mediciones.

Principales endpoints:

```http
GET /api/granjas
GET /api/galpones
GET /api/lineas
GET /api/lineas/{id}/mediciones
```

Además, dispone de operaciones de creación, actualización y eliminación para las entidades principales.

### Microservicio de flushing

**Puerto:** 8082

Administra los registros de limpieza de las líneas.

```http
GET /api/flushings
GET /api/flushings/{id}
POST /api/flushings
PUT /api/flushings/{id}
DELETE /api/flushings/{id}
```

El registro de flushing incluye:

- Identificador de línea.
- Identificador de usuario.
- Fecha y hora.
- Observación.
- URI de fotografía opcional.

### Integración entre flushing e infraestructura

Al registrar una limpieza:

1. La aplicación envía el registro a `ms-flushing`.
2. El microservicio guarda la información en Oracle.
3. La aplicación solicita al servicio de infraestructura la actualización de la temperatura de la línea.
4. El servicio de infraestructura gestiona el estado y las mediciones de temperatura.

Esta integración reemplaza la lógica de actualización de temperatura que anteriormente dependía de datos simulados.

**Nota:** la corrección fue incorporada en la rama Android de integración y debe mantenerse sincronizada con la copia del proyecto incluida en el repositorio oficial.

## API externa — Open-Meteo

La aplicación utiliza **Open-Meteo** para consultar la temperatura ambiente de la zona.

Ejemplo de consulta:

```http
GET https://api.open-meteo.com/v1/forecast?latitude=-33.45&longitude=-70.66&current=temperature_2m
```

Características:

- No requiere clave de API para el uso implementado.
- Permite obtener temperatura ambiente actual.
- Se integra mediante Retrofit.
- Su funcionamiento es independiente de los microservicios propios.

## Persistencia de datos

### Oracle

Los microservicios utilizan esquemas separados:

| Microservicio | Esquema |
|---|---|
| Infraestructura | `BEBEDERO_INFRA` |
| Flushing | `BEBEDERO_FLUSHING` |

La configuración de conexión y las contraseñas se administran en cada servicio mediante sus propiedades y variables de entorno.

### Room

La aplicación cuenta con almacenamiento local para respaldar consultas de granjas, galpones, líneas y registros de flushing cuando los servicios remotos no están disponibles.

El respaldo local no reemplaza la persistencia principal en Oracle.

## Notificaciones

La aplicación incorpora notificaciones locales según el tipo de información y el rol.

- **Operario:** notificaciones relacionadas con temperaturas en Advertencia o Crítico.
- **Supervisor:** notificaciones con el conteo de registros de flushing realizados durante el día.

Se utiliza WorkManager para tareas periódicas de comprobación de temperaturas.

## Pruebas unitarias

Se implementaron pruebas unitarias utilizando:

- JUnit4.
- MockK.
- kotlinx-coroutines-test.
- JaCoCo para medir cobertura.

Las pruebas consideran las siguientes clases:

| Clase | Alcance |
|---|---|
| `FlushingViewModelTest` | Validaciones, registro, consulta, actualización y eliminación |
| `ConsultaViewModelTest` | Consultas, transformación y procesamiento de información |
| `BebederoRepositoryTest` | Acceso remoto, respaldo local, errores HTTP, flushing y notificaciones |

### Resultados de cobertura

Resultados obtenidos mediante una ejecución local exitosa de JaCoCo después de integrar las pruebas:

| Componente | Instrucciones | Ramas |
|---|---:|---:|
| ViewModels | 87 % | 100 % |
| Repository | 97 % | 84 % |
| **Total analizado** | **96 %** | **86 %** |

Se superó el umbral del **80 %** en ambas métricas del reporte.

Estos valores corresponden únicamente a las clases incluidas en la configuración de JaCoCo, no a la totalidad del código Android.

### Ejecutar las pruebas

Desde la carpeta `Bebedero-Android`:

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

### Generar la cobertura

```powershell
.\gradlew.bat :app:jacocoTestReport
```

También es posible limpiar el proyecto, ejecutar las pruebas y generar el informe en una sola instrucción:

```powershell
.\gradlew.bat :app:clean :app:testDebugUnitTest :app:jacocoTestReport
```

El resultado HTML se encuentra en:

```text
app/build/reports/jacoco/jacocoTestReport/html/index.html
```

## Tecnologías utilizadas

| Tecnología | Utilización |
|---|---|
| Kotlin | Desarrollo Android |
| Jetpack Compose | Interfaces de usuario |
| Material 3 | Componentes visuales |
| Navigation Compose | Navegación |
| MVVM | Arquitectura |
| Retrofit | Consumo de servicios HTTP |
| Gson | Conversión de JSON |
| Room | Persistencia local |
| WorkManager | Procesos periódicos |
| Spring Boot | Microservicios |
| Oracle Database | Persistencia principal |
| Open-Meteo | Temperatura ambiente |
| JUnit4 | Pruebas unitarias |
| MockK | Simulación de dependencias |
| JaCoCo | Cobertura de pruebas |

## Requisitos previos

- Android Studio.
- SDK de Android compatible con la configuración Gradle del proyecto.
- JDK compatible con la versión de Gradle y Android Gradle Plugin utilizada.
- Dispositivo físico o emulador Android compatible.
- Oracle Database configurado.
- Microservicios `ms-infraestructura` y `ms-flushing` en ejecución.
- Conexión de red entre el dispositivo Android y el computador que ejecuta los microservicios.

## Instalación y ejecución

### 1. Clonar el repositorio oficial

```powershell
git clone -b integracion-oracle https://github.com/jazmin26lopez/app-movil-backend.git
```

### 2. Configurar los microservicios

Desde sus respectivas carpetas:

```text
ms-infraestructura/
ms-flushing/
```

Configurar la conexión con Oracle y las variables de entorno correspondientes.

- `DB_INFRA_PASSWORD`
- `DB_PASSWORD`

### 3. Iniciar los microservicios

Cada servicio debe ejecutarse de forma independiente.

Infraestructura:

```text
http://localhost:8081
```

Flushing:

```text
http://localhost:8082
```

Las instrucciones de configuración están disponibles en los README específicos de cada microservicio.

### 4. Abrir Android Studio

Abrir la carpeta:

```text
app-movil-backend/Bebedero-Android
```

Esperar la sincronización de Gradle.

### 5. Configurar la conexión de red

Revisar el archivo:

```text
app/src/main/java/com/example/bebedero/repository/remote/RetrofitCliente.kt
```

Configurar la constante `IP_SERVIDOR` con la IPv4 del computador que ejecuta los microservicios.

Para obtenerla en Windows:

```powershell
ipconfig
```

El dispositivo Android y el computador deben estar conectados a una red que permita la comunicación.

No subir direcciones IP personales como configuración definitiva del proyecto.

### 6. Ejecutar la aplicación

Seleccionar el dispositivo o emulador desde Android Studio y presionar **Run**.

También se puede ejecutar:

```powershell
.\gradlew.bat :app:installDebug
```

## Generación de APK firmado

Para distribuir la aplicación se debe generar una APK de tipo *release* y firmarla con un certificado privado.

### Procedimiento recomendado

Desde Android Studio:

1. Abrir **Build**.
2. Seleccionar **Generate Signed App Bundle or APK**.
3. Elegir **APK**.
4. Crear o seleccionar un archivo de firma `.jks`.
5. Introducir el alias y las credenciales correspondientes.
6. Seleccionar la variante **release**.
7. Generar la APK.

La APK firmada puede entregarse e instalarse en un dispositivo Android compatible.

**Seguridad:** los archivos `.jks`, las contraseñas y las credenciales privadas no deben incluirse en el repositorio público.

La compilación por consola requiere que la configuración de firma de Gradle esté preparada correctamente. Si no está configurada, utilizar el asistente de firma de Android Studio.

## Documentación de microservicios

- [README de ms-infraestructura](../ms-infraestructura/README.md)
- [README de ms-flushing](../ms-flushing/README.md)
- [README principal del proyecto](../README.md)

## Planificación

La planificación y distribución de tareas del equipo se realizó mediante el tablero Trello **APP Bebederos**.

## Repositorio y rama de entrega

**Repositorio oficial:**

https://github.com/jazmin26lopez/app-movil-backend

**Rama:**

`integracion-oracle`

Esta rama reúne el proyecto Android y los dos microservicios para la entrega de la asignatura.
