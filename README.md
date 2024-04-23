# Grupo 44 mercado Livrase



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

[GitLab](https://about.gitlab.com/): Plataforma de gerenciamento de ciclo de vida de desenvolvimento de software com versionamento de codigo git.

[Postman](https://learning.postman.com/docs/developer/postman-api/intro-api/): Ferramenta destinada a desenvolvedores que possibilita testar chamadas API e gerar documentação de forma iterativa.Foi usado neste projeto para gerar collections e realizar teste de chamadas aos endpoints;

[Tortoise](https://tortoisegit.org/docs/tortoisegit/): Ferramenta gerencial que facilita manipulação de projetos em GIT. Foi usado neste projeto para resolução de conflitos.

[Sourcetree](https://confluence.atlassian.com/get-started-with-sourcetree): Assim como o Tortoise é uma ferramenta gerencial para facilitar o desenvolvimento de projetos em Git, no entanto possui uma interface mais receptivel e navegabilidade facilitada.Foi usado neste projeto paa navegação e criação de ramos.
## Práticas adotadas


- Uso de DTOs para a API
- Injeção de Dependências
- Arquitetura hexagonal

## Escalabilidade de sistema:


- [CI](https://about.gitlab.com/) (Continuous Integration):  CI (Continuous Integration) é uma prática de desenvolvimento em que as alterações de código são regularmente integradas e testadas automaticamente. O GitLab CI automatiza esse processo, organizando-o em pipelines, que representam as etapas de construção, teste e implantação de um aplicativo. Isso melhora a eficiência e a qualidade do desenvolvimento de software.[Veja pipeline executada](https://gitlab.com/mattec1/grupo-44-sistema-de-parquimetro-fiap/-/jobs/5473301001)

```
###############################################
##### Pipeline Gitlab-CI - v1.0           #####
##### MATTEC PROJETOS  - 13/03/2024       #####
##### GRUPO 44 MERCADO LIVRASE FIAP       #####
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
- Criar bancos localmente nas portas equivalentes aos serviços
```
./mvnw clean package
```
- Executar:
- -  Microserviço Usuario:

- - - A API poderá ser acessada em [localhost:8080](http://127.0.0.1:8080/)

- - Microserviço Estoque:

- - - A API poderá ser acessada em [localhost:8081](http://127.0.0.1:8081)

- - Microserviço Carrinho:

- - - A API poderá ser acessada em [localhost:8082](http://127.0.0.1:8082)

- - Microserviço Pedidos:
- - - A API poderá ser acessada em [localhost:8083](http://127.0.0.1::8083)




<h1 align="center">
  MICROSERVIÇO USUARIO (MS_USER)
</h1>

<p align="center">
 https://gitlab.com/mattec1/grupo-44-mercado-livrase-fiap
</p>

Este microserviço deve ser considerado a porta de entrada para todo sistema, é por ele que toda parte de autenticação, autorização e controle de usuários será feita.
## API Endpoints

A seguir veremos tres endpoints de cadastros de usuarios, embora muito semelhantes cada um cumpre uma função determinada de cadastrar um tipo de usuario podendo ser este CUSTOMER, ADMINISTRATOR e ADMINISTRATOR-ASSIstent. Cadanivel de acesso confere ao portador permissões especificas tanto de leitura, alteração, inclusão e deleção.
O administrador possui permissão total dentro do sistema, ja o assistente só tem permissão para leitura e atualização de produtos, ja o customer possui apenas permissão de leitura.
Nestes proximos  endpoint envia-se nome completo do usuario, o acesso e a senha e  ele retorna as informações cadastrais mais um jwt gerado  que possui as informações como nivel de acesso permissões e as autorizações de usuario;

Para facilitação de testes os endpoints de cadastros ficaram abertoz, mas em ambiente de produção seriam fechados para ADMs
### Cadastro de usuario (ADMINISTRATOR)

```
Requet:

curl --request POST \
  --url http://127.0.0.1:8080//api/v1/setting/administrator/cadastra_hole \
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
    "jwt": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXJpY2tAaG90bWFpbC5jb20iLCJpYXQiOjE3MTEyODIzMzIsImV4cCI6MTcxMTI4NDEzMiwicm9sZSI6IlJPTEVfQURNSU5JU1RSQVRPUiIsIm5hbWUiOiJIZXJpY2sgZGEgU2lsdmEgU2F1cm9zIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJFQURfQUxMX1BST0RVQ1RTIn0seyJhdXRob3JpdHkiOiJSRUFEX09ORV9QUk9EVUNUIn0seyJhdXRob3JpdHkiOiJDUkVBVEVfT05FX1BST0RVQ1QifSx7ImF1dGhvcml0eSI6IlVQREFURV9PTkVfUFJPRFVDVCJ9LHsiYXV0aG9yaXR5IjoiRElTQUJMRV9PTkVfUFJPRFVDVCJ9LHsiYXV0aG9yaXR5IjoiUkVBRF9BTExfQ0FURUdPUklFUyJ9LHsiYXV0aG9yaXR5IjoiUkVBRF9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IkNSRUFURV9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IlVQREFURV9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IkRJU0FCTEVfT05FX0NBVEVHT1JZIn0seyJhdXRob3JpdHkiOiJSRUFEX01ZX1BST0ZJTEUifV19.EBqmvwcrZz4dtieXG-m003cvGacseqHTv6CA1as3Fpg"
}


Json retornado JWT:
{
  "sub": "herick@hotmail.com",
  "iat": 1711283968,
  "exp": 1711285768,
  "role": "ROLE_ADMINISTRATOR",
  "name": "Herick da Silva Sauros",
  "authorities": [
    {
      "authority": "READ_ALL_PRODUCTS"
    },
    {
      "authority": "READ_ONE_PRODUCT"
    },
    {
      "authority": "CREATE_ONE_PRODUCT"
    },
    {
      "authority": "UPDATE_ONE_PRODUCT"
    },
    {
      "authority": "DISABLE_ONE_PRODUCT"
    },
    {
      "authority": "READ_ALL_CATEGORIES"
    },
    {
      "authority": "READ_ONE_CATEGORY"
    },
    {
      "authority": "CREATE_ONE_CATEGORY"
    },
    {
      "authority": "UPDATE_ONE_CATEGORY"
    },
    {
      "authority": "DISABLE_ONE_CATEGORY"
    },
    {
      "authority": "READ_MY_PROFILE"
    }
  ]
}
        
```

### Cadastro de usuario (ADMINISTRATOR-ASSISTENT)

```
Requet:

curl --request POST \
  --url http://127.0.0.1:8080//api/v1/setting/administrator/cadastra_hole_assistent \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=D7ED8DCCC055A4D43250F761B4B29517 \
  --data '{
	  
	"name": "Marcos Saimpaio",
  "username_or_email": "marcos@hotmail.com",
	"password": "987654321",
  "repeatedPassword": "987654321"
}
'


Response:

{
    "id": 2,
    "username": "marcos@hotmail.com",
    "name": "Marcos Saimpaio",
    "jwt": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJjb3NAaG90bWFpbC5jb20iLCJpYXQiOjE3MTEyODY4MTksImV4cCI6MTcxMTI4ODYxOSwicm9sZSI6IlJPTEVfQVNTSVNUQU5UX0FETUlOSVNUUkFUT1IiLCJuYW1lIjoiTWFyY29zIFNhaW1wYWlvIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJFQURfQUxMX1BST0RVQ1RTIn0seyJhdXRob3JpdHkiOiJSRUFEX09ORV9QUk9EVUNUIn0seyJhdXRob3JpdHkiOiJVUERBVEVfT05FX1BST0RVQ1QifSx7ImF1dGhvcml0eSI6IlJFQURfQUxMX0NBVEVHT1JJRVMifSx7ImF1dGhvcml0eSI6IlJFQURfT05FX0NBVEVHT1JZIn0seyJhdXRob3JpdHkiOiJVUERBVEVfT05FX0NBVEVHT1JZIn0seyJhdXRob3JpdHkiOiJSRUFEX01ZX1BST0ZJTEUifV19.IL4U9ay1AC1A26Tbwbpqi9zX68AR9emKPvHqohtb740"
}


Json retornado JWT:
{
  "sub": "marcos@hotmail.com",
  "iat": 1711286819,
  "exp": 1711288619,
  "role": "ROLE_ASSISTANT_ADMINISTRATOR",
  "name": "Marcos Saimpaio",
  "authorities": [
    {
      "authority": "READ_ALL_PRODUCTS"
    },
    {
      "authority": "READ_ONE_PRODUCT"
    },
    {
      "authority": "UPDATE_ONE_PRODUCT"
    },
    {
      "authority": "READ_ALL_CATEGORIES"
    },
    {
      "authority": "READ_ONE_CATEGORY"
    },
    {
      "authority": "UPDATE_ONE_CATEGORY"
    },
    {
      "authority": "READ_MY_PROFILE"
    }
  ]
}
        
```
### Cadastro de usuario (CUSTOMER)

```
Requet:

curl --request POST \
  --url http://127.0.0.1:8080//api/v1/customers \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=D7ED8DCCC055A4D43250F761B4B29517 \
  --data '{
	  
	"name": "Joao da Silva Sauros",
  "username_or_email": "joao@hotmail.com",
	"password": "987654321",
  "repeatedPassword": "987654321"
}
'


Response:

{
    "id": 1,
    "username": "joao@hotmail.com",
    "name": "joao da Silva Sauros",
    "jwt": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXJpY2tAaG90bWFpbC5jb20i
    LCJpYXQiOjE3MTA4MTM0MDQsImV4cCI6MTcxMDgxNTIwNCwicm9sZSI6IlJPTEVfQ1VTVE9NRVIiLCJuY
    W1lIjoiSGVyaWNrIGRhIFNpbHZhIFNhdXJvcyIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJSRU
    FEX01ZX1BST0ZJTEUifV19.eKE9tdwfGhLe3aVP8m5R4dEHtmWi0V15Jz1FCnz_2Sc"
}


Json retornado JWT:
{
  "sub": "joao@hotmail.com",
  "iat": 1710813404,
  "exp": 1710815204,
  "role": "ROLE_CUSTOMER",
  "name": "João da Silva Sauros",
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
  --url http://127.0.0.1:8080//api/v1/auth/authenticate \
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
http GET http://127.0.0.1:8080//api/v1/enderecos/buscar-todos

Request:

curl --location 'http://127.0.0.1:8080//api/v1/enderecos/buscar-todos' \
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
  --url http://127.0.0.1:8081/api/v1/produto/cadastro \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXJpY2tAaG90bWFpbC5jb20iLCJpYXQiOjE3MTE0MTg4NDIsImV4cCI6MTcxMTQyNDI0Miwicm9sZSI6IlJPTEVfQURNSU5JU1RSQVRPUiIsIm5hbWUiOiJIZXJpY2sgZGEgU2lsdmEgU2F1cm9zIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJFQURfQUxMX1BST0RVQ1RTIn0seyJhdXRob3JpdHkiOiJSRUFEX09ORV9QUk9EVUNUIn0seyJhdXRob3JpdHkiOiJDUkVBVEVfT05FX1BST0RVQ1QifSx7ImF1dGhvcml0eSI6IlVQREFURV9PTkVfUFJPRFVDVCJ9LHsiYXV0aG9yaXR5IjoiRElTQUJMRV9PTkVfUFJPRFVDVCJ9LHsiYXV0aG9yaXR5IjoiUkVBRF9BTExfQ0FURUdPUklFUyJ9LHsiYXV0aG9yaXR5IjoiUkVBRF9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IkNSRUFURV9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IlVQREFURV9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IkRJU0FCTEVfT05FX0NBVEVHT1JZIn0seyJhdXRob3JpdHkiOiJSRUFEX01ZX1BST0ZJTEUifV19.DxcEcST7p2mXrjUQ425NSfUhxzyxBkMapn0V_a98-m0 ' \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/8.6.1' \
  --cookie JSESSIONID=D7ED8DCCC055A4D43250F761B4B29517 \
  --data '{
	"descricao":"radio",
	"quantidadeEstoque":10,
	"valorUnitario":1500.97
}'

Response:
{
	"id": 4,
	"descricao": "radio",
	"quantidadeEstoque": 1,
	"valorUnitario": 1500.97
}
```

### Atualização de produtos
Na atualização de produtos todos os atributos exceto o id poderão ser modificados.
```
curl --request PUT \
  --url http://127.0.0.1:8081/api/v1/produto/update/4 \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXJpY2tAaG90bWFpbC5jb20iLCJpYXQiOjE3MTE0MTg4NDIsImV4cCI6MTcxMTQyNDI0Miwicm9sZSI6IlJPTEVfQURNSU5JU1RSQVRPUiIsIm5hbWUiOiJIZXJpY2sgZGEgU2lsdmEgU2F1cm9zIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJFQURfQUxMX1BST0RVQ1RTIn0seyJhdXRob3JpdHkiOiJSRUFEX09ORV9QUk9EVUNUIn0seyJhdXRob3JpdHkiOiJDUkVBVEVfT05FX1BST0RVQ1QifSx7ImF1dGhvcml0eSI6IlVQREFURV9PTkVfUFJPRFVDVCJ9LHsiYXV0aG9yaXR5IjoiRElTQUJMRV9PTkVfUFJPRFVDVCJ9LHsiYXV0aG9yaXR5IjoiUkVBRF9BTExfQ0FURUdPUklFUyJ9LHsiYXV0aG9yaXR5IjoiUkVBRF9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IkNSRUFURV9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IlVQREFURV9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IkRJU0FCTEVfT05FX0NBVEVHT1JZIn0seyJhdXRob3JpdHkiOiJSRUFEX01ZX1BST0ZJTEUifV19.DxcEcST7p2mXrjUQ425NSfUhxzyxBkMapn0V_a98-m0 ' \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/8.6.1' \
  --cookie JSESSIONID=D7ED8DCCC055A4D43250F761B4B29517 \
  --data '{
	"descricao":"tc",
	"quantidadeEstoque":10,
	"valorUnitario":1500.97
}'

Response:
{
	"id": 4,
	"descricao": "tv",
	"quantidadeEstoque": 1,
	"valorUnitario": 1500.97
}

```

### Deletar produtos
Para realizar a deleção basta completar url com o identificador do produto a ser deletado. Ao deletar retorna codigo 204.

```
curl --request DELETE \
  --url http://127.0.0.1:8081/api/v1/produto/deletar/2 \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXJpY2tAaG90bWFpbC5jb20iLCJpYXQiOjE3MTE0MTg4NDIsImV4cCI6MTcxMTQyNDI0Miwicm9sZSI6IlJPTEVfQURNSU5JU1RSQVRPUiIsIm5hbWUiOiJIZXJpY2sgZGEgU2lsdmEgU2F1cm9zIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJFQURfQUxMX1BST0RVQ1RTIn0seyJhdXRob3JpdHkiOiJSRUFEX09ORV9QUk9EVUNUIn0seyJhdXRob3JpdHkiOiJDUkVBVEVfT05FX1BST0RVQ1QifSx7ImF1dGhvcml0eSI6IlVQREFURV9PTkVfUFJPRFVDVCJ9LHsiYXV0aG9yaXR5IjoiRElTQUJMRV9PTkVfUFJPRFVDVCJ9LHsiYXV0aG9yaXR5IjoiUkVBRF9BTExfQ0FURUdPUklFUyJ9LHsiYXV0aG9yaXR5IjoiUkVBRF9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IkNSRUFURV9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IlVQREFURV9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IkRJU0FCTEVfT05FX0NBVEVHT1JZIn0seyJhdXRob3JpdHkiOiJSRUFEX01ZX1BST0ZJTEUifV19.DxcEcST7p2mXrjUQ425NSfUhxzyxBkMapn0V_a98-m0 ' \
  --header 'User-Agent: Insomnia/2023.5.7' \
  --cookie JSESSIONID=D7ED8DCCC055A4D43250F761B4B29517                  
```
### Listar Produtos:
Ao listar os resultados são exibidos de forma paginada.
```
Request:
curl --request GET \
  --url http://127.0.0.1:8081/api/v1/produto/listar \
 --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXJpY2tAaG90bWFpbC5jb20iLCJpYXQiOjE3MTE0MTg4NDIsImV4cCI6MTcxMTQyNDI0Miwicm9sZSI6IlJPTEVfQURNSU5JU1RSQVRPUiIsIm5hbWUiOiJIZXJpY2sgZGEgU2lsdmEgU2F1cm9zIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJFQURfQUxMX1BST0RVQ1RTIn0seyJhdXRob3JpdHkiOiJSRUFEX09ORV9QUk9EVUNUIn0seyJhdXRob3JpdHkiOiJDUkVBVEVfT05FX1BST0RVQ1QifSx7ImF1dGhvcml0eSI6IlVQREFURV9PTkVfUFJPRFVDVCJ9LHsiYXV0aG9yaXR5IjoiRElTQUJMRV9PTkVfUFJPRFVDVCJ9LHsiYXV0aG9yaXR5IjoiUkVBRF9BTExfQ0FURUdPUklFUyJ9LHsiYXV0aG9yaXR5IjoiUkVBRF9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IkNSRUFURV9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IlVQREFURV9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IkRJU0FCTEVfT05FX0NBVEVHT1JZIn0seyJhdXRob3JpdHkiOiJSRUFEX01ZX1BST0ZJTEUifV19.DxcEcST7p2mXrjUQ425NSfUhxzyxBkMapn0V_a98-m0 ' \
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
		},
		{
			"id": 4,
			"descricao": "radio",
			"quantidadeEstoque": 10,
			"valorUnitario": 1500.97
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
	"totalElements": 2,
	"size": 10,
	"number": 0,
	"sort": {
		"empty": true,
		"sorted": false,
		"unsorted": true
	},
	"numberOfElements": 2,
	"first": true,
	"empty": false
}
```
### Busca por ID

```
Request:
curl --request GET \
      --url http://127.0.0.1:8081/api/v1/produto/busca/3 \
       --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXJpY2tAaG90bWFpbC5jb20iLCJpYXQiOjE3MTE0MTg4NDIsImV4cCI6MTcxMTQyNDI0Miwicm9sZSI6IlJPTEVfQURNSU5JU1RSQVRPUiIsIm5hbWUiOiJIZXJpY2sgZGEgU2lsdmEgU2F1cm9zIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJFQURfQUxMX1BST0RVQ1RTIn0seyJhdXRob3JpdHkiOiJSRUFEX09ORV9QUk9EVUNUIn0seyJhdXRob3JpdHkiOiJDUkVBVEVfT05FX1BST0RVQ1QifSx7ImF1dGhvcml0eSI6IlVQREFURV9PTkVfUFJPRFVDVCJ9LHsiYXV0aG9yaXR5IjoiRElTQUJMRV9PTkVfUFJPRFVDVCJ9LHsiYXV0aG9yaXR5IjoiUkVBRF9BTExfQ0FURUdPUklFUyJ9LHsiYXV0aG9yaXR5IjoiUkVBRF9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IkNSRUFURV9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IlVQREFURV9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IkRJU0FCTEVfT05FX0NBVEVHT1JZIn0seyJhdXRob3JpdHkiOiJSRUFEX01ZX1BST0ZJTEUifV19.DxcEcST7p2mXrjUQ425NSfUhxzyxBkMapn0V_a98-m0 ' \
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
  --url http://127.0.0.1:8082/carrinho \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXJpY2tAaG90bWFpbC5jb20iLCJpYXQiOjE3MTE0MTg4NDIsImV4cCI6MTcxMTQyNDI0Miwicm9sZSI6IlJPTEVfQURNSU5JU1RSQVRPUiIsIm5hbWUiOiJIZXJpY2sgZGEgU2lsdmEgU2F1cm9zIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJFQURfQUxMX1BST0RVQ1RTIn0seyJhdXRob3JpdHkiOiJSRUFEX09ORV9QUk9EVUNUIn0seyJhdXRob3JpdHkiOiJDUkVBVEVfT05FX1BST0RVQ1QifSx7ImF1dGhvcml0eSI6IlVQREFURV9PTkVfUFJPRFVDVCJ9LHsiYXV0aG9yaXR5IjoiRElTQUJMRV9PTkVfUFJPRFVDVCJ9LHsiYXV0aG9yaXR5IjoiUkVBRF9BTExfQ0FURUdPUklFUyJ9LHsiYXV0aG9yaXR5IjoiUkVBRF9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IkNSRUFURV9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IlVQREFURV9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IkRJU0FCTEVfT05FX0NBVEVHT1JZIn0seyJhdXRob3JpdHkiOiJSRUFEX01ZX1BST0ZJTEUifV19.DxcEcST7p2mXrjUQ425NSfUhxzyxBkMapn0V_a98-m0' \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: Insomnia/2023.5.7' \
  --cookie JSESSIONID=D7ED8DCCC055A4D43250F761B4B29517 \
  --data '{
  "idProduto": 123,
  "quantidade": 2
}'

