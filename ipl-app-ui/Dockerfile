# Set the base-image for build stage
FROM node:18.20-alpine AS build
# Set the maintainer label
LABEL org.opencontainers.image.source="https://github.com/akikr/ipl-dashboard"
LABEL maintainer="ankit akikr@duck.com"
# Set up working directory
WORKDIR /usr/app
# Copy only package.json and package-lock.json
COPY package*.json .
# Install dependencies using --frozen-lockfile to ensure exact versions are installed
RUN npm install --frozen-lockfile
# Copy the rest of the source code
COPY . .
# Build the application
RUN npm run build

# Set the base-image for final stage
FROM nginx:1.27-alpine
# Set the maintainer label
LABEL org.opencontainers.image.source="https://github.com/akikr/ipl-dashboard"
LABEL maintainer="ankit akikr@duck.com"
# Copy the artifact from build-stage
COPY --from=build /usr/app/build /usr/share/nginx/html
# Copy the nginx config file
COPY default.conf /etc/nginx/conf.d/default.conf
COPY ipl_app.conf /etc/nginx/conf.d/services/ipl_app.conf
# Set a non-root user
RUN chmod -R go+rwx /var/run && \
    chmod -R go+rwx /var/cache/nginx && \
    chmod -R go+rwx /usr/share/nginx/html && \
    chmod -R go+rwx /usr/lib/nginx/modules && \
    chmod -R go+rwx /etc/nginx
RUN chown -R nginx:nginx /var/run && \
    chown -R nginx:nginx /var/cache/nginx && \
    chown -R nginx:nginx /usr/share/nginx/html && \
    chown -R nginx:nginx /usr/lib/nginx/modules && \
    chown -R nginx:nginx /etc/nginx/ && \
    chown -R nginx:nginx /etc/nginx/conf.d
USER nginx
# Expose the application port
EXPOSE 80
# Run using start-up cmd
CMD ["nginx", "-g", "daemon off;"]
