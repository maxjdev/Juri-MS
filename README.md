# ⚖️ Juri MS Platform

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

## 📌 Status do projeto

- [x] CRUD de Cliente
- [x] CRUD de Processo
- [x] Comunicação entre microsserviços via HTTP
- [x] Configuração inicial de infraestrutura
- [ ] Frontend integrado
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
