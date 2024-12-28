# Sistema de Gestión de Catering

Este proyecto implementa un sistema para gestionar reservas y servicios de catering para eventos, desarrollado en Java. 
El sistema está diseñado siguiendo los principios de la programación orientada a objetos, utilizando abstracción, encapsulamiento, herencia y polimorfismo. 
Además, incluye persistencia de datos sin el uso de bases de datos y opera exclusivamente a través de la consola.

## Funcionalidades Principales

- **Gestión de Clientes**:
  - Almacenar datos personales, como nombre, dirección de entrega, correo electrónico y número de teléfono.
  - Cada cliente tiene un código único de identificación.
- **Gestión de Reservas**:
  - Registro de múltiples reservas asociadas a un cliente.
  - Detalles de las reservas: fecha y hora de inicio y fin, menús seleccionados, precios y estado de los servicios.
- **Gestión de Servicios de Catering**:
  - Información sobre el tipo de servicio, menú, restricciones dietéticas y preferencias.
- **Roles de Usuario**:
  - Clientes: Consultan sus reservas.
  - Coordinadores de eventos: Administran reservas y clientes.
  - Administradores: Gestionan los servicios de catering y usuarios.

## Requisitos Técnicos

- **Lenguaje de programación**: Java
- **Ejecución**: Consola (sin GUI)
- **Persistencia**: Archivos (sin bases de datos)
- **Documentación**:
  - Código fuente documentado con Javadoc.
  - Diagramas UML (casos de uso, clases y secuencia).

## Instalación y Ejecución

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/ManuelaRamdan/sistemaCatering.git
