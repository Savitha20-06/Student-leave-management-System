# Student Leave Management System

A simple full-stack project for students to apply for leave and for an
admin to approve/reject requests.

## Tech Stack

| Part                 | Technology              |
|----------------------|--------------------------|
| Frontend             | HTML + CSS + JavaScript |
| Backend              | Java + Spring Boot      |
| API                  | REST API                |
| Database             | MySQL                   |
| Database Connection  | Spring Data JPA         |
| Build Tool           | Maven                   |
| Testing              | Postman                 |
| Version Control      | Git + GitHub            |
| Cloud                | AWS EC2                 |

## Folder Structure

```
student-leave-management/
├── backend/                  Spring Boot REST API (Maven project)
│   ├── pom.xml
│   └── src/main/java/com/leavemanagement/
│       ├── LeaveManagementApplication.java
│       ├── model/LeaveRequest.java
│       ├── repository/LeaveRequestRepository.java
│       ├── service/LeaveRequestService.java
│       └── controller/LeaveRequestController.java
│   └── src/main/resources/application.properties
│
├── frontend/                 Plain HTML/CSS/JS UI
│   ├── index.html            Student page (apply leave / check status)
│   ├── admin.html            Admin page (approve / reject / delete)
│   ├── css/style.css
│   └── js/
│       ├── script.js
│       └── admin.js
│
├── database/
│   └── schema.sql            Reference SQL (table is also auto-created by JPA)
│
├── postman/
│   └── Student-Leave-Management.postman_collection.json
│
├── .gitignore
└── README.md
```

## How It Works

- A **student** fills out the form on `index.html` to apply for leave
  (name, roll number, department, dates, reason).
- The request is saved in MySQL through the Spring Boot REST API.
- An **admin** opens `admin.html` to see every request and click
  **Approve**, **Reject**, or **Delete**.
- Students can check their own status by searching their roll number.

No login system is included, to keep the project simple. You can add
authentication later if needed.

## 1. Set Up MySQL

Make sure MySQL is running, then just start the backend — it will
auto-create the `student_leave_db` database and `leave_requests` table
for you (via `spring.datasource.url=...createDatabaseIfNotExist=true`
and `spring.jpa.hibernate.ddl-auto=update`).

If you'd rather create it manually, run `database/schema.sql` in MySQL
Workbench or the `mysql` CLI.

Update your credentials in:
`backend/src/main/resources/application.properties`
```
spring.datasource.username=root
spring.datasource.password=root
```

## 2. Run the Backend (Spring Boot + Maven)

```bash
cd backend
mvn spring-boot:run
```

The API will start at: `http://localhost:8080`

Main endpoints:

| Method | Endpoint                          | Description                  |
|--------|------------------------------------|-------------------------------|
| POST   | /api/leaves                        | Apply for a new leave        |
| GET    | /api/leaves                        | Get all leave requests       |
| GET    | /api/leaves/{id}                   | Get one leave request        |
| GET    | /api/leaves/student/{rollNumber}   | Get leaves for one student   |
| PUT    | /api/leaves/{id}/approve           | Approve a leave              |
| PUT    | /api/leaves/{id}/reject            | Reject a leave               |
| PUT    | /api/leaves/{id}/status            | Update status (body: status) |
| DELETE | /api/leaves/{id}                   | Delete a leave request       |

## 3. Run the Frontend

The frontend is plain HTML/CSS/JS — no build step needed.

Simplest option: just open `frontend/index.html` and `frontend/admin.html`
directly in your browser (double-click the file, or right-click →
"Open with" → your browser).

Or serve it locally (optional):
```bash
cd frontend
python3 -m http.server 5500
```
Then visit `http://localhost:5500`.

> The frontend calls the API at `http://localhost:8080`. If you deploy
> the backend elsewhere, update `API_BASE` at the top of
> `frontend/js/script.js` and `frontend/js/admin.js`.

## 4. Test with Postman

Import `postman/Student-Leave-Management.postman_collection.json`
into Postman and try each request (make sure the backend is running
first).

## 5. Version Control (Git + GitHub)

```bash
git init
git add .
git commit -m "Initial commit - Student Leave Management System"
git branch -M main
git remote add origin <your-github-repo-url>
git push -u origin main
```

## 6. Deploy to AWS EC2 (Basic Steps)

1. Launch an EC2 instance (Amazon Linux or Ubuntu), open ports
   **22** (SSH), **80/8080** (app), and **3306** (MySQL, if needed).
2. Install Java 17, Maven, and MySQL on the instance.
3. Copy your project to the server:
   ```bash
   git clone <your-github-repo-url>
   cd student-leave-management/backend
   ```
4. Build the JAR:
   ```bash
   mvn clean package
   ```
5. Run it (in the background so it keeps running after logout):
   ```bash
   nohup java -jar target/student-leave-management.jar &
   ```
6. Update MySQL credentials in `application.properties` to match your
   EC2 MySQL setup.
7. Upload the `frontend/` folder to the same server (e.g. served by
   Nginx, Apache, or simply opened via a static file host), and update
   `API_BASE` in the JS files to your EC2 public IP, e.g.
   `http://<your-ec2-ip>:8080/api/leaves`.

## Notes

- Kept intentionally simple: no authentication, no frameworks like
  React/Angular, no Lombok — just plain, readable Java and vanilla
  JS/HTML/CSS so it's easy to understand and extend.
- Status values used: `PENDING`, `APPROVED`, `REJECTED`.
