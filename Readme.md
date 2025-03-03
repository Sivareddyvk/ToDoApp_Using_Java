To-Do List Application

 Overview

The To-Do List Application is a simple task management system that allows users to add, update, delete, sort, and manage tasks efficiently. The application features a **Java Swing GUI** for user interaction and integrates with **MySQL** for data storage using **JDBC**.

** Features**

- Add Tasks – Users can add tasks with a title, description, and priority level.
- Update Tasks – Modify existing task details.
- Delete Tasks – Remove completed or unwanted tasks.
- Mark Tasks as Completed – Track progress easily.
- Sort Tasks – Sort tasks based on priority or date.
-MySQL Database Integration – Persistent storage using MySQL.

** Tech Stack**

- Frontend: Java Swing (GUI)
- Backend: Java (JDBC for database connection)
- Database: MySQL
- IDE: Eclipse


1. Install MySQL and Create Database

-- Open MySQL Workbench.
-- Create a new database:

```sql
CREATE DATABASE todo_db;
USE todo_db;
```

3. Create the `tasks` table:

```sql
CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    priority VARCHAR(50) NOT NULL
);
```

 3. Configure Database Connection

Update the database credentials in `DatabaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/todo_db";
private static final String USER = "root"; // Change to your MySQL username
private static final String PASSWORD = "yourpassword"; // Change to your MySQL password
```
4. Add MySQL JDBC Driver

- Download MySQL Connector JAR from: [MySQL Connector](https://dev.mysql.com/downloads/connector/j/)
- Add the JAR file to your Eclipse project:
 
5.  Run the Application

- Open Main.java and run it.
- The GUI will appear, allowing you to add, edit, delete, and sort tasks.
- Once task created in GUI are automatically stored in mysql database.

 Screenshots
![image](https://github.com/user-attachments/assets/270acf43-762c-4e85-9f24-554c1bba08bb)



If you like this project, don't forget to star the repository on GitHub!

