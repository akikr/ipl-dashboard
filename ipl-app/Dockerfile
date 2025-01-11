# Set the base-image for build stage: front-end
FROM node:12.21.0-slim AS build-frontend
# Set up working directory
RUN mkdir -p /usr/app
COPY . /usr/app
# Build the application
WORKDIR /usr/app/src/front-end
RUN npm install;npm run build

# Set the base-image for build stage: back-end
FROM maven:3.9.8-eclipse-temurin-8 AS build-backend
RUN mkdir -p /usr/app
# Copy the artifact from build-frontend stage
COPY --from=build-frontend /usr/app /usr/app
# Build the application
WORKDIR /usr/app
RUN mvn clean package

# Set the base-image for final stage
FROM alpine:latest@sha256:77726ef6b57ddf65bb551896826ec38bc3e53f75cdde31354fbffb4f25238ebd
ENV JAVA_HOME=/usr/lib/jvm/default-jvm/jre
RUN echo $JAVA_HOME
RUN apk upgrade --update-cache; \
  apk add openjdk8-jre; \
  rm -rf /tmp/* /var/cache/apk/*
RUN apk --no-cache add curl
RUN apk --no-cache add busybox-extras
RUN apk --no-cache add wget
RUN apk --no-cache add net-tools
# Copy the artifact from build-stage
RUN mkdir -p /usr/webapp
COPY --from=build-backend /usr/app/target/*.jar /usr/webapp/ipl-app.jar
WORKDIR /usr/webapp
# Set a non-root user: Add a system group 'appgroup' and a system user 'appuser' in this group
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
RUN chown -R appuser:appgroup /usr/webapp
USER appuser
# Expose the application port
EXPOSE 8081
# Define environment variables for java-options
ENV JAVA_OPTS ''
# Run the application
ENTRYPOINT java $JAVA_OPTS -jar ipl-app.jar