Response:
{
	"timestamp": "2024-03-26T03:23:50.115+00:00",
	"status": 400,
	"error": "Bad Request",
	"message": "No message available",
	"path": "//api/v1/carrinho"
}
```
### Atualização de quantidade de itens
De mesmo modo como explicado no endpoint anterior, ao atualizar a quantidade o sistema atualizará tambem o valor total do item proporcionalmente a quantidade.
```
Request:
curl --request PATCH \
  --url http://127.0.0.1:8082/carrinho/atualizaqtde/1/5 \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXJpY2tAaG90bWFpbC5jb20iLCJpYXQiOjE3MTA4MTM0OTcsImV4cCI6MTcxMDgxNTI5Nywicm9sZSI6IlJPTEVfQ1VTVE9NRVIiLCJuYW1lIjoiSGVyaWNrIGRhIFNpbHZhIFNhdXJvcyIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJSRUFEX01ZX1BST0ZJTEUifV19.3JhXuWz3WVawo1W7f2hcK0Zfe7HwUxQn5T6kVFEF2AQ' \
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
  --url 'http://127.0.0.1:8082/carrinho?email_usuario=herick%40hotmail.com' \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXJpY2tAaG90bWFpbC5jb20iLCJpYXQiOjE3MTA4MTM0OTcsImV4cCI6MTcxMDgxNTI5Nywicm9sZSI6IlJPTEVfQ1VTVE9NRVIiLCJuYW1lIjoiSGVyaWNrIGRhIFNpbHZhIFNhdXJvcyIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJSRUFEX01ZX1BST0ZJTEUifV19.3JhXuWz3WVawo1W7f2hcK0Zfe7HwUxQn5T6kVFEF2AQ' \
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

