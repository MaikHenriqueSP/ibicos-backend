# syntax=docker/dockerfile:1
 
FROM openjdk:16-alpine3.13

WORKDIR /ibicos-backend

COPY .mvn/ .mvn
COPY mvnw pom.xml package.json package-lock.json ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline 
RUN apk add --no-cache --update nodejs=14.18.1-r0 nodejs-npm 
RUN npm install

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]
