# Loan Application Management System

## Overview
This is a full-stack Java Swing Desktop Application for managing loan applications. Built with MVC architecture, JDBC, and SQLite. It features role-based access control for Users (Applicants) and Admins (Loan Officers).

## Tech Stack
- **Frontend:** Java Swing (Desktop UI)
- **Backend:** Java (OOP, MVC, DAO patterns)
- **Database:** SQLite (Embedded, ZERO server installation required)
- **Connectivity:** JDBC

## Project Folder Structure
```
LoanApplicationSystem/
│── src/
│   ├── main/
│   │   └── Main.java           # Entry point
│   ├── model/
│   │   ├── User.java           # User entity
│   │   └── LoanApplication.java# Loan App entity
│   ├── dao/
│   │   ├── UserDAO.java        # DB operations for users
│   │   └── LoanApplicationDAO.java # DB operations for loans
│   ├── controller/
│   │   ├── AuthController.java # Handles login/register
│   │   └── LoanController.java # Handles loan processes
│   ├── view/
│   │   ├── LoginFrame.java     # Login UI
│   │   ├── RegisterFrame.java  # Registration UI
│   │   ├── UserDashboard.java  # User interface & form
│   │   └── AdminDashboard.java # Admin panel
│   └── utility/
│       ├── DatabaseConnection.java # SQLite JDBC auto-initialization
│       └── ValidationUtil.java # Input & File validation
└── README.md                   # Setup guide
```

## Setup Instructions

Since we are using SQLite, there is **NO NEED to install or configure any database server like MySQL!** The database runs entirely inside your application.

1. **Download the SQLite JDBC Driver:**
   - Download the JAR file from here: [SQLite JDBC Driver](https://github.com/xerial/sqlite-jdbc/releases) (Look for `sqlite-jdbc-x.x.x.x.jar`).

2. **Add to your IDE:**
   - Open your project in Eclipse, IntelliJ, or VSCode.
   - Add the downloaded `sqlite-jdbc.jar` file to your project's Build Path / External Libraries.

3. **Run the Project:**
   - Run the `src/main/Main.java` file.
   - *Note: The very first time you run the project, the app will automatically create a file called `loan_management.db` in your project folder. It will also create all the necessary tables and the default admin account instantly!*

4. **Login:**
   - You can register as a new User.
   - You can login as an Admin using these auto-generated credentials:
     - **Admin Username:** `admin`
     - **Admin Password:** `admin123`

## Features Included
- **Zero Database Setup:** The database auto-generates on the first run.
- **MVC Architecture:** Clean code separation.
- **Authentication:** Role-based login (User/Admin).
- **Validations:** File size limits, extensions, loan amount checks based on income, age validation logic.
- **File Uploads:** Secure path tracking for ID and Income proofs.
- **Dashboards:** Dedicated screens for users to submit/view loans and for admins to approve/reject them.
