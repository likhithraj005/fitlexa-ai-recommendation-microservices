# Fitlexa – AI Recommendation Fitness Microservices

Fitlexa is a production‑ready microservices‑based fitness application that tracks user activities and provides AI‑powered workout recommendations using an event‑driven architecture.

This project demonstrates real‑world backend engineering practices including microservices, API Gateway, service discovery, centralized configuration, event‑driven communication, authentication, and cloud deployment.

---

# Architecture Overview

## Architecture Diagram
![Architecture Diagram](https://github.com/likhithraj005/fitlexa-ai-recommendation-microservices/blob/main/Microservices-Architecture-Diagram.png)

This diagram shows the complete microservices architecture including API Gateway, Eureka, Config Server, User Service, Activity Service, AI Service, RabbitMQ, MongoDB, Keycloak, and the React frontend.

## Infrastructure Services:

- **Eureka Server (Service Discovery)** – Enables dynamic service registration and discovery for microservices.
- **Config Server (Centralized Configuration)** – Implemented Spring Cloud Config Server to centralize configuration management by fetching application configuration files from a GitHub repository and providing them to microservices.
- **Keycloak (Authentication & Authorization)** – Deployed Keycloak as a centralized identity provider to handle secure authentication and authorization using OAuth2 and OpenID Connect. Containerized with Docker, hosted on Render, and integrated with PostgreSQL on Neon for persistent storage of users, roles, and authentication(realm) data.
- **API Gateway (Centralized Routing & Security)** – – Implemented a centralized API Gateway to intercept and route all incoming client requests to the appropriate microservices. Each microservice runs as an independent service, and the gateway provides a single entry point for the frontend, enabling centralized routing, security by integrates with Keycloak for JWT-based authentication, and abstraction of internal service endpoints.

---

# Tech Stack

## Backend

- Java 21
- Spring Boot  
- Spring Cloud Gateway  
- Spring Security  
- Spring Data JPA (PostgreSQL integration)  
- Spring Data MongoDB  
- Spring Cloud Netflix Eureka (Service Discovery)  
- Spring Cloud Config Server (Centralized Configuration)  
- Keycloak (OAuth2 & OpenID Connect Authentication)  
- RabbitMQ (Event-Driven Communication)  
- WebClient (Inter-service communication)  
- Resilience4j (Circuit Breaker & Fault Tolerance)  
- Lombok (Boilerplate code reduction)  

## Frontend

- React
- Vite
- Axios
- Redux Toolkit
- Material UI (MUI)
- React Router
- OAuth2 PKCE Client

## Database

- MongoDB 
- PostgreSQL

## AI Integration

• Google Gemini API

## Deployment

- **Docker** – Containerized microservices using Docker with **Buildpacks and Google Jib** for efficient image creation without requiring Dockerfiles.

- **Render** – Deployed Spring Boot microservices, API Gateway, Eureka Server, Config Server, and Keycloak for scalable cloud hosting.

- **Netlify** – Deployed the React frontend with continuous deployment and global CDN support.

- **Neon (PostgreSQL)** – Used as the managed relational database for persistent storage of user and authentication-related data.

- **MongoDB Atlas** – Used as the cloud-hosted NoSQL database for storing activity and AI-related data.

- **CloudAMQP (Managed RabbitMQ)** – Used CloudAMQP to deploy and manage RabbitMQ message broker in the cloud, enabling reliable asynchronous communication between Activity Service and AI Recommendation Service.

---

## Microservices Description

The system follows a distributed microservices architecture where each service is responsible for a specific business capability. Services communicate via REST and event-driven messaging using RabbitMQ.

![Architecture schema](https://github.com/likhithraj005/fitlexa-ai-recommendation-microservices/blob/main/fitlexa-schema.png)

---

### 1. API Gateway

**Responsibility:**  
Acts as the single entry point for all client requests.

**Key Features:**
- Routes requests to appropriate microservices
- Integrates with Keycloak for JWT authentication
- Uses Eureka Server for dynamic service discovery
- Provides centralized security and request filtering

**Technology:**  
Spring Cloud Gateway

---

### 2. User Service

**Responsibility:**  
Manages user profile and user-related operations.

**Database:** PostgreSQL (Neon)

**Main Functions:**
- Store and manage user information
- Retrieve user profile details
- Validate user existence for activities and recommendations

**Database Table:**
`users`

**Schema:**
- id (PK)
- email
- first_name
- last_name
- password
- role
- created_at
- updated_at

**Technology:**
- Spring Boot
- Spring Data JPA
- PostgreSQL

---

### 3. Activity Service

**Responsibility:**  
Manages user fitness activities.

**Database:** MongoDB Atlas

**Main Functions:**
- Create and store activity records
- Track activity metrics such as duration and calories burned
- Publish activity events to RabbitMQ for AI processing

**Database Table / Collection:**
`activities`

**Schema:**
- id (PK)
- user_id (FK → users.id)
- type
- duration
- calories_burned
- additional_metrics (JSON)
- start_time
- created_at
- updated_at

**Technology:**
- Spring Boot
- Spring Data MongoDB
- RabbitMQ Producer

---

### 4. AI Recommendation Service

**Responsibility:**  
Generates AI-powered fitness recommendations based on user activities.

**Database:** MongoDB Atlas

**Main Functions:**
- Consume activity events from RabbitMQ
- Process activity data
- Call external AI service (Gemini API)
- Generate personalized recommendations
- Store recommendations

**Database Table / Collection:**
`recommendations`

**Schema:**
- id (PK)
- activity_id (FK → activities.id)
- user_id (FK → users.id)
- type
- recommendation
- suggestions (JSON)
- safety (JSON)
- improvements (JSON)
- created_at

**Technology:**
- Spring Boot
- Spring Data MongoDB
- RabbitMQ Consumer
- WebClient (Gemini API integration)

---

### 5. Eureka Server

**Responsibility:**  
Service discovery and registration.

**Key Features:**
- Registers all microservices
- Enables dynamic service lookup
- Eliminates hardcoded service URLs

**Technology:**
Spring Cloud Netflix Eureka

---

### 6. Config Server

**Responsibility:**  
Centralized configuration management.

**Key Features:**
- Fetches configuration from GitHub repository
- Provides configuration to all microservices
- Supports environment-specific configs

**Technology:**
Spring Cloud Config Server

---

### 7. Keycloak Authentication Server

**Responsibility:**  
Authentication and authorization.

**Key Features:**
- OAuth2 and OpenID Connect authentication
- JWT token generation and validation
- Role-based access control

**Database:** PostgreSQL (Neon)

**Technology:**
- Keycloak
- Docker
- Render Deployment

---

### 8. RabbitMQ Message Broker

**Responsibility:**  
Event-driven communication between services.

**Event Flow:**
1. Activity Service publishes activity event
2. AI Service consumes event
3. AI Service generates recommendation
4. Recommendation stored in database

**Benefits:**
- Loose coupling
- Asynchronous processing
- Scalability

---

### 9. External AI Integration (Gemini)

**Responsibility:**  
Provides AI-powered fitness recommendations.

**Integration:**
- Called from AI Recommendation Service using WebClient
- Processes activity data
- Returns structured recommendations

---

## Service Communication Flow

### Synchronous Request Flow (REST)

Client → API Gateway → User Service → PostgreSQL  
Client → API Gateway → Activity Service → MongoDB  
Client → API Gateway → AI Recommendation Service → MongoDB  

### Event-Driven Flow (Asynchronous)

Client → API Gateway → Activity Service → User Service → PostgreSQL (validate user)  
Activity Service → MongoDB (store activity)  
Activity Service → RabbitMQ (publish event)  
AI Recommendation Service → RabbitMQ (consume event)  
AI Recommendation Service → MongoDB (store recommendation)

- Activity Service first validates the user by calling the User Service
- User Service verifies user existence from PostgreSQL
- Activity Service stores the activity in MongoDB
- Activity Service publishes activity event to RabbitMQ
- AI Recommendation Service consumes the event from RabbitMQ
- AI Service fetches activity data and generates recommendations using Gemini API
- Recommendations are stored in MongoDB

### Authentication Flow (OAuth2 + JWT)

Client → API Gateway → Keycloak → JWT Token  
Client → API Gateway → Microservices (JWT validated)

- User authenticates via Keycloak
- Keycloak issues JWT token
- API Gateway validates JWT
- Requests are forwarded to microservices

---

# Event‑Driven Flow

- Step 1: User creates activity
- Step 2: Activity Service stores activity in MongoDB
- Step 3: Activity Service publishes event to RabbitMQ
- Step 4: AI Service consumes event
- Step 5: AI Service generates recommendation using Gemini
- Step 6: Recommendation stored in MongoDB
- Step 7: Frontend fetches recommendation via Gateway

---

# Security Flow

- User logs in via Keycloak
- Keycloak issues JWT token
- Gateway validates token
- Gateway forwards request to services

---

## Responsibilities Summary

### User Service Responsibilities

- Manage user profile and user-related data
- Store user information in PostgreSQL
- Validate user existence for other microservices
- Provide user data via REST APIs

### Activity Service Responsibilities

- Validate user via User Service
- Store activity data in MongoDB
- Publish activity events to RabbitMQ

### AI Recommendation Service Responsibilities

- Consume activity events from RabbitMQ
- Generate AI recommendations using Gemini API
- Store recommendations in MongoDB

---

# Project Deployment Flow

## Overview
This project is a microservices-based architecture for a fitness recommendation system with AI integration. It uses PostgreSQL, MongoDB, RabbitMQ, Keycloak for authentication, and a React frontend hosted on Netlify. All backend services are containerized with Docker and deployed on Render.
![Fitlexa.com](https://github.com/likhithraj005/fitlexa-ai-recommendation-microservices/blob/main/fitlexa.png)

---

## Deployment Flow

### 1. Databases
- **PostgreSQL** hosted on **Neon**  
- **MongoDB** hosted on **MongoDB Atlas**  

### 2. Message Broker
- **RabbitMQ** deployed on **CloudAMQP**

### 3. Backend Services
All backend services are deployed on **Render** using Docker containers:

- **Config Server** – Centralized configuration for all microservices
  [https://config-deployment-fitness-latest.onrender.com](https://config-deployment-fitness-latest.onrender.com)

- **Eureka Server** – Service discovery for microservices
  [https://eureka-server-fitness-latest.onrender.com](https://eureka-server-fitness-latest.onrender.com)
  
- **User Service** – Handles user management and authentication
  [https://userservice-deployment-fitness-latest.onrender.com](https://userservice-deployment-fitness-latest.onrender.com)
  
- **Activity Service** – Manages fitness activities and recommendations
  [https://activityservice-deployment-fitness-latest.onrender.com](https://activityservice-deployment-fitness-latest.onrender.com)

- **AI Service** – Provides AI-powered fitness recommendations
  [https://aiservice-deployment-fitness-latest.onrender.com](https://aiservice-deployment-fitness-latest.onrender.com)
  
- **Keycloak** – Identity and access management
  [https://keycloak-txe2.onrender.com](https://keycloak-txe2.onrender.com)
  
- **API Gateway** – Routes requests to appropriate microservices
  [https://gateway-deployment-fitness-latest.onrender.com](https://gateway-deployment-fitness-latest.onrender.com)

**API Documentation:**  
All backend APIs are fully documented and can be explored via **Postman**: [https://documenter.getpostman.com/view/29635048/2sBXcHiyj9](https://documenter.getpostman.com/view/29635048/2sBXcHiyj9)  

### 4. Frontend
- **React Application** hosted on **Netlify**: [https://fitlexa.netlify.app](https://fitlexa.netlify.app)

---

# Demo Credentials:
- Username: user1
- Password: password1
  
Feel free to register your own, but using these demo creds saves some database space on my free-tier hosting. 😉

---

# Key Features

- Microservices architecture
- Event‑driven system using RabbitMQ
- AI‑powered recommendations
- Secure authentication with Keycloak
- Centralized config management
- Service discovery
- API Gateway routing
- Cloud deployment

---

# Learning Outcomes

This project demonstrates:

- Microservices architecture design
- Event‑driven communication
- Secure authentication
- Cloud deployment
- Distributed system design
- Production‑level backend development

---

# Future Improvements

- Kubernetes deployment
- Distributed tracing (Zipkin)
- Monitoring (Prometheus, Grafana)
- CI/CD pipeline
- Rate limiting

---
