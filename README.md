# 🏡 SecureRealty

SecureRealty is a secure full-stack real estate platform that enables realtors and clients to communicate, exchange documents, and manage the home-buying process through a role-based web application. The project emphasizes cybersecurity concepts such as authentication, authorization, encryption, and secure document management.

## Features

### 🔐 Secure Authentication
- JWT-based authentication using Spring Security
- Role-based authorization (Customer, Client, Realtor)
- Protected REST APIs and application routes

### 👥 Customer Onboarding
- New users register as **Customers**
- Customers complete a mortgage pre-approval process
- Successful pre-approval upgrades the user to **Client**, unlocking additional features

### 💬 Real-Time Chat
- WebSocket/STOMP-based messaging between clients and realtors
- Multiple conversation support for realtors
- Messages stored using **AES-GCM encryption**
- Automatic decryption for authorized users during retrieval

### 📄 Secure Document Vault
- Upload and manage PDF documents
- Download shared documents
- JWT-protected document APIs
- Backend architecture prepared for encrypted cloud storage

## Technology Stack

### Frontend
- Flutter Web
- Dart

### Backend
- Spring Boot
- Spring Security
- WebSockets (STOMP)
- REST APIs

### Database
- MongoDB

### Security
- JSON Web Tokens (JWT)
- AES-GCM Encryption
- BCrypt Password Hashing

## System Architecture

```text
Flutter Web
      │
      ▼
Spring Boot REST APIs
      │
      ├──────────────┐
      ▼              ▼
WebSocket/STOMP   MongoDB
      │
      ▼
AES Encryption Service
```

## Project Structure

```
Frontend/
    Flutter Application

Backend/
    Controllers
    Services
    Repositories
    Security
    WebSocket
    Models
```

## Key Security Features

- JWT Authentication
- Role-Based Access Control
- AES-GCM Message Encryption
- BCrypt Password Storage
- Protected REST Endpoints
- Secure WebSocket Communication

## Future Enhancements

- Encrypt documents before storing them in Amazon S3
- AI-powered assistant for mortgage pre-approval and customer onboarding
- Audit logging
- Attribute-Based Access Control (ABAC)
- Cloud deployment
- Notification system for document updates and messages

## Demo

The application demonstrates:

- User registration and login
- Customer-to-client role transition
- Secure real-time messaging
- Encrypted message storage
- Secure document upload and download
- Realtor dashboard supporting multiple client conversations

## Authors

- **Parth Thakur**
- **Daud Masih**
