# MediRecordatorio - Sistema de Gestión y Recordatorio de Medicamentos

Aplicación web y API REST desarrollada en **Java 17** con **Spring Boot 3**, **Spring Data JPA**, **MySQL** y **Project Reactor** para la gestión de prescripciones médicas, pacientes, médicos y alertas reactivas de medicación.

---

## Tecnologías Utilizadas
- **Backend:** Java 17, Spring Boot 3.2.5 (Spring Web, Spring Data JPA, Spring Validation, Spring AOP)
- **Programación Reactiva:** Project Reactor (`reactor-core` - `Flux`, `Mono`, `BaseSubscriber`)
- **Base de Datos:** MySQL
- **Pruebas de API:** Postman

---

## Semana 13 - API CRUD con Spring Boot y Postman

### Funcionalidad implementada: Gestión de Medicamentos (`/api/medicamentos`)
Exposición de endpoints RESTful para administrar el ciclo de vida de los medicamentos en formato JSON.

| Método | Endpoint | Descripción | Códigos HTTP |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/medicamentos` | Lista todos los medicamentos registrados | `200 OK` |
| `GET` | `/api/medicamentos/{id}` | Consulta un medicamento por su ID | `200 OK`, `404 Not Found` |
| `POST` | `/api/medicamentos` | Registra una nueva prescripción médica | `201 Created`, `400 Bad Request` |
| `PUT` | `/api/medicamentos/{id}` | Actualiza los datos de un medicamento | `200 OK`, `400 Bad Request`, `404 Not Found` |
| `DELETE` | `/api/medicamentos/{id}` | Elimina un medicamento por su ID | `204 No Content`, `404 Not Found` |

---

## Semana 14 - Estándares REST y Buenas Prácticas

### Arquitectura y Mejoras Implementadas
- **Separación de responsabilidades:**
    - `MedicamentoRestController`: Gestión de rutas plurales (`/api/medicamentos`), cabeceras HTTP y códigos de estado.
    - `MedicamentoRestService`: Lógica de negocio, mapeo de entidades y validaciones de existencia.
- **Modelos de Entrada y Salida (DTOs):**
    - **Entrada (`MedicamentoRequestDTO`):** Recibe los datos del medicamento (`nombre`, `dosis`, `hora`, `frecuencia`, `pacienteId`, `medicoId`) sin identificador.
    - **Salida (`MedicamentoResponseDTO`):** Retorna la representación del recurso con su `id` generado y los nombres asociados.
- **Estándares de Respuesta HTTP:**
    - `200 OK`: Consultas y actualizaciones exitosas.
    - `201 Created`: Creación exitosa incluyendo la cabecera `Location` con la URI del recurso creado (`/api/medicamentos/{id}`).
    - `204 No Content`: Eliminación exitosa sin cuerpo en la respuesta.
    - `400 Bad Request`: Campos obligatorios vacíos (`@NotBlank`, `@NotNull`) o referencias inexistentes.
    - `404 Not Found`: Solicitudes sobre identificadores que no existen en base de datos.

### Evidencias y Ejemplos de Pruebas (Postman)

**1. Creación (`POST /api/medicamentos`) -> `201 Created`**
- **Cabeceras de solicitud:** `Content-Type: application/json`
- **Entrada (JSON):**
```json
{
  "nombre": "Losartan",
  "dosis": "50 mg",
  "hora": "07:00 AM",
  "frecuencia": "Cada 24 horas",
  "pacienteId": 1,
  "medicoId": 2
}
```

## Semana 16 - Spring WebFlux, Streaming SSE y WebClient

### Arquitectura del Módulo Reactivo (`semana16-webflux`)
Se implementó un módulo reactivo independiente sobre Netty (`spring-boot-starter-webflux` en puerto `8085`) conservando intacta la aplicación principal Spring MVC + API REST (`/api/medicamentos` en puerto `8080`).

### Endpoints Implementados
| Método | Endpoint | Tipo Retorno | Descripción |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/reactive/saludo/{paciente}` | `Mono<String>` | Emite un saludo individual confirmando el monitoreo del paciente |
| `GET` | `/api/v1/reactive/alertas-dosis` | `Flux<String>` (`text/event-stream`) | Emite progresivamente 5 alertas de dosis (1 por segundo) usando `interval()`, `take(5)` y `map()` |
| `GET` | `/api/v1/reactive/estado-farmacia` | `Mono<String>` | Consume mediante `WebClient` el servicio `/api/v1/fuente/estado-farmacia` con `timeout(3s)` y `onErrorResume()` |

### Ejecución y Pruebas
```bash
cd semana16-webflux
mvn spring-boot:run
curl -i http://localhost:8085/api/v1/reactive/saludo/Carlos
curl -N http://localhost:8085/api/v1/reactive/alertas-dosis
curl -i http://localhost:8085/api/v1/reactive/estado-farmacia