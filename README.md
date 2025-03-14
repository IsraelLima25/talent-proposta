# Projeto Spring Boot - Proposta

Este projeto implementa um desafio de propostas utilizando o Spring Boot, com integração ao MySQL, OAuth2 e Prometheus para métricas.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para criação de aplicações Java.
- **Spring Data JPA**: Para interagir com bancos de dados relacionais usando JPA.
- **Spring Web**: Para criar uma API REST.
- **Spring Security**: Para gerenciar autenticação e autorização.
- **Spring Boot Actuator**: Para monitoramento e gerenciamento da aplicação.
- **MySQL**: Banco de dados relacional para persistência.
- **Micrometer + Prometheus**: Para métricas e monitoramento.
- **OAuth2**: Para autenticação e autorização de recursos.
- **Jaeger**: Para rastreamento distribuído (opcional).
- **H2 Database**: Banco de dados em memória para testes.

## Requisitos

- **Java 11**
- **Apache Maven**
- **MySQL** (para ambiente de produção)
- **Docker** (opcional, caso queira rodar o Jaeger)

## Como Rodar o Projeto

### 1. Clonando o Repositório

Clone o repositório com o seguinte comando:

```bash
git clone https://github.com/usuario/proposta.git
cd proposta

### Compilando o projeto
./mvnw clean install

### Executando o projeot
./mvnw spring-boot:run

### Executando os testes
./mvnw test