### Fechar compras
Este serviço zera todos os itens existentes no carrinho e invoca o serviço de pedido. Se não houver itens no carrinho a operação não ocorre.

```
Request:
curl --request POST \
  --url http://127.0.0.1:8082/carrinhofechar-compra \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXJpY2tAaG90bWFpbC5jb20iLCJpYXQiOjE3MTE0MTg4NDIsImV4cCI6MTcxMTQyNDI0Miwicm9sZSI6IlJPTEVfQURNSU5JU1RSQVRPUiIsIm5hbWUiOiJIZXJpY2sgZGEgU2lsdmEgU2F1cm9zIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJFQURfQUxMX1BST0RVQ1RTIn0seyJhdXRob3JpdHkiOiJSRUFEX09ORV9QUk9EVUNUIn0seyJhdXRob3JpdHkiOiJDUkVBVEVfT05FX1BST0RVQ1QifSx7ImF1dGhvcml0eSI6IlVQREFURV9PTkVfUFJPRFVDVCJ9LHsiYXV0aG9yaXR5IjoiRElTQUJMRV9PTkVfUFJPRFVDVCJ9LHsiYXV0aG9yaXR5IjoiUkVBRF9BTExfQ0FURUdPUklFUyJ9LHsiYXV0aG9yaXR5IjoiUkVBRF9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IkNSRUFURV9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IlVQREFURV9PTkVfQ0FURUdPUlkifSx7ImF1dGhvcml0eSI6IkRJU0FCTEVfT05FX0NBVEVHT1JZIn0seyJhdXRob3JpdHkiOiJSRUFEX01ZX1BST0ZJTEUifV19.DxcEcST7p2mXrjUQ425NSfUhxzyxBkMapn0V_a98-m0' \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: Insomnia/2023.5.7' \
  --cookie JSESSIONID=D7ED8DCCC055A4D43250F761B4B29517 \
  --data '{
  "emailUsuario":"ernesto.sambongo@outlook.com",
  "formaPagamento":"PIX"
}'

Response:

    "data": {
        "id": 9,
        "dataPedido": "2024-03-26",
        "valorPedido": 73996.00,
        "formaPagamento": "PIX",
        "estadoPedido": "AGUARDANDO_PAGAMENTO",
        "emailUsuario": "ernesto.sambongo@outlook.com",
        "itensPedido": [
            {
                "id": 11,
                "descricao": "Notebook Asus Rog Strix G16 Core I9 16gb 512ssd W11 Rtx 4060 Cor Cinza",
                "quantidade": 2,
                "valorUnitario": 29998.0,
                "idProduto": 4
            },
            {
                "id": 12,
                "descricao": "Ihpone 15 pro max",
                "quantidade": 2,
                "valorUnitario": 7000.0,
                "idProduto": 1
            }
        ]
    },
    "mensagem": "Pedido efetuado com sucesso!"
}
```

