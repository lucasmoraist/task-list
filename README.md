# Task List - Backend
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

## Descrição
O projeto Task List é uma aplicação de gerenciamento de tarefas que permite aos usuários criar, ler, atualizar e excluir
tarefas de forma eficiente. Ideal para organizar suas atividades diárias. Nele é possível adicionar comentários dentro
das tarefas criadas, além como mudar o status da tarefa para "Pendente", "Em Progresso" e "Concluído".

## Funcionalidades
1. Criação de Tarefas
   - Título: Nome da tarefa.
   - Descrição: Descrição detalhada da tarefa (opcional).
   - Status: Estado da tarefa (PENDING, IN_PROGRESS, COMPLETED).
````json
{
  "title": "Exemplo de Tarefa",
  "description": "Esta é uma tarefa de exemplo.",
  "status": "PENDING"
}
````
2. Listar Tarefas
   - Listagem de todas as tarefas criadas.
3. Buscar Tarefa por ID
   - Busca de uma tarefa específica pelo ID.
4. Atualizar Tarefa
   - Atualização das informações de uma tarefa existente.
5. Excluir Tarefa
    - Exclusão de uma tarefa existente.
6. Autenticação e Autorização
   - Implemente autenticação JWT (JSON Web Token) para proteger a API.
   - Use Spring Security para gerenciar usuários e permissões.
7. Documentação
   - Implementação de documentação utilizando Swagger.
   - Link para acessar na máquina -> `http://localhost:8080/`
## Instruções de Instalação
### Pré-requisitos
- Java 17 ou superior
- IDE (Eclipse, IntelliJ, VSCode)
- Maven 3.2.5 ou superior
- Docker
### Etapas
1. Clone o repositório na sua máquina
````shell
git clone https://github.com/lucasmoraist/task-list.git 
````
2. Acesse o diretório do projeto
````shell
cd task-list
````
3. Execute o Docker Compose para criar o container do banco de dados
````shell
docker-compose up -d
````
3. Faça o build da aplicação

**Linux**
````shell
mvn clean package \ 
  -Dspring.mail.username=email@example.com \
  -Dspring.mail.password="passwordExample" \
  -Dapi.security.token.secret=developer
````

**Windows**
````shell
mvn clean package ^
  -Dspring.mail.username=email@example.com ^
  -Dspring.mail.password="passwordExample" ^
  -Dapi.security.token.secret=developer
````
5. Execute o arquivo jar gerado pelo build

**Linux**
````shell
java -jar target/task-list-0.1.0-SNAPSHOT.jar \
  --spring.mail.username=email@example.com \
  --spring.mail.password="passwordExample" \
  --api.security.token.secret=developer
````

**Windows**
````shell
java -jar target/task-list-0.1.0-SNAPSHOT.jar ^
  --spring.mail.username=email@example.com ^
  --spring.mail.password="passwordExample" ^
  --api.security.token.secret=developer
````

### Docker
1. Build da imagem Docker
````shell
docker build -t task-list .
````

2. Execute o container

**Linux**
````shell
docker run -d \
  -e MAIL_USERNAME=seu_email@example.com \
  -e MAIL_PASSWORD=sua_senha \
  -e JWT_SECRET=sua_chave_secreta \
  -e PROFILE=dev \
  -e DATABASE_HOST=localhost \
  -e DATABASE_PORT=5432 \
  -e DATABASE_NAME=meu_banco \
  -e DATABASE_USER=meu_usuario \
  -e DATABASE_PASSWORD=minha_senha \
  -p 8080:8080 \
  task-list
````

**Windows**
````cmd
docker run -d ^
  -e MAIL_USERNAME=seu_email@example.com ^
  -e MAIL_PASSWORD=sua_senha ^
  -e JWT_SECRET=sua_chave_secreta ^
  -e PROFILE=dev ^
  -e DATABASE_HOST=localhost ^
  -e DATABASE_PORT=5432 ^
  -e DATABASE_NAME=meu_banco ^
  -e DATABASE_USER=meu_usuario ^
  -e DATABASE_PASSWORD=minha_senha ^
  -p 8080:8080 ^
  task-list
````

## Instruções de Uso
- Com o projeto em execução, abra sua ferramenta para testes de requisições (Insomnia ou Postman).
- Importe o arquivo json que está em `./collection` e já será possível realizar os testes.

## Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para enviar pull requests com melhorias, correções de bugs ou novos recursos.

## Contatos
<a href = "mailto:seu-email@gmail.com">
  <img src="https://img.shields.io/badge/-Gmail-%23333?style=for-the-badge&logo=gmail&logoColor=white" target="_blank" alt="">
</a>
<a href="https://www.linkedin.com/in/seu-linkedin/" target="_blank">
  <img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white" target="_blank" alt="">
</a>