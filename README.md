# E-Library-API

E-Library-API is the backend RESTful API for an online library system that supports both **admin** and **user** functionalities. The API is designed to handle everything from user authentication to book management, reviews, and cart functionality.

## Project Goals

To provide a unified and secure API where both users and administrators can:
- Register and log in
- Manage books (admins)
- View and interact with books (users)
- Manage personal profiles
- Write and view book reviews
- Use a virtual cart system

## Features

### AuthService
Handles user authentication:
- `signUpUser`: Registers a new user account.
- `loginUser`: Logs in the user and returns a JWT token.

### AdminService
Admin-specific book management:
- `addBooks`: Adds new books to the system.
- `updateBooks`: Updates book information.
- `updateBooksAvailable`: Changes the availability status of a book.
- `deleteBooks`: Removes a book from the system.

### BooksService
Book browsing and pagination:
- `getBooksPagination`: Returns a paginated list of books.

### CartsService
Virtual cart operations:
- `getBookFromCart`: Retrieves books from a user's cart.
- `addBookToCart`: Adds a book to the cart.
- `deleteBookFromCart`: Removes a book from the cart.

### ReviewsService
Book review functionality:
- `showReviews`: Displays reviews for a selected book.
- `addReview`: Allows a user to write a review.

### UsersService
User profile management:
- `getUserAccount`: Retrieves user's profile information.
- `editUserAccount`: Allows the user to update their profile.
- `changeUserPassword`: Lets the user change their password.
- `deleteUserAccount`: Deletes the user's account.

## Technologies Used

- **Java 17**: Main programming language.
- **Spring Boot**: Backend framework for building REST APIs.
- **Spring Security**: Handles security, authentication, and authorization.
- **JWT (JSON Web Token)**: For secure token-based user authentication.
- **Spring Data JPA**: For working with relational data.
- **PostgreSQL**: Relational database for storing books, users, etc.
- **MongoDB**: NoSQL database for storing reviews or cart data.
- **Pagination**: For efficient browsing of large book collections.
- **Swagger UI**: For interactive API documentation.
- **JUnit & Mockito**: For unit and integration testing.

## API Documentation

You can view and test the API endpoints using **Swagger UI** once the application is running:

