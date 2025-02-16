# Library-Management-Spring-Boot-backend

A Spring Boot-based Library Management System that allows users to manage books, perform searches, and get AI-generated insights.

##  Features
- **CRUD Operations** for books (Create, Read, Update, Delete)
- **Search books** by title and author
- **AI Insights** for books (via AI Service)
- **Spring Boot WebFlux** for reactive programming
- **Swagger API Documentation** enabled
- **Validation** for book fields
- **H2 In-Memory Database** for easy testing


## Build and Run the Project

### **Prerequisites**
- **Java 17+**
- **Maven 3+**
- **Spring Boot**

### **Clone the Repository**

git clone 
cd library-management

### API Endpoints
```sh
Book Management
Method	         Endpoint	                            Description
POST	            /books	                            Add a new book
GET	              /books	                            Get all books
GET	              /books/{id}	                        Get book by ID
DELETE	          /books/{id}	                        Delete book by ID
GET	              /books/search?title=xxx&author=yyy	Search books
AI Insights
Method	         Endpoint	                            Description
GET	            /books/{id}/ai-insights	              Get AI-generated insights for a book
```


###  Swagger is enabled for easy API testing.

Open http://localhost:8080/swagger-ui.html

### Run Tests
mvn test

### Technologies Used
  Spring Boot 3.3.0
  Spring Data JPA
  Spring WebFlux
  Lombok
  H2 Database (In-Memory)
  Swagger OpenAPI
  Maven
  JUnit 5 for Testing