<h1 align="center">
  MICROSERVIÇO PEDIDO 
</h1>

<p align="center">
 https://gitlab.com/mattec1/grupo-44-mercado-livrase-fiap
</p>

O serviço de pedido é o serviço invocado pelo microserviço de carrinho no fechamento da compra, sendo assim fica responsavel pelo gerenciamento de pedidos efetuados pelos usuarios. Assim como demais todos endpoints necessitam de token valido.

### Salvar pedidos
Serviço responsavel para submeter o pedido, que por sinal é usado pelo MS-CARRINHO no fecho dos produtos adicionados no carrinho

```
Request:
ccurl --location 'http://127.0.0.1:8083/pedido/salvar' \
--header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdXJlbG1hLnNhbWJvbmdvQG91dGxvb2suY29tIiwiaWF0IjoxNzExNDY4Njk2LCJleHAiOjE3MTY2NTI2OTYsInJvbGUiOiJST0xFX0NVU1RPTUVSIiwibmFtZSI6Imp1cmVsbWEuc2FtYm9uZ28iLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiUkVBRF9NWV9QUk9GSUxFIn1dfQ.ovWdli1Ev96tgH0zv6pLJej70c05uS5vleiuTBrTXmE' \
--header 'Content-Type: application/json' \
--data-raw '{
    "formaPagamento": "PIX",
    "emailUsuario": "ernesto.sambongo@outlook.com",
    "itensPedido": [
        {
            "idProduto": "10",
            "descricao": "Iphone 15 pro max",
            "quantidade": "3",
            "valorUnitario": "3500"
        },
        {
            "idProduto": "25",
            "descricao": "Smart Tv 32 Philco Led Ptv32g23agssblh Android Tv",
            "quantidade": "1",
            "valorUnitario": "969"
        }
    ]
}'


RESPONSE
{
    "data": {
        "id": 10,
        "dataPedido": "2024-03-26",
        "valorPedido": 11469.00,
        "formaPagamento": "PIX",
        "estadoPedido": "AGUARDANDO_PAGAMENTO",
        "emailUsuario": "ernesto.sambongo@outlook.com",
        "itensPedido": [
            {
                "id": 13,
                "descricao": "Iphone 15 pro max",
                "quantidade": 3,
                "valorUnitario": 3500,
                "idProduto": 10
            },
            {
                "id": 14,
                "descricao": "Smart Tv 32 Philco Led Ptv32g23agssblh Android Tv",
                "quantidade": 1,
                "valorUnitario": 969,
                "idProduto": 25
            }
        ]
    },
    "mensagem": "Pedido efetuado com sucesso!"
}

```


