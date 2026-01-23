🧱 Project Overview

This project demonstrates a Spring Cloud–based microservices architecture with:

API Gateway

Service Discovery

Synchronous REST communication

Asynchronous JMS-based messaging

Centralized DTOs

Production-style service boundaries



📦 Services Overview

🔹 api-gateway

Spring Cloud Gateway

Single entry point for all external requests

Routes traffic to backend services using service discovery

Hides internal service ports

Applies cross-cutting concerns like routing and security

🔹 eureka-server

Service Discovery (Netflix Eureka)

Central service registry

All microservices register themselves here

API Gateway uses Eureka for dynamic routing

Enables load balancing and service decoupling

🔹 CustomerAccountService

Customer & Wallet Management Service

Manages customer accounts and wallet balances

Exposes REST APIs for customer operations

Initiates wallet top-up requests

Publishes top-up requests to Payment Service via ActiveMQ (JMS)

Consumes top-up result events and updates wallet balances accordingly

🔹 PaymentService

Payment Processing Service

Processes wallet top-up requests asynchronously

Listens to top-up requests from ActiveMQ

Persists top-up transactions with status (SUCCESS / FAILED)

Publishes top-up result events back to CustomerAccountService

Acts as the source of truth for payment transactions

🔹 PaymentSchedulerService

Background / Scheduled Payment Tasks

Handles scheduled or delayed payment-related jobs

Useful for retries, reconciliation, or future payment workflows

Demonstrates background processing in a microservice architecture

🔹 OrderService

Order Management Service

Handles order creation and lifecycle

Coordinates with other services through API Gateway

Demonstrates synchronous REST-based service communication

Serves as an example of business orchestration

🔹 common-dto

Shared DTO Module

Contains common request/response and event DTOs

Used across services for REST and JMS communication

Prevents duplication and ensures contract consistency

Includes JMS message payloads like TopUpRequest and TopUpResult
