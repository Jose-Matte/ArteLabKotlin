 ArteLab SPA – Aplicación Móvil Android

ArteLab SPA es una aplicación móvil desarrollada en **Kotlin + Jetpack Compose**, orientada a la gestión y visualización de productos y servicios de arte y bienestar.  


##  Objetivo del Proyecto
Desarrollar una aplicación móvil completamente funcional que integre:
- Arquitectura moderna (MVVM)
- Consumo de microservicios propios
- Consumo de una API externa
- Persistencia local
- Uso de recursos nativos
- Validaciones, animaciones y pruebas unitarias

##  Arquitectura
La aplicación utiliza el patrón MVVM (Model – View – ViewModel):

- UI (Screens / Composables):
  Interfaces desarrolladas con Jetpack Compose y Material Design 3.
- ViewModel: 
  Manejo de estado usando `StateFlow` y lógica desacoplada de la UI.
- Repository: 
  Abstracción entre la UI y las fuentes de datos (API, base de datos local).
- Data: 
  - Remote (Retrofit + APIs)
  - Local (SessionManager / Room / DataStore)


 Estructura del Proyecto
com.localgo.artelabspa
│
├── data
│ ├── local → Persistencia local (SessionManager, Room)
│ ├── remote → Retrofit, APIs y DTOs
│ └── repository → Repositorios
│
├── model → Modelos de dominio
├── ui
│ ├── screens → Pantallas (Login, Home, Productos, Perfil)
│ ├── components → Componentes reutilizables
│ └── navigation → Navegación
│
├── viewmodel → Lógica de negocio
└── utils → Validaciones y utilidades

  Funcionalidades Implementadas

### Autenticación
- Login con validación de campos
- Acceso como invitado
- Persistencia de sesión local

###  CRUD de Productos
- **Create:** Crear producto
- **Read:** Listar productos
- **Update:** Editar producto
- **Delete:** Eliminar producto  
Integrado mediante microservicios backend.

###  Consumo de API Externa
- API de **Clima**
- Consumo mediante Retrofit
- Visualización integrada en HomeScreen

###  Recursos Nativos
- Galería de imágenes
- Ubicación del dispositivo
- Gestión de permisos en tiempo real

### Persistencia Local
- Almacenamiento de sesión
- Uso de Room / DataStore según contexto

###  Animaciones
- Animación simple en pantalla de Login
- Mejora de experiencia visual y feedback

###  Pruebas Unitarias
- Pruebas en ViewModel y Utils
- Uso de JUnit y MockK

##  Tecnologías Utilizadas
- Kotlin
- Jetpack Compose
- Material Design 3
- Retrofit
- Coroutines + StateFlow
- Room / DataStore
- MockK / JUnit
- GitHub / Trello


##  APK Firmado
El proyecto incluye:
- Archivo `.jks`
- APK firmado correctamente
- Configuración documentada en Gradle

## Trabajo Colaborativo
- Repositorio público en GitHub
- Commits individuales
- Planificación en Trello

 Autor
**Luciano José Picinini**  
Jose Miguel Matte
Ingeniería en Informática – DUOC UC  
2025

Cómo ejecutar el proyecto:
Requisitos previos--> 

- Android Studio (versión recomendada: Hedgehog o superior)
- JDK 11
- Gradle (incluido en Android Studio)
- Emulador Android o dispositivo físico
- Conexión a Internet (para consumo de APIs)

## Clonar el repositorio
git clone https://github.com/Luchopicinini/ArteLabSpa_.git

## Abrir el proyecto

Abrir Android Studio
- Seleccionar Open an existing project
- Elegir la carpeta ArteLabSpa_
- Esperar la sincronización de Gradle

  
