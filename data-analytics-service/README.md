# Data Analytics Service
- Docker container running an Elasticsearch instance.

# Packaging
docker build -t analytics-service:latest .

# Running
docker run -d --rm -p 9200:9200 -p 9300:9300 --name analytics -e "discovery.type=single-node" analytics-service:latest
