# Single Live URL Deployment: Library Management System

Your application is now configured for a **Single Live URL**! 

When deployed, both the React frontend and Spring Boot REST API run together under **one single URL** (e.g. `https://your-app.onrender.com`).
- Visiting `https://your-app.onrender.com/` opens the React web interface.
- All API requests go to `/api/...` on the exact same domain.
- **Zero CORS issues, no separate frontend hosting on Vercel required!**

---

## Prerequisites (Takes ~2 minutes)
You only need a free PostgreSQL database.
1. Sign up at [Neon.tech](https://neon.tech) (100% Free PostgreSQL).
2. Create a project named `library`.
3. In the Neon dashboard under **Connection Details**, select **JDBC**.
4. You will see a connection string like:
   ```
   jdbc:postgresql://ep-silent-wave-123456.us-east-2.aws.neon.tech/neondb?sslmode=require
   ```
5. Note down your **DB_URL**, **Username**, and **Password**.

---

## Deploy to Render (Single Service = Single URL)

1. Push your updated code to GitHub:
   ```bash
   git add .
   git commit -m "Configure single live URL deployment"
   git push origin main
   ```

2. Open [Render.com](https://render.com) and click **New +** > **Web Service**.
3. Select your GitHub repository: `TJGokul/l_m_s`.
4. Fill in the details:
   - **Name**: `library-management-system` (or whatever you prefer)
   - **Branch**: `main`
   - **Runtime**: `Java` (or `Docker` - both are supported!)
   - **Build Command**: `./mvnw clean package -DskipTests`
   - **Start Command**: `java -jar target/Library_Management_System-0.0.1-SNAPSHOT.jar`
   - **Instance Type**: `Free`

5. Add the **Environment Variables**:
   | Key | Value |
   |---|---|
   | `PORT` | `8080` |
   | `DB_URL` | `jdbc:postgresql://<neon-host>/neondb?sslmode=require` |
   | `DB_USERNAME` | `<your-neon-username>` |
   | `DB_PASSWORD` | `<your-neon-password>` |

6. Click **Create Web Service**.

---

## Your Lively URL!
Render will build and launch your application. Once finished, you will receive a single URL:
```
https://library-management-system-xxxx.onrender.com
```
Opening this URL in any browser will directly load your complete Library Management System!
