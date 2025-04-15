mvn clean compile package -DskipTests=true

docker build --pull --platform linux/amd64 --rm -f "Dockerfile" -t language-link-be:latest .

docker tag language-link-be:latest registry.nodo.vn/language-link/language-link-be:latest

docker push registry.nodo.vn/language-link/language-link-be:latest