### Listar pedidos

Serviço responsavel em buscar todos os pedidos na base do recurso de pagination, no caso do exemplo em causa, foi usado a pagina 0 pedindo 3 elementos.
```
Request:
curl --location 'http://127.0.0.1:8083/pedido/buscar-todos?pagina=2&tamanho=10' \
--header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdXJlbG1hLnNhbWJvbmdvQG91dGxvb2suY29tIiwiaWF0IjoxNzExNDY4Njk2LCJleHAiOjE3MTY2NTI2OTYsInJvbGUiOiJST0xFX0NVU1RPTUVSIiwibmFtZSI6Imp1cmVsbWEuc2FtYm9uZ28iLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiUkVBRF9NWV9QUk9GSUxFIn1dfQ.ovWdli1Ev96tgH0zv6pLJej70c05uS5vleiuTBrTXmE'

RESPONSE
{
    "data": [
        {
            "id": 1,
            "dataPedido": "2024-03-26",
            "valorPedido": 59996.00,
            "formaPagamento": "PIX",
            "estadoPedido": "AGUARDANDO_PAGAMENTO",
            "emailUsuario": "ernesto.sambongo@outlook.com",
            "itensPedido": [
                {
                    "id": 1,
                    "descricao": "Notebook Asus Rog Strix G16 Core I9 16gb 512ssd W11 Rtx 4060 Cor Cinza",
                    "quantidade": 2,
                    "valorUnitario": 29998.00,
                    "idProduto": 4
                }
            ]
        },
        {
            "id": 2,
            "dataPedido": "2024-03-26",
            "valorPedido": 8196.00,
            "formaPagamento": "PIX",
            "estadoPedido": "AGUARDANDO_PAGAMENTO",
            "emailUsuario": "ernesto.sambongo@outlook.com",
            "itensPedido": [
                {
                    "id": 2,
                    "descricao": "Smart Tv Led 50 Google Tv Uhd 4k Tcl 50p635 3 Hdmi 1 Usb",
                    "quantidade": 2,
                    "valorUnitario": 4098.00,
                    "idProduto": 3
                }
            ]
        },
        {
            "id": 3,
            "dataPedido": "2024-03-26",
            "valorPedido": 8196.00,
            "formaPagamento": "PIX",
            "estadoPedido": "AGUARDANDO_PAGAMENTO",
            "emailUsuario": "ernesto.sambongo@outlook.com",
            "itensPedido": [
                {
                    "id": 3,
                    "descricao": "Smart Tv Led 50 Google Tv Uhd 4k Tcl 50p635 3 Hdmi 1 Usb",
                    "quantidade": 2,
                    "valorUnitario": 4098.00,
                    "idProduto": 3
                }
            ]
        }
    ],
    "paginator": {
        "pageNumber": 0,
        "totalElements": 10,
        "totalPages": 4
    }
}
```



