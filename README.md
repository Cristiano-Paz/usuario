# 📌 Cadastro de Usuários

Aplicação backend para gerenciar usuários, desenvolvida em **Java 17** com **Spring Boot**, utilizando **PostgreSQL** como banco de dados e **Postman** para testes.

---

## 🚀 Tecnologias utilizadas
- Java 17  
- Spring Boot 3 (Web, Data JPA, Security, Validation)  
- PostgreSQL  
- Gradle  
- Lombok  
- JWT (JSON Web Token)  
- Postman  
- Docker (opcional, para banco ou containerização)

---

## ⚙️ Configuração

### 1️⃣ Pré-requisitos
- **Java 17** instalado (`java -version` para confirmar)  
- **Gradle**   
- **PostgreSQL** em execução

### 2️⃣ Banco de dados
Crie um banco no Postgres:
```sql
CREATE DATABASE cadastro_usuarios;
```

Edite o arquivo `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/cadastro_usuarios
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
## 🏗️ Build e Execução

### 1️⃣ Build do projeto
No terminal, dentro da pasta raiz:
```bash
./gradlew clean build
```

### 2️⃣ Executar a aplicação
Escolha um dos modos:

- **Via Gradle**:
  ```bash
  ./gradlew bootRun
  ```

- **Via JAR**:
  ```bash
  java -jar build/libs/cadastro-usuarios-0.0.1-SNAPSHOT.jar
  ```

A aplicação ficará disponível em:
```
http://localhost:8080
```

---

## 📌 Endpoints principais
| Método | Endpoint         | Descrição                |
|--------|-----------------|-------------------------|
| POST   | /usuarios       | Cadastra um novo usuário |
| GET    | /usuarios       | Lista todos os usuários |
| GET    | /usuarios/{id}  | Busca usuário por ID |
| PUT    | /usuarios/{id}  | Atualiza dados |
| DELETE | /usuarios/{id}  | Remove um usuário |

> Caso utilize autenticação via JWT, envie o token no header:  
> `Authorization: Bearer <seu-token>`

---

## 🧪 Testando com Postman
1. Abra o Postman.  
2. Crie uma requisição `POST` para `http://localhost:8080/usuarios`.  
3. Envie um JSON:
```json
{
  "nome": "João Silva",
  "email": "joao@teste.com",
  "senha": "123456"
}

