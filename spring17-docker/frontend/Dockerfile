FROM node:alpine as builder
WORKDIR /app
COPY ./frontend/package.json ./
RUN npm install
COPY ./frontend /app
RUN npm run build

FROM nginx:alpine
COPY --from=builder /app /usr/share/nginx/html
EXPOSE 80 443
COPY --from=builder /app/default.conf /etc/nginx/conf.d/default.conf
CMD [ "nginx", "-g", "daemon off;" ]