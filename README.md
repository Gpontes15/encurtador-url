# 🚀 URL Shortener com Cache Redis

Sistema de encurtamento de URLs focado em performance, utilizando arquitetura de Cache Distribuído.

## 🏗 Arquitetura
O sistema utiliza a estratégia **Cache-Aside**:
1. **Leitura:** A aplicação verifica primeiro no **Redis** (Memória RAM). Se encontrar (`Cache Hit`), retorna em milissegundos. Se não (`Cache Miss`), busca no **PostgreSQL**, salva no Redis e retorna.
2. **Escrita:** O link gerado é salvo no PostgreSQL (Disco) para segurança e no Redis para acesso imediato.

## 🛠 Tecnologias
- **Java 17 & Spring Boot 3**
- **Spring Data Redis** (Cache)
- **Spring Data JPA & PostgreSQL** (Persistência)
- **Docker Compose** (Orquestração dos Bancos)
- **Google Guava** (Hashing Murmur3)

## ⚡ Performance (Evidência)
Logs da aplicação mostrando a atuação do Cache:
![Redis Logs](link-da-sua-imagem-aqui.png)