### Buscar pedido por ID


O serviço a baixo é responsavel em buscar pedidos por ID do pedido que no caso foi usado no pedido de codigo 8
```
Request:
curl --location 'http://127.0.0.1:8083/pedido/buscarPorId?idPedido=1' \
--header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdXJlbG1hLnNhbWJvbmdvQG91dGxvb2suY29tIiwiaWF0IjoxNzExNDY4Njk2LCJleHAiOjE3MTY2NTI2OTYsInJvbGUiOiJST0xFX0NVU1RPTUVSIiwibmFtZSI6Imp1cmVsbWEuc2FtYm9uZ28iLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiUkVBRF9NWV9QUk9GSUxFIn1dfQ.ovWdli1Ev96tgH0zv6pLJej70c05uS5vleiuTBrTXmE'

RESPONSE

RESPONSE
{
    "data": {
        "id": 8,
        "dataPedido": "2024-03-26",
        "valorPedido": 71592.00,
        "formaPagamento": "PIX",
        "estadoPedido": "PAGO",
        "emailUsuario": "ernesto.sambongo@outlook.com",
        "itensPedido": [
            {
                "id": 8,
                "descricao": "Smart Tv Led 50 Google Tv Uhd 4k Tcl 50p635 3 Hdmi 1 Usb",
                "quantidade": 2,
                "valorUnitario": 4098.00,
                "idProduto": 3
            },
            {
                "id": 9,
                "descricao": "Mini Geladeira Portátil Veicular 24 Litros 12v Função Aquece",
                "quantidade": 2,
                "valorUnitario": 1700.00,
                "idProduto": 5
            },
            {
                "id": 10,
                "descricao": "Notebook Asus Rog Strix G16 Core I9 16gb 512ssd W11 Rtx 4060 Cor Cinza",
                "quantidade": 2,
                "valorUnitario": 29998.00,
                "idProduto": 4
            }
        ]
    },
    "mensagem": "Buscar efetivada com sucesso!"
}

```

