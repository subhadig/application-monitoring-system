# Database Service
- Docker container running a MongoDB instance.
- It initializes itself from the *script/initialize.js* file after startup.

# Packaging
docker build -t database-service:latest .

# Running
docker run -d --rm --name db database-service:latest
