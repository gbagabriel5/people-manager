# People Manager API

## Proposta
Ferramenta para cadastro de pessoas com autenticação

## Funcionalidades 
* Cadastrar, Atualizar Listar, Deletar e Autenticar PESSOAS via JWT(gera token por autenticação)

## Pré-requisitos
* Java (openjdk 17)
* Intellij Community / Eclipse
* SpringBoot
* Maven

### Executando os testes da API
Para realizar os testes da api é preciso ter o **Maven** e **Java 17** instalados.
````
mvn clean test
````

### Executando a API
Para realizar os testes da api é preciso ter o **Maven** e **Java 17** instalados.
````
mvn clean spring-boot:run
````
### Autenticação
Autenticação usando SpringSecurity através da autenticação e geração de token JWT por dados de Pessoas
- Autenticação por pessoa predefinida. Email: gba@gmail.com Senha: 123

## Tecnologias
* SpringBoot - O Spring foi usado pela praticidade, velocidade e simplicidade, assim tendo uma maior produtividade.
* MongoDB - O MongoDB é um banco de dados orientado a documentos que possui código aberto (open source) e foi projetado para armazenar uma grande escala de dados, além de permitir que se trabalhe de forma eficiente com grandes volumes.
* H2 - Banco de dados relacional escrito em Java. Ele pode ser integrado em aplicativos Java ou executado no modo cliente-servidor.

## Arquitetura
O padrāo de arquitetura MVC, foi usado por ser um padrāo com camadas definidas, e trás como benefício isolar as regras de negócios da lógica de apresentação, a interface com o usuário. Isto possibilita a existência de várias interfaces com o usuário que podem ser modificadas sem que haja a necessidade da alteração das regras de negócios, proporcionando assim muito mais flexibilidade e oportunidades de reuso das classes.
