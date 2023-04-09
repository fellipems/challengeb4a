
# challengeb4a

# 🚀 Desafio Backend Java com SpringBoot

Objetivo: Construa uma API RESTful que permita criar, atualizar, listar e excluir usuários e seus respectivos endereços.

---

- [Requisitos](#requisitos)
- [Critérios de Avaliação](#criterios-avaliacao)
- [Instruções](#instruções)
  - [To-do list](#to-do-list-futura)

### Requisitos

- Usuários: 
	- Cada usuário deve ter um nome, um email e um ou mais endereços;
	- Cada endereço deve ter um logradouro, um número, um complemento, um bairro, uma cidade, um estado e um CEP;
	- A API deve permitir a criação de um novo usuário, fornecendo seu nome e email;
	 - A API deve permitir a atualização de um usuário existente, fornecendo seu ID, nome e email;
	 - A API deve permitir a exclusão de um usuário existente, fornecendo seu ID;
	 - A API deve permitir a listagem de todos os usuários existentes com seus respectivos endereços.

- Endereço:
	- A API deve permitir a criação de um novo endereço para um usuário existente, fornecendo o ID do usuário e os dados do endereço;
	- A API deve permitir a atualização de um endereço existente de um usuário, fornecendo o ID do usuário, o ID do endereço e os novos dados do endereço;
	- A API deve permitir a exclusão de um endereço existente de um usuário, fornecendo o ID do usuário e o ID do endereço;
  
  <a id="criterios-avaliacao"></a>
## Critérios de Avaliação

-   Usar o Spring Data para acessar o banco de dados e fazer o mapeamento dos relacionamentos;
-   Escolher o banco de dados que preferir, mas deve usar o Spring Data para acessá-lo;
-   Escolher a estrutura de pacotes que preferir, mas tente seguir as convenções do Spring Boot;
-   Usar o Maven ou o Gradle como gerenciador de dependências e construção;
-   A API deve seguir as práticas RESTful, incluindo o uso adequado dos verbos HTTP (POST, GET, PUT, DELETE) e códigos de status;
-   Deve seguir as boas práticas do Clean Code, mantendo seu código limpo, organizado e fácil de ler;

## Instruções
### Aplicação e suas dependências:
- Java 1.8
- Spring Boot 2.7.10
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-data-jdbc
- springfox-swagger
- springfox-swagger-ui
- apache-commons-lang3
- spring-boot-devtools
- h2-database
- JUnit 5

| componente | porta |
| --------- | ----------- |
| Swagger UI (API/Documentation)  | `http://localhost:8000/swagger-ui/#/` |
| H2 DataBase Console  | `http://localhost:8000/h2-console` |


application.properties foi configurado para apontar para o banco local h2:
```
server.port=8000  
spring.datasource.url=jdbc:h2:mem:challengeb4a  
spring.datasource.driverClassName=org.h2.Driver  
spring.datasource.username=sa  
spring.datasource.password=password  
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect  
spring.h2.console.enabled=true  
spring.h2.console.path=/h2-console
```

### To-do list futura
- [ ] OAuth 2 para fazer requests na API
