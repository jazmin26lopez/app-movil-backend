# Bebedero — Monitoreo de Bebederos

Proyecto desarrollado para la asignatura **DSY1105 — Desarrollo de Aplicaciones Móviles**, Duoc UC.

## Integrantes

- Bray Arciniegas
- Jazmin López

## Descripción

Bebedero es una solución de monitoreo de temperatura del agua en líneas de bebederos de granjas avícolas. Permite consultar granjas, galpones y líneas, identificar alertas de temperatura y registrar limpiezas (*flushing*).

El sistema utiliza una aplicación Android conectada mediante Retrofit a dos microservicios Spring Boot, con persistencia de datos en Oracle. Incorpora además Room para almacenamiento local y Open-Meteo para consultar la temperatura ambiente.

## Estructura del proyecto

| Carpeta | Descripción |
|---|---|
| `Bebedero-Android/` | Aplicación móvil Kotlin, Jetpack Compose y MVVM |
| `ms-infraestructura/` | Microservicio de granjas, galpones, líneas y temperaturas; puerto 8081 |
| `ms-flushing/` | Microservicio de registros de limpieza; puerto 8082 |

## Roles

**Operario:** consulta temperaturas y alertas, registra limpiezas y revisa su historial.

**Supervisor:** consulta indicadores generales, eventos críticos, historiales de temperaturas y registros de flushing.

## Pruebas unitarias

La aplicación cuenta con pruebas unitarias implementadas con JUnit4, MockK y `kotlinx-coroutines-test`.

Se incluyen pruebas para:

- `FlushingViewModelTest`: validación de observaciones y operaciones de flushing.
- `ConsultaViewModelTest`: consultas y procesamiento de información.
- `BebederoRepositoryTest`: comunicación con microservicios, manejo de errores, respaldo local con Room, registros de flushing y notificaciones según el rol.

### Cobertura de código

Resultados verificados mediante JaCoCo:

| Componente | Instrucciones | Ramas |
|---|---:|---:|
| ViewModels | 87 % | 100 % |
| Repository | 97 % | 84 % |
| **Total** | **96 %** | **86 %** |

La cobertura corresponde a las clases incluidas en la configuración de JaCoCo.

### Ejecutar las pruebas

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

### Generar el informe de cobertura

```powershell
.\gradlew.bat :app:jacocoTestReport
```

El informe HTML se genera en:

`app/build/reports/jacoco/jacocoTestReport/html/index.html`
## Documentación

- [Aplicación Android](Bebedero-Android/README.md)
- [Microservicio de infraestructura](ms-infraestructura/README.md)
- [Microservicio de flushing](ms-flushing/README.md)

## Rama de integración

La versión de entrega se mantiene en la rama `integracion-oracle`.
