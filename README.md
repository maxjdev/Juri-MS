# ⚖️ Juri MS

🚧 **Projeto em construção** 🚧

Plataforma jurídica baseada em arquitetura de microsserviços, desenvolvida com foco em aprendizado prático de infraestrutura, comunicação entre serviços e boas práticas modernas de desenvolvimento.

---

## 🧠 Objetivo

Este projeto tem como principal objetivo estudar e aplicar conceitos como:

- Arquitetura de microsserviços
- Comunicação entre serviços (HTTP)
- Containerização com Docker
- Orquestração com Docker Compose
- Gerenciamento de variáveis de ambiente
- CI/CD (em breve)
- Boas práticas com Spring Boot e Vue.js

---

## 🏗️ Arquitetura (em evolução)

O sistema é composto por:

- **ms-cliente** → gerenciamento de clientes
- **ms-processo** → gerenciamento de processos jurídicos
- **frontend** → interface em Vue 3
- **infra** → Docker, Compose e configurações de ambiente

Cada microsserviço possui:

- Banco de dados isolado
- Configuração independente
- Comunicação via Requisições HTTP

---

## 🛠️ Tecnologias

### Backend

- Java 21
- Spring Boot 4
- Spring Data JPA
- OpenFeign
- PostgreSQL
- SpringDoc OpenAPI

### Frontend

- Typescript
- Vue 3 (Options API)
- Vite

### Infraestrutura

- Docker
- Docker Compose

---

## 🚀 Como rodar (em construção)

> ⚠️ Instruções completas serão adicionadas conforme o projeto evolui

### Pré-requisitos

- Docker
- Node.js
- Java 21

---

### Subir com Docker Compose

1. Ajuste as variáveis em `infra/.env` se precisar trocar portas ou credenciais.
2. Rode `docker compose --env-file .env -f docker-compose.yml up --build` dentro de `infra/`.
3. Acesse o frontend em `http://localhost:5173`.

### Frontend Docker MVP

- `infra/frontend.Dockerfile`: usa multistage para separar build e runtime. O stage com Node instala dependências e gera o `dist`; o stage com Nginx entrega só os arquivos estáticos, deixando a imagem final menor e mais simples.
- `infra/nginx/frontend.conf`: existe porque o Vue Router usa histórico HTML5. Sem `try_files ... /index.html`, um refresh em rota do SPA quebraria no Nginx. O mesmo arquivo também faz proxy de `/api/clientes` e `/api/processos` para os microsserviços, o que simplifica o MVP e evita depender de CORS no navegador.
- `frontend/src/config/api.ts`: centraliza a regra de URL das APIs. Em produção o frontend chama `/api`; em desenvolvimento local, continua podendo usar `VITE_CLIENTE_API` e `VITE_PROCESSO_API`.
- `infra/docker-compose.yml`: ganhou o serviço `frontend` para publicar o Nginx na mesma rede dos microsserviços. Isso é o que permite ao proxy resolver `ms-cliente` e `ms-processo` por nome.

## 📌 Status do projeto

- [x] CRUD de Cliente
- [x] CRUD de Processo
- [x] Comunicação entre microsserviços via HTTP
- [x] Configuração inicial de infraestrutura
- [x] Frontend integrado
- [ ] Docker Compose completo para Produção
- [ ] CI/CD
- [ ] API Gateway
- [ ] Evoluir comunicação síncrona (HTTP) para assíncrona com mensageria (Kafka ou RabbitMQ)

---

## 📚 Aprendizados

Este projeto está sendo utilizado como laboratório para evolução em:

- Engenharia de software
- Arquitetura distribuída
- DevOps e infraestrutura

---

## 🤝 Contribuição

Este é um projeto de estudo pessoal, mas sugestões são sempre bem-vindas.
