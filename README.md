# 🍽️ Foodio - Online Food Ordering App

**Foodio** is a full-stack online food ordering system that allows users to browse meals, place orders, and manage their profiles. Admins and employees can manage meals, users, and orders through a secure role-based system.

---

## 🔧 Tech Stack

### Backend
- Spring Boot
- Spring Security + JWT
- PostgreSQL
- Hibernate (JPA)
- JavaMail for Email

### Frontend
- React
- Axios
- React Router
- Tailwind CSS / Material UI (based on usage)

---

## ✅ Features

- 🧑‍🍳 Role-based authentication (Admin, Employee, User)
- 🔒 JWT-based login with secure token handling
- 📧 Email verification and OTP-based 2FA
- 🍔 Meal management and ordering
- 📦 Order tracking and history
- 🌐 Clean and responsive UI

---

## 📁 Project Structure

```
Foodio/
├── backend/
│   ├── src/
│   ├── pom.xml
│   └── application.properties
├── frontend/
│   ├── src/
│   ├── public/
│   └── package.json
├── README.md
└── ...
```

---

## 🚀 Getting Started

### 🔧 Prerequisites

Make sure you have installed:

- Node.js & npm
- Java 17+ (for Spring Boot)
- Maven
- PostgreSQL (running locally)
- Git

---

## 🛠️ Backend Setup (Spring Boot)

### 1. Navigate to the backend folder

```bash
cd backend
```

### 2. Configure the `application.properties`

Edit only the following values:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_DB
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password

spring.mail.username=your_email_address
spring.mail.password=your_email_password
```

⚠️ Only these fields need editing. Leave other properties as-is.

### 3. Run the Spring Boot application

```bash
mvn clean install
mvn spring-boot:run
```

---

## 💻 Frontend Setup (React)

### 1. Navigate to the frontend folder

```bash
cd frontend
```

### 2. Install dependencies

```bash
npm install
```

### 3. Start the development server

```bash
npm start
```

🔗 Make sure your backend server is running at `http://localhost:8080`.

---

## 📦 Deployment Tips

- Backend: Deploy on [Render](https://render.com), [Heroku](https://heroku.com), or [Railway](https://railway.app).
- Frontend: Deploy on [Vercel](https://vercel.com), [Netlify](https://netlify.com), or GitHub Pages.
- Update environment variables and CORS settings accordingly.

---

## 👨‍💻 Author

**Mitul Ranpariya**

---

## 📜 License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT) - feel free to use and modify.
