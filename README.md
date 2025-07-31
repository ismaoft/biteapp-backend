# BiteApp - Backend 🍽️

Este es el backend de **BiteApp**, una aplicación móvil de pedidos y entregas desarrollada como proyecto académico en la Universidad Nacional de Costa Rica.

El backend está construido con **Kotlin + Spring Boot**, usando **Gradle** como sistema de construcción. Gestiona la lógica de negocio, autenticación, manejo de usuarios, restaurantes, productos, pedidos, IA de recomendación y más.

---

## 🚀 Tecnologías utilizadas

- Kotlin 1.9
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Firebase Admin SDK
- OpenAI API (para recomendaciones)
- Gradle Kotlin DSL
- Heroku (despliegue)

---

## 📁 Estructura del proyecto

```
src/
├── main/
│   ├── kotlin/com/example/bite_api/
│   │   ├── controllers/
│   │   ├── models/
│   │   ├── repositories/
│   │   ├── services/
│   │   └── strategies/
│   ├── resources/
│       └── application.properties
├── test/
```

---

## ⚙️ Configuración e instalación local

1. Clona el repositorio:

```bash
git clone https://github.com/ismaoft/biteapp-backend.git
cd biteapp-backend
```

2. Configura tu archivo `.env` (ver más abajo)

3. Construye el proyecto:

```bash
./gradlew build
```

4. Corre el servidor:

```bash
./gradlew bootRun
```

---

## 🔐 Variables de entorno

Crea un archivo `.env` en la raíz del proyecto o configura estas variables en tu entorno local:

```env
OPENAI_API_KEY=tu_clave_openai
FIREBASE_CREDENTIALS=path/al/archivo.json
DATABASE_URL=jdbc:postgresql://localhost:5432/biteapp
DATABASE_USERNAME=usuario
DATABASE_PASSWORD=contraseña
```

También puedes definirlas directamente en `application.properties`.

---

## 🔗 API REST (ejemplo)

| Método | Endpoint                | Descripción               |
|--------|-------------------------|---------------------------|
| GET    | /api/restaurantes       | Lista de restaurantes     |
| POST   | /api/usuarios           | Registro de usuario       |
| POST   | /api/login              | Login de usuario          |
| GET    | /api/productos/{id}     | Obtener producto por ID   |
| POST   | /api/recomendaciones    | Recomendación con IA      |

---

## 🎓 Contexto académico

> Este proyecto fue desarrollado por **Ismael Salazar Blanco** como parte del curso *Desarrollo Móvil* en la Universidad Nacional de Costa Rica (2024). Aunque el proyecto fue originalmente en equipo, el backend completo fue implementado de forma independiente como práctica integral de desarrollo profesional.

---

## 🙌 Créditos y contacto

Desarrollado por: **Ismael Salazar Blanco**  
Portafolio: [https://ismael-salazar-blanco-portfolio.onrender.com](https://ismael-salazar-blanco-portfolio.onrender.com)  
Correo: ismaoft@gmail.com

---

