# app-movil-backend

Backend de microservicios Spring Boot para la app Android **Bebedero** (DSY1105 - Duoc UC).

Dos microservicios independientes, cada uno con su propio esquema Oracle:

- [`ms-infraestructura`](ms-infraestructura/README.md) (puerto 8081) — Granjas, Galpones, Líneas y Temperatura. Responsable: Bray Arciniegas.
- [`ms-flushing`](ms-flushing/README.md) (puerto 8082) — CRUD de registros de flushing. Responsable: Jazmin López.

Documentación de la app Android que los consume: [repositorio Bebedero](https://github.com/jazmin26lopez/Bebedero).
