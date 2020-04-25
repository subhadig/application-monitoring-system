# Database Service
- Docker container running a MongoDB instance.
- It initializes itself from the *script/initialize.js* file after startup.

# Packaging
docker build -t database-service:latest .

# Running
docker run -d --rm -p 27017:27017 --name db database-service:latest

# Connect using the *mongo* client
docker exec -it db mongo --username mongouser --password mongouser
