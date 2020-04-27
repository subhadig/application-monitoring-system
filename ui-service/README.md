# Data Ingestion Service
- Docker container running a Kibana instance.

# Packaging
docker build -t ui-service:latest .

# Running
docker run -d --rm -p 5601:5601 --name kibana ui-service:latest

# URL
http://localhost:5601
