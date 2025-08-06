# CareSync – A Unified Digital Healthcare Platform

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE.txt)

**CareSync** is a secure, web-based digital healthcare platform designed to unify key stakeholders in the healthcare ecosystem: patients, doctors, pharmacies, and laboratories. This repository contains the source code and documentation for a microservice-based system that aims to reduce medical errors, eliminate duplication of prescriptions and lab tests, and improve care coordination through a patient-controlled, consent-driven model.

---

## 1. Introduction

This repository holds the complete implementation of **CareSync**, a full-stack web application developed as a capstone project for a Bachelor of Science in Software Engineering. The platform enables seamless, secure, and real-time data sharing across independent healthcare providers, addressing fragmentation in the current system. It features role-based access control, encrypted medical records, intelligent prescription fulfillment, and centralized administration.

The project is built using a **microservice architecture** to ensure scalability, maintainability, and independent deployment of components.

---

## 2. Problem Trying to Solve

In many healthcare systems, including Sri Lanka’s, stakeholders such as doctors, patients, pharmacies, and laboratories operate in silos. This fragmentation leads to:
- **Medical errors** due to incomplete patient histories.
- **Duplication of diagnostic tests and prescriptions**.
- **Inefficiencies** in communication and data sharing.
- **Lack of patient control** over their own health data.

CareSync addresses these issues by providing a **unified digital platform** where all authorized parties can securely access and contribute to a patient’s medical journey — only with the patient’s explicit consent.

---

## 3. Development Details

### Architecture

CareSync follows a **microservice architecture**, with each service handling a specific domain. Services communicate via REST APIs and are orchestrated using an **API Gateway**. The system uses **Eureka Service Discovery** and **Spring Cloud Config** for dynamic service management and centralized configuration.

Authentication is handled through **JWT tokens**, and all sensitive medical data is encrypted at rest using **AES-256**. Patient consent is required before any doctor or authorized personnel can access medical records.

#### Microservices Overview

| Service | Technology Stack | Database |
|-------|------------------|----------|
| **Patient Service** | Spring Boot (Java 21) | PostgreSQL |
| **Medical Record Service** | Spring Boot (Java 21) | PostgreSQL |
| **Doctor Service** | Spring Boot (Java 21) | PostgreSQL |
| **Look Up Service** | Express.js (Node.js) | MongoDB |
| **Pharmacy Service** | Express.js (Node.js) | MongoDB |
| **Laboratory Service** | Express.js (Node.js) | MongoDB |
| **Notification Service** | Express.js (Node.js) | MongoDB |
| **Auth Service** | Spring Boot (Java 21) | MongoDB |
| **Administrator Service** | Spring Boot (Java 21) | PostgreSQL |

### Tech Stack

- **Frontend**: React.js (Responsive UI with role-based dashboards)
- **Backend**:
    - Spring Boot (Java 21) for core services
    - Express.js (Node.js) for lightweight inventory and lookup services
- **Databases**:
    - PostgreSQL (structured data: users, medical records, etc.)
    - MongoDB (flexible data: inventories, logs)
- **Infrastructure**:
    - **API Gateway**: Spring Cloud Gateway
    - **Service Discovery**: Netflix Eureka
    - **Configuration Server**: Spring Cloud Config
    - **Containerization**: Docker & Docker Compose
    - **Version Control**: Git / GitHub
- **Third-Party Integrations**:
    - Google Maps Platform (for pharmacy/lab distance calculations)
    - Email notifications (via Nodemailer or SMTP)

---

## 4. Future Goals

- ✅ **Mobile App Development**: Build a **React Native** mobile application to extend access to smartphones and tablets.
- 🏥 **Institutional Integration**: Add support for hospitals, clinics, and government health departments to integrate with the platform.
- 🔐 **FHIR Compliance**: Implement **Fast Healthcare Interoperability Resources (FHIR)** standards for national and global interoperability.
- 📊 **Analytics Dashboard**: Provide administrators with insights into system usage, verification trends, and medicine availability.
- 🌍 **Multilingual Support**: Add Sinhala and Tamil language options to improve accessibility.

---

## 5. License

This project is licensed under the **MIT License** – see the [LICENSE.txt](LICENSE.txt) file for details.