
docker build --pull --platform linux/amd64 --rm -f "Dockerfile" -t tangthu-be:latest .

docker tag tangthu-be:latest registry.nodo.vn/c09-tang-thu/tangthu-be:latest

docker push registry.nodo.vn/c09-tang-thu/tangthu-be:latest
