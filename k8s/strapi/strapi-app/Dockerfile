FROM node:18-alpine

ARG SERVER_URL

ENV SERVER_URL=${SERVER_URL}

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

EXPOSE 1337

CMD ["npm", "start"]
