# Stage 1: Build the React frontend
FROM node:20-alpine AS frontend-build
WORKDIR /frontend
COPY library-frontend/package*.json ./
RUN npm install
COPY library-frontend ./
RUN npm run build

# Stage 2: Build the Spring Boot backend with the frontend bundled
FROM maven:3.9.9-eclipse-temurin-21-alpine AS backend-build
WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN mvn dependency:go-offline -B || true
COPY src src
# Copy React build to Spring Boot static resources
COPY --from=frontend-build /frontend/dist src/main/resources/static
RUN mvn clean package -DskipTests -B

# Stage 3: Lightweight Runtime image (Single URL)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=backend-build /app/target/Library_Management_System-0.0.1-SNAPSHOT.jar app.jar
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
