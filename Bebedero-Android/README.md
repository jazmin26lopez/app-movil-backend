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

Las pruebas Android utilizan JUnit4, MockK y kotlinx-coroutines-test.

En la ejecución local verificada, JaCoCo informó:

- Cobertura de instrucciones: **96 %**.
- Cobertura de ramas: **86 %**.

Los porcentajes corresponden a las clases incluidas en la configuración de JaCoCo.

## Documentación

- [Aplicación Android](Bebedero-Android/README.md)
- [Microservicio de infraestructura](ms-infraestructura/README.md)
- [Microservicio de flushing](ms-flushing/README.md)

## Rama de integración

La versión de entrega se mantiene en la rama `integracion-oracle`.
