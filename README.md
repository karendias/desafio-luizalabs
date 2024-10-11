# Desafio Luizalabs

### 📋 Pré-requisitos

Do que você vai precisar:

* Java 17

* Maven

* Docker


### 🔧 Instalação

1 - Execute o comando ***mvn install*** para instalar as dependencias do projeto

2 - Após baixar o projeto, execute o comando ***mvn spring-boot:run***, que irá inicializar os containers do Postgres e RabbitMQ e inicializará a API

3 - Execute init_db.sql dentro do banco de dados do container Postgres.

**Para testar a api, importe a collection no Postman**

## ⚙️ Executando os testes

Para executar os testes, execute o arquivo **src/test/java/com/desafio_luizalabs/SendingScheduleServiceImplTest.java**

## 🛠️ Construído com

* [Spring Framework](https://spring.io/projects/spring-framework) - Framework utilizado
* [Maven](https://maven.apache.org/) - Gerente de Dependência
* [Postgres](https://www.postgresql.org/docs/) - Banco de Dados
* [RabbitMQ](https://www.rabbitmq.com/docs) - Fila de Processamento
