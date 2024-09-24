
## Project Overview

The BoltoMart backend application serves as the core of our multi-vendor e-commerce platform, BoltoMart. This platform is designed to facilitate fast delivery of electronic and hardware products through various vendors, similar to services like Swiggy and Zomato but focused on electronics.

Built using Java Spring Boot, the application follows a microservice architecture and leverages AWS RDS for MySQL database management to ensure scalability and reliability.

## Technology Stack

The project utilizes the following technologies:

Java Spring Boot: Framework for building and deploying microservices.

AWS RDS MySQL: Managed relational database service for reliable and scalable database solutions.

## Microservices Architecture

The BoltoMart application is built using a microservices architecture to ensure scalability, flexibility, and maintainability. The following services are integral to the system:

### **Auth Service** (`Port: 8081`)
Responsible for managing authentication and authorization for both vendors and customers. This service handles user login, registration, and token management to ensure secure access to the platform.

### **Vendor Service** (`Port: 8084`)
Dedicated to managing vendor-related operations, this service handles vendor registration, profile management, and vendor-specific functionalities such as product listings and order fulfillment.

### **Customer Service** (`Port: 8082`)
Manages customer-related operations, including customer registration, profile management, and preferences. This service also handles interactions between customers and the platform, ensuring a seamless user experience.

### **Cart Service** (`Port: 8085`)
Responsible for managing customer shopping carts. This service handles cart creation, item addition and removal, and cart updates, ensuring that customer cart data is accurately maintained throughout their shopping journey.

### **Order Service** (`Port: 8086`)
Facilitates order processing, including order creation, tracking, and management. This service ensures that orders are correctly processed and communicated to both vendors and customers.

### **Notification Service**
Manages the sending of notifications via email and SMS to admins, customers, and vendors. This service handles alerts, order confirmations, and other important communications to keep all stakeholders informed.

### **Product Service** (`Port: 8083`)
Dedicated to managing product-related operations. This service handles product information, inventory management, and product-related queries, ensuring that product data is up-to-date and accessible.


## API Documentation

Detailed API documentation for the BoltoMart backend application is available in the Google Doc linked below. This documentation provides comprehensive information about the API endpoints, request and response formats, and example usage.

- **API Documentation**: [View API Documentation](https://docs.google.com/document/d/1QFuJw2Qg0L6cFGXVyDb0ka3-fv1rDRa4o344gsu4KAQ/edit?usp=sharing)


---------
## Installation

 **Clone the repository:**

```bash
git clone <repository-url>
```
    
**Navigate to the project directory:**

```bash
cd javacode-backend
```

**Clone the repository:**

```bash
git clone <repository-url>
```

**Run the application:**

You can run the application using one of the following methods:

Using IntelliJ IDEA:
 - Open the project in IntelliJ IDEA.
 - Locate the `Application` class containing the `main` method.
 - Right-click on the `Application` class and select **Run**.

Using Eclipse:
   - Import the project into Eclipse as a Maven project.
   - Locate the `Application` class containing the `main` method.
   - Right-click on the `Application` class and select **Run As > Java Application**.






