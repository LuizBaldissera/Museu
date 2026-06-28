# Biblioteca Virtual

## Descrição

### Problema

A biblioteca do Teatro 13 de Maio possui um acervo de livros físicos, porém não conta com uma plataforma digital que permita aos usuários consultar facilmente as obras disponíveis. Isso dificulta a divulgação do acervo e o acesso às informações sobre os livros existentes.

### Solução Proposta

A Biblioteca Virtual foi desenvolvida para disponibilizar um acervo digital do Teatro 13 de Maio, permitindo que os usuários consultem os livros cadastrados e visualizem informações como título, autor, editora, ano de publicação, gênero e demais dados relevantes.

O sistema **não disponibiliza a leitura dos livros em formato digital**, servindo exclusivamente como um catálogo virtual para consulta do acervo físico da biblioteca.

---

## Tecnologias Utilizadas

O projeto foi desenvolvido utilizando as seguintes tecnologias:

* **Java 17**
* **Spring Boot**
* **MySQL**
* **IntelliJ IDEA**
* **Maven**
* **Git e GitHub**

---

## Arquitetura Adotada

O sistema foi desenvolvido seguindo o padrão de arquitetura **MVC (Model-View-Controller)**, proporcionando uma organização clara do código e facilitando sua manutenção.

A divisão ocorre da seguinte forma:

* **Model:** responsável pela representação dos dados e comunicação com o banco de dados MySQL.
* **View:** responsável pela interface apresentada ao usuário.
* **Controller:** responsável por receber as requisições, processar as regras de negócio e intermediar a comunicação entre Model e View.

Essa arquitetura torna o projeto mais organizado, modular e de fácil manutenção.

---

## Instalação e Execução

### Pré-requisitos

Antes de executar o projeto, certifique-se de possuir instalado:

* Java JDK 17
* IntelliJ IDEA
* MySQL Server
* Maven
* Git

### Clonando o repositório

```bash
git clone https://github.com/SEU-USUARIO/SEU-REPOSITORIO.git
```

### Configurando o banco de dados

1. Crie um banco de dados no MySQL.
2. Atualize o arquivo `application.properties` com as credenciais do banco:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### Executando o projeto

1. Abra o projeto no IntelliJ IDEA.
2. Aguarde o Maven baixar as dependências automaticamente.
3. Execute a classe principal anotada com `@SpringBootApplication`.
4. Após iniciar, o sistema estará disponível em:

```text
http://localhost:8080
```

---

## Equipe de Desenvolvimento

* **Luiz Henrique Baldissera Ghisleri**
* **Gabriel Sarturi**

---

## Vídeo Demonstrativo

O vídeo demonstrando o funcionamento do sistema pode ser acessado pelo link abaixo:

**(Adicionar link do vídeo)**
