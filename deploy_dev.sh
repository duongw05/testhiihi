mvn clean compile package -DskipTests=true

docker build --pull --platform linux/amd64 --rm -f "Dockerfile" -t business-service:latest .

#docker tag tangthu-fe:latest registry.nodo.vn/c09-tang-thu/tangthu-fe:latest

#docker push registry.nodo.vn/c09-tang-thu/tangthu-fe:latest
docker save -o business-service.tar business-service:latest