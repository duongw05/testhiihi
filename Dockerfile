FROM node:16.20.2-alpine as build-stage
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . .
RUN npm run build

# production stage
FROM nginx:stable-alpine as production-stage
COPY nginx.conf /etc/nginx/conf.d/noffice-fe-nginx.conf
COPY server.crt /etc/nginx/conf.d/cert/server.crt
COPY server.csr /etc/nginx/conf.d/cert/server.csr
COPY server.key /etc/nginx/conf.d/cert/server.key
COPY --from=build-stage /app/dist /usr/share/nginx/html
COPY firebase-messaging-sw.js /usr/share/nginx/html
EXPOSE 9001
CMD ["nginx", "-g", "daemon off;"]
