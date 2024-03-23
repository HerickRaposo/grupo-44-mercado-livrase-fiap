# GRUPO 44 Locação  Hackathon



## Introdução:

Conforme solicitado para atender os requisitos logicos e funcionais do tech Chellenge 5 foram desenvolvidos 4 microserviços: Usuario (Para gestão de logins e registros de usuarios), carrinho (Para gestçao de itens adicionados pelos usuarios), estoque (Gerenciamento de produtos cadastrados sendo acessados apenas por ADMs) e por fim o microserviço de pedidos, onde a venda será consolidada, itens serão removidos do estoque e carrinho é limpo,

Vale lembrar que para facilitar a publicação de projeto colocamos os projetos filhos dentro do microserviço de usuario, no entanto, embora ocupem mesmo espaço em disco cada um deles possuem pom avulso, cada um rodando em portas diferentes previamente configuradas e  bancos distintos.

<h1 align="center">
  Desenvolvimento das APIs
</h1>

## Tecnologias

[Spring Boot](https://spring.io/projects/spring-boot): Modulo derivado do Spring Framework que facilita desenvolvimento de aplicações java implementando injeção e inversão de dependencias


[Spring Security](https://spring.io/projects/spring-security): O Spring Security é uma estrutura que se concentra em fornecer autenticação e autorização para aplicativos Java.

[JSON Web Token (JWT)](https://jwt.io/): O JSON Web Token é um padrão da Internet para a criação de dados com assinatura opcional e/ou criptografia cujo payload contém o JSON que afirma algum número de declarações. 

[Docker](https://www.docker.com/): Docker é um conjunto de produtos de plataforma como serviço que usam virtualização de nível de sistema operacional para entregar software em pacotes chamados contêineres. 

[GitLab](https://about.gitlab.com/): Plataforma de gerenciamento de ciclo de vida de desenvolvimento de software com versionamento de codigo git.

[Postman](https://learning.postman.com/docs/developer/postman-api/intro-api/): Ferramenta destinada a desenvolvedores que possibilita testar chamadas API e gerar documentação de forma iterativa.Foi usado neste projeto para gerar collections e realizar teste de chamadas aos endpoints;

[Tortoise](https://tortoisegit.org/docs/tortoisegit/): Ferramenta gerencial que facilita manipulação de projetos em GIT. Foi usado neste projeto para resolução de conflitos.

[Sourcetree](https://confluence.atlassian.com/get-started-with-sourcetree): Assim como o Tortoise é uma ferramenta gerencial para facilitar o desenvolvimento de projetos em Git, no entanto possui uma interface mais receptivel e navegabilidade facilitada.Foi usado neste projeto paa navegação e criação de ramos.
## Práticas adotadas


- Uso de DTOs para a API
- Injeção de Dependências
- Arquitetura hexagonal
- Utilização de dockers para compilação do projeto

## Escalabilidade de sistema:

- [Modularização em Containner e Docker](https://about.gitlab.com/): Docker é uma plataforma de código aberto que facilita a criação, implantação e gerenciamento de aplicativos por meio de contêineres, que são ambientes isolados e leves. Esses contêineres empacotam aplicativos e suas dependências, permitindo uma execução consistente em diversos sistemas, eliminando problemas de compatibilidade e melhorando a eficiência no desenvolvimento.

- [CI](https://about.gitlab.com/) (Continuous Integration):  CI (Continuous Integration) é uma prática de desenvolvimento em que as alterações de código são regularmente integradas e testadas automaticamente. O GitLab CI automatiza esse processo, organizando-o em pipelines, que representam as etapas de construção, teste e implantação de um aplicativo. Isso melhora a eficiência e a qualidade do desenvolvimento de software.[Veja pipeline executada](https://gitlab.com/mattec1/grupo-44-sistema-de-parquimetro-fiap/-/jobs/5473301001)

```
###############################################
##### Pipeline Gitlab-CI - v1.0           #####
##### MATTEC PROJETOS  - 13/03/2024       #####
##### GRUPO 44 LOCACAO HACKATHON FIAP     #####
###############################################


stages:
  - teste
  - build
  - deploy


executar_teste:
  stage: teste
  before_script:
    - echo "Preparando testes..."
    - chmod +x ./script.sh
  script:
    - ./script.sh
  after_script:
    - echo "Apagando arquivos temporários..."

executar_teste2:
  image: node:19.1
  needs:
    - executar_teste
  stage: teste
  script:
    - echo "Executando mais um teste..."
    - npm version

criar_imagens:
  stage: build
  script:
    - echo "Criando as imagens..."

push_imagens:
  needs:
    - criar_imagens
  stage: build
  script:
    echo "Realizando upload das imagens..."

kubernetes:
  stage: deploy
  script:
    - echo "Executando deploy..."


```


## Como Executar

### Localmente
- Clonar repositório git
- Construir o projeto:
```
./mvnw clean package
```
- Executar:
- -  Microserviço Usuario:

- - - A API poderá ser acessada em [localhost:8080](http://localhost:8080)

- - - O Swagger poderá ser visualizado em [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- - Microserviço Estoque:

- - - A API poderá ser acessada em [localhost:8081](http://localhost:8081)

- - -O Swagger poderá ser visualizado em [localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

- - Microserviço Carrinho:

 - - - A API poderá ser acessada em [localhost:8082](http://localhost:8082)

- - - O Swagger poderá ser visualizado em [localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

- - Microserviço Pedidos:
- - - A API poderá ser acessada em [localhost:8083](http://localhost:8083)

- - - O Swagger poderá ser visualizado em [localhost:8082/swagger-ui.html](http://localhost:8083/swagger-ui.html)




<h1 align="center">
  MICROSERVIÇO USUARIO (MS_USER)
</h1>

<p align="center">
 https://gitlab.com/mattec1/grupo-44-mercado-livrase-fiap
</p>

Este microserviço deve ser considerado a porta de entrada para todo sistema, é por ele que toda parte de autenticação, autorização e controle de usuários será feita. 
## API Endpoints

### Cadastro de usuario (CUSTOMER)

Neste endpoint envia-se nome completo do usuario, o acesso e a senha e  ele retorna as informações cadastrais mais um jwt gerado  que possui as informações como nivel de acesso permissões e as autorizações de usuario;
Apenas usuarios Administradores, ou seja, apenas as pessoas que possuem ROLE_ADMINISTRATOR conseguirão acessar o endpoint de cadastro, caso contrario o sistema retornará erro 403.
Os niveis de acesso foram separados em tres: 
 - ROLE_CUSTOMER
 - ROLE_ADMINISTRATOR
 - ROLE_ASSISTANT_ADMINISTRATOR
```
Requet:

curl --request POST \
  --url http://localhost:8080/api/v1/customers \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=D7ED8DCCC055A4D43250F761B4B29517 \
  --data '{
	  
	"name": "Herick da Silva Sauros",
  "username_or_email": "herick@hotmail.com",
	"password": "987654321",
  "repeatedPassword": "987654321"
}
'


Response:

{
    "id": 1,
    "username": "herick@hotmail.com",
    "name": "Herick da Silva Sauros",
    "jwt": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXJpY2tAaG90bWFpbC5jb20i
    LCJpYXQiOjE3MTA4MTM0MDQsImV4cCI6MTcxMDgxNTIwNCwicm9sZSI6IlJPTEVfQ1VTVE9NRVIiLCJuY
    W1lIjoiSGVyaWNrIGRhIFNpbHZhIFNhdXJvcyIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJSRU
    FEX01ZX1BST0ZJTEUifV19.eKE9tdwfGhLe3aVP8m5R4dEHtmWi0V15Jz1FCnz_2Sc"
}


Json retornado JWT:
{
  "sub": "herick@hotmail.com",
  "iat": 1710813404,
  "exp": 1710815204,
  "role": "ROLE_CUSTOMER",
  "name": "Herick da Silva Sauros",
  "authorities": [
    {
      "authority": "READ_MY_PROFILE"
    }
  ]
}
        
```



 ### Login (Customer authenticate):
Assim que o suario realiza o processo de login o mesmo recebe um token de acesso que deverá ser usado nas demais requisições
```
Request:
curl --request POST \
  --url http://localhost:8080/api/v1/auth/authenticate \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=D7ED8DCCC055A4D43250F761B4B29517 \
  --data '{
  "username": "herick@hotmail.com",
	"password": "987654321"
}
'

Response:
{
    "jwt": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ
    oZXJpY2tAaG90bWFpbC5jb20iLCJpYXQiOjE3MTA5ODEwMDUsImV4cCI
    6MTcxMDk4MjgwNSwicm9sZSI6IlJPTEVfQ1VTVE9NRVIiLCJuYW1lIjo
    iSGVyaWNrIGRhIFNpbHZhIFNhdXJvcyIsImF1dGhvcml0aWVzIjpbeyJ
    hdXRob3JpdHkiOiJSRUFEX01ZX1BST0ZJTEUifV19.xaoACQrvGxZcW1
    bVcRE-3yKnoUw_dd1e6P9Yr5SlRtM"
}
```

### Validate token:
Para controle de sessão de usuario validando que o token esta ativo e autorizado foi criado este endpoint que retorna um Booleano validando se token está ativo. O tempo estimado para cada token é de 1 minuto.Este endpoint é usado pelos demais microserviços como carrinho, estoque e pedido.

```
http GET http://localhost:8080/api/v1/enderecos/buscar-todos

Request:

curl --location 'http://localhost:8080/api/v1/enderecos/buscar-todos' \
--data ''

Response:
{
    "data": [
        {
            "id": 1,
            "rua": "Rua das Flores",
            "numero": 123,
            "bairro": "Centro",
            "cidade": "São Paulo",
            "estado": "SP",
            "cep": "01234-567"
        }
    ],
    "paginator": {
        "pageNumber": 0,
        "totalElements": 1,
        "totalPages": 1
    }
}


```

<h1 align="center">
  MICROSERVIÇO ESTOQUE 
</h1>

<p align="center">
 https://gitlab.com/mattec1/grupo-44-mercado-livrase-fiap
</p>
```
Após descorrermos sobre o microserviço de usuarios agora veremos sobre o microserviço de estoque, que possui endpoints gerenciais de estoque os quais  são exclusivos para administradores

## API Endpoints


### Cadastro de produtos
O cadastro assim como os demais endpoints necessita o envio do token obtido no login de usuario . Para cadastrar o produto é obrigatorio o preenchimento de nome, quantidade em estoque e valor unitario

```
Request:
curl --request POST \
    --url http://localhost:8081/produto \
    --header 'Content-Type: application/json' \
    --header 'User-Agent: Insomnia/2023.5.7' \
    --cookie JSESSIONID=D7ED8DCCC055A4D43250F761B4B29517 \
    --data '{
    "descricao": "Produto B",
    "quantidadeEstoque": 10,
    "valorUnitario": 50.5
}
'

Response:
{
    "id": 3,
    "descricao": "Produto B",
    "quantidadeEstoque": 10,
    "valorUnitario": 50.5
}
```

### Atualização de produtos
Na atualização de produtos todos os atributos exceto o id poderão ser modificados.
```
curl --location --request PUT 'http://localhost:8080/api/v1/localidades/1' \
--header 'Content-Type: application/json' \
--data '{
  "id": 1,
  "nome": "Localidade Teste update",
  "enderecoDTO": {
    "id": 1
  },
  "idsAmenidades": [1, 2, 3]
}
'
```

### Deletar produtos
Para realizar a deleção basta completar url com o identificador do produto a ser deletado. Ao deletar retorna codigo 204.

```
curl --request DELETE \
  --url http://localhost:8081/produto/2 \
  --header 'User-Agent: Insomnia/2023.5.7' \
  --cookie JSESSIONID=D7ED8DCCC055A4D43250F761B4B29517                  
```
### Listar Customers:
Ao listar os resultados são exibidos de forma paginada.
```
Request:
curl --request GET \
  --url http://localhost:8081/produto \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: Insomnia/2023.5.7' \
  --cookie JSESSIONID=D7ED8DCCC055A4D43250F761B4B29517
'

Response:
     {
	"content": [
		{
			"id": 3,
			"descricao": "Produto B",
			"quantidadeEstoque": 10,
			"valorUnitario": 50.5
		}
	],
	"pageable": {
		"pageNumber": 0,
		"pageSize": 10,
		"sort": {
			"empty": true,
			"sorted": false,
			"unsorted": true
		},
		"offset": 0,
		"paged": true,
		"unpaged": false
	},
	"last": true,
	"totalPages": 1,
	"totalElements": 1,
	"first": true,
	"numberOfElements": 1,
	"size": 10,
	"number": 0,
	"sort": {
		"empty": true,
		"sorted": false,
		"unsorted": true
	},
	"empty": false
}               
```
### Busca por ID

```
Request:
curl --request GET \
      --url http://localhost:8081/produto/3 \
      --header 'Content-Type: application/json' \
      --header 'User-Agent: Insomnia/2023.5.7' \
      --cookie JSESSIONID=D7ED8DCCC055A4D43250F761B4B29517

Response:
{
    "id": 3,
    "descricao": "Produto B",
    "quantidadeEstoque": 10,
    "valorUnitario": 50.5
}

```
<h1 align="center">
  MICROSERVIÇO CARRINHO 
</h1>

<p align="center">
 https://gitlab.com/mattec1/grupo-44-mercado-livrase-fiap
</p>

Diferentemente do microserviço anterior que era um microserviço gerencial restrito a administradores este fica acessivel a todos usuarios cadastrados, no entanto ainda necessita do envio do token capturado no login

## API Endpoints

### Inserção de itens no carrinho:
Neste endpoint na requisição enviamos apenas o id do produto que  será adicionado e a quantidade deste produto. Ao realizar a requisição o sistema  pelo id do produto busca as informações do produto na base de estoque e baseado nela multiplica o valor unitario do produto em estoque pela quantidade solicitada encontrando o valor proporcional a quantidade selecionada pelo usuario. Posteriormente o sistema extrai o email de usuario do token de autenticação e finalmente registra o item no carrinho.
```
Request:
curl --request POST \
  --url http://localhost:8082/carrinho \
  --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXJpY2tAaG90bWFpbC5jb20iLCJpYXQiOjE3MTA4MTM0OTcsImV4cCI6MTcxMDgxNTI5Nywicm9sZSI6IlJPTEVfQ1VTVE9NRVIiLCJuYW1lIjoiSGVyaWNrIGRhIFNpbHZhIFNhdXJvcyIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJSRUFEX01ZX1BST0ZJTEUifV19.3JhXuWz3WVawo1W7f2hcK0Zfe7HwUxQn5T6kVFEF2AQ' \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: Insomnia/2023.5.7' \
  --cookie JSESSIONID=D7ED8DCCC055A4D43250F761B4B29517 \
  --data '{
  "idProduto": 3,
  "quantidade": 5
}'
'

Response:
{
    "id": 1,
    "idProduto": 3,
    "emailUsuario": "herick@hotmail.com",
    "quantidade": 5,
    "valor": 252.5
}
```
### Atualização de quantidade de itens
De mesmo modo como explicado no endpoint anterior, ao atualizar a quantidade o sistema atualizará tambem o valor total do item proporcionalmente a quantidade.
```
Request:
curl --request PATCH \
  --url http://localhost:8082/carrinho/atualizaqtde/1/5 \
  --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXJpY2tAaG90bWFpbC5jb20iLCJpYXQiOjE3MTA4MTM0OTcsImV4cCI6MTcxMDgxNTI5Nywicm9sZSI6IlJPTEVfQ1VTVE9NRVIiLCJuYW1lIjoiSGVyaWNrIGRhIFNpbHZhIFNhdXJvcyIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJSRUFEX01ZX1BST0ZJTEUifV19.3JhXuWz3WVawo1W7f2hcK0Zfe7HwUxQn5T6kVFEF2AQ' \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: Insomnia/2023.5.7' \
  --cookie JSESSIONID=D7ED8DCCC055A4D43250F761B4B29517
  
Response:
{
    "id": 1,
    "idProduto": 3,
    "emailUsuario": "herick@hotmail.com",
    "quantidade": 5,
    "valor": 252.5
}
```

### Listar itens
```
Request:
curl --request GET \
  --url 'http://localhost:8082/carrinho?email_usuario=herick%40hotmail.com' \
  --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXJpY2tAaG90bWFpbC5jb20iLCJpYXQiOjE3MTA4MTM0OTcsImV4cCI6MTcxMDgxNTI5Nywicm9sZSI6IlJPTEVfQ1VTVE9NRVIiLCJuYW1lIjoiSGVyaWNrIGRhIFNpbHZhIFNhdXJvcyIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJSRUFEX01ZX1BST0ZJTEUifV19.3JhXuWz3WVawo1W7f2hcK0Zfe7HwUxQn5T6kVFEF2AQ' \
  --header 'User-Agent: Insomnia/2023.5.7' \
  --cookie JSESSIONID=D7ED8DCCC055A4D43250F761B4B29517

Response:
{
	"content": [
		{
			"id": 1,
			"idProduto": 3,
			"emailUsuario": "herick@hotmail.com",
			"quantidade": 5,
			"valor": 252.5
		}
	],
	"pageable": {
		"pageNumber": 0,
		"pageSize": 10,
		"sort": {
			"empty": true,
			"sorted": false,
			"unsorted": true
		},
		"offset": 0,
		"unpaged": false,
		"paged": true
	},
	"last": true,
	"totalElements": 1,
	"totalPages": 1,
	"size": 10,
	"number": 0,
	"sort": {
		"empty": true,
		"sorted": false,
		"unsorted": true
	},
	"numberOfElements": 1,
	"first": true,
	"empty": false
}      
```
