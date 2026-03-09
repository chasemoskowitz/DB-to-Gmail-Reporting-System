DB-to-Gmail Reporting System
Full-Stack Java Application | Spring Boot | PostgreSQL | Google OAuth 2.0

📌 Project Overview
A professional automated reporting engine that bridges a relational database with the Gmail REST API. This application allows users to persist data via a web dashboard and trigger high-fidelity HTML email reports containing live database records.

Key Technical Features:
OAuth 2.0 Integration: Secure, token-based authorization to interface with Google Mail services.

RESTful API: Engineered with Spring MVC using POST mappings and JSON payloads for asynchronous data handling.

Data Persistence: Managed via PostgreSQL with Hibernate ORM and Spring Data JPA for efficient object-relational mapping.

Security: Configured a custom SecurityFilterChain to manage CSRF protection and secure login flows.

🛠️ Tech Stack
Backend: Java 21, Spring Boot, Spring Security, Hibernate.

Frontend: JavaScript (Fetch API), HTML5, CSS3.

Database: PostgreSQL 18.

Build Tool: Gradle.

APIs: Google Gmail REST API.

🚀 Setup & Installation
1. Clone the Repository
Bash
git clone https://github.com/chasemoskowitz/DB-to-Gmail-Reporting-System.git
cd DB-to-Gmail-Reporting-System-
2. Configure Environment Variables
To keep credentials secure, this project uses environment variables. Set the following on your local machine:

GOOGLE_CLIENT_ID: Your Google Cloud Console Client ID.

GOOGLE_CLIENT_SECRET: Your Google Cloud Console Client Secret.

3. Edit Application Properties
Navigate to src/main/resources/application.properties and update the database configuration to match your local PostgreSQL setup:

Properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db_name
spring.datasource.username=your_postgres_user
spring.datasource.password=your_postgres_password

# Security Placeholders (Do not hardcode secrets here)
spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID}
spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_CLIENT_SECRET}
4. Build and Run
Using Gradle, run the following command to start the server:

Bash
./gradlew bootRun
Access the application at http://localhost:8080.

📂 Project Structure
/lib/src/main/java: Backend logic, Controllers, and JPA Entities.

/lib/src/main/resources: Static frontend assets and application.properties.

/gradle: Build configuration and dependency management.