### Valida pagamento pedido

Serviço responsavel em validar o pagamento de um pedido ou cancelar o mesmo por ação do cliente.

```
Request:
curl --location 'http://127.0.0.1:8083/pedido/validar-pagamento-ou-cancelar' \
--header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdXJlbG1hLnNhbWJvbmdvQG91dGxvb2suY29tIiwiaWF0IjoxNzExNDY4Njk2LCJleHAiOjE3MTY2NTI2OTYsInJvbGUiOiJST0xFX0NVU1RPTUVSIiwibmFtZSI6Imp1cmVsbWEuc2FtYm9uZ28iLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiUkVBRF9NWV9QUk9GSUxFIn1dfQ.ovWdli1Ev96tgH0zv6pLJej70c05uS5vleiuTBrTXmE' \
--header 'Content-Type: application/json' \
--data '{
    "idPedido":27,
    "dataPagamento":"2024-03-25",
    "estadoPedido":"CANCELADO"
}'

RESPONSE
{
    "data": {
        "id": 6,
        "dataPedido": "2024-03-26",
        "valorPedido": 8196.00,
        "dataPagamento": null,
        "formaPagamento": "PIX",
        "estadoPedido": "PAGO",
        "emailUsuario": "ernesto.sambongo@outlook.com"
    },
    "mensagem": "Operação realizada com sucesso"
}
```

### Deletar pedido

Serviço abaixo é responsavel em remover um pedido antes feito.

```
Request:
curl --location --request DELETE 'http://127.0.0.1:8083/pedido/apagar?idPedido=29' \
--header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdXJlbG1hLnNhbWJvbmdvQG91dGxvb2suY29tIiwiaWF0IjoxNzExNDY4Njk2LCJleHAiOjE3MTY2NTI2OTYsInJvbGUiOiJST0xFX0NVU1RPTUVSIiwibmFtZSI6Imp1cmVsbWEuc2FtYm9uZ28iLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiUkVBRF9NWV9QUk9GSUxFIn1dfQ.ovWdli1Ev96tgH0zv6pLJej70c05uS5vleiuTBrTXmE'

RESPONSE
{
    "mensagem": "O pedido com o código: 4 eliminado com sucesso!"
}
```