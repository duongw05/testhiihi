docker build --pull --platform linux/amd64 --rm -f "Dockerfile" -t language-link-fe:latest .

docker tag language-link-fe:latest registry.nodo.vn/language-link/language-link-fe:latest

docker push registry.nodo.vn/language-link/language-link-fe:latest
