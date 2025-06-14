# 🧠 MindWell Connect – Backend API

A secure and scalable backend for **MindWell Connect**, a mental health platform connecting patients with professional therapists.

---

## 👤 Author

**Arit Pal**  
📧 [itsaritpal@gmail.com](mailto:itsaritpal@gmail.com)

---

## 🚀 Tech Stack

- Java 21
- Spring Boot 3.5+
- Spring Security (JWT + Cookies)
- Spring Data JPA
- MySQL
- Maven
- Lombok

---

## 🛠️ Setup Instructions

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/YOUR_GITHUB_USERNAME/mindwell-connect.git
cd mindwell-connect
````

---

### 2️⃣ Set Up MySQL Database

Run this SQL:

```sql
CREATE DATABASE mindwell_connect;
```
Contact me for DB Schema.

---

### 3️⃣ Create `.env` File in Root Directory

```env
JWT_SECRET=your_super_secure_jwt_secret_key
```

---

### 4️⃣ Configure `application.properties`

Ensure the following is in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mindwell_connect
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### 5️⃣ Build & Run the Project

```bash
./mvnw clean install
./mvnw spring-boot:run
```

➡️ App runs at: `http://localhost:8080`

---

## 📦 API Documentation

### 🔐 Authentication

| Method | Endpoint           | Description                |
| ------ | ------------------ | -------------------------- |
| POST   | /api/auth/register | Register patient/therapist |
| POST   | /api/auth/login    | Login, get JWT as cookie   |

**🧪 Register Payload:**

```json
{
  "email": "user@example.com",
  "password": "123456",
  "role": "PATIENT"
}
```

**🧪 Login Payload:**

```json
{
  "email": "user@example.com",
  "password": "123456"
}
```

---

### 👤 Patient Endpoints

| Method | Endpoint                      | Description               |
| ------ | ----------------------------- | ------------------------- |
| POST   | /api/patient/profile          | Create patient profile    |
| GET    | /api/patient/profile          | View own profile          |
| GET    | /api/appointments/mine        | List patient appointments |
| POST   | /api/appointments/book        | Book appointment          |
| PATCH  | /api/appointments/{id}/status | Cancel appointment        |

**🧪 Create Patient Profile:**

```json
{
  "fullName": "Arit Pal",
  "dateOfBirth": "2000-01-01",
  "gender": "Male",
  "phone": "9876543210",
  "concerns": "Anxiety and stress"
}
```

**🧪 Book Appointment:**

```json
{
  "therapistId": 1,
  "startTime": "2025-06-24T10:00:00",
  "endTime": "2025-06-24T11:00:00"
}
```

**🧪 Cancel Appointment:**

```json
{
  "status": "CANCELLED"
}
```

---

### 🧑‍⚕️ Therapist Endpoints

| Method | Endpoint                         | Description                  |
| ------ | -------------------------------- | ---------------------------- |
| POST   | /api/therapist/profile           | Create/update profile        |
| GET    | /api/therapist/profile           | View own profile             |
| POST   | /api/therapist/availability/{id} | Add availability slots       |
| GET    | /api/therapist/availability/{id} | View availability            |
| GET    | /api/appointments/therapist      | Therapist appointments       |
| PATCH  | /api/appointments/{id}/status    | Confirm/complete appointment |

**🧪 Create Therapist Profile:**

```json
{
  "fullName": "Dr. Mind Mentor",
  "specialties": "Anxiety, Depression",
  "qualifications": "PhD Clinical Psychology",
  "bio": "Experienced mental health professional",
  "consultationFee": 1200
}
```

**🧪 Add Availability:**

```json
[
  {
    "dayOfWeek": "MONDAY",
    "startTime": "2025-06-24T10:00:00",
    "endTime": "2025-06-24T11:00:00"
  },
  {
    "dayOfWeek": "WEDNESDAY",
    "startTime": "2025-06-26T14:00:00",
    "endTime": "2025-06-26T15:00:00"
  }
]
```

**🧪 Update Appointment Status (Therapist):**

```json
{
  "status": "CONFIRMED"
}
```

---

### 🌐 Public Endpoints

| Method | Endpoint                    | Description                   |
| ------ | --------------------------- | ----------------------------- |
| GET    | /api/public/therapists      | Search/filter therapists      |
| GET    | /api/public/therapists/{id} | View public therapist profile |

---

### 👑 Admin Endpoints

> Admin account must be manually created in the database.

| Method | Endpoint                                | Description            |
| ------ | --------------------------------------- | ---------------------- |
| GET    | /api/admin/users                        | View all users         |
| PATCH  | /api/admin/users/{id}/role?role=...     | Update user role       |
| PATCH  | /api/admin/users/{id}/status?active=... | Enable/disable account |

---
Check codebase for more detailed payload information.

## 🧠 Made with ❤️ by Arit Pal