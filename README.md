# Bebedero — Monitoreo de Bebederos

Proyecto desarrollado para la asignatura **DSY1105 — Desarrollo de Aplicaciones Móviles**, Duoc UC.

## Integrantes

- Bray Arciniegas
- Jazmin López

## Descripción

Bebedero es una solución de monitoreo de temperatura del agua en líneas de bebederos de granjas avícolas.

Permite consultar granjas, galpones y líneas de bebederos, identificar alertas de temperatura y registrar limpiezas (*flushing*).

El sistema está compuesto por una aplicación Android desarrollada con Kotlin y Jetpack Compose, conectada mediante Retrofit a dos microservicios Spring Boot que utilizan Oracle Database.

Además, incorpora Room para almacenamiento local y Open-Meteo para consultar la temperatura ambiente.

## Estructura del proyecto

| Carpeta | Descripción |
|---|---|
| `Bebedero-Android/` | Aplicación móvil Kotlin, Jetpack Compose y MVVM |
| `ms-infraestructura/` | Microservicio de granjas, galpones, líneas y temperaturas; puerto 8081 |
| `ms-flushing/` | Microservicio de registros de limpieza; puerto 8082 |

## Roles

**Operario:** consulta temperaturas y alertas, registra limpiezas y revisa su historial.

**Supervisor:** consulta indicadores generales, eventos críticos, historiales de temperaturas y registros de flushing.

## Funcionalidades principales

- Monitoreo de temperaturas de líneas de bebederos.
- Clasificación de temperaturas en Normal, Advertencia y Crítico.
- Registro, consulta, actualización y eliminación de flushings.
- Consulta de historial de temperaturas.
- Notificaciones locales.
- Persistencia mediante Oracle.
- Respaldo local mediante Room.
- Consulta de temperatura ambiente mediante Open-Meteo.
- Arquitectura MVVM.
- Comunicación REST mediante Retrofit.

## Pruebas unitarias

Las pruebas Android utilizan JUnit4, MockK y kotlinx-coroutines-test.

57 pruebas en total. Resultados verificados con JaCoCo:

| Componente | Líneas | Instrucciones | Ramas |
|---|---:|---:|---:|
| ViewModels + Repository | 99.4 % | 96.3 % | 86.5 % |

La cobertura corresponde a las clases incluidas en la configuración de JaCoCo (ViewModels y Repository) y supera el mínimo del 80 % en las tres métricas.

## Instalación

Clonar el repositorio oficial:

```powershell
git clone -b integracion-oracle https://github.com/jazmin26lopez/app-movil-backend.git
```

Configurar Oracle, iniciar los dos microservicios y abrir la carpeta `Bebedero-Android` en Android Studio.

Las instrucciones detalladas se encuentran en los README de cada componente.

## Documentación

- [Aplicación Android](Bebedero-Android/README.md)
- [Microservicio de infraestructura](ms-infraestructura/README.md)
- [Microservicio de flushing](ms-flushing/README.md)

## Repositorio oficial

**GitHub:** https://github.com/jazmin26lopez/app-movil-backend

**Rama de entrega:** `integracion-oracle`

## Planificación

La planificación y distribución de tareas del equipo se realizó mediante el tablero Trello **APP Bebederos**.
