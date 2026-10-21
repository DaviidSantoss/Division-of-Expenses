# 💸 Expense Splitter API

API REST para divisão de despesas entre grupos de pessoas, desenvolvida com Java 21 e Spring Boot.

---

## 📌 Sobre o projeto

O Expense Splitter resolve um problema cotidiano: dividir contas entre grupos. Seja uma viagem, um aluguel compartilhado ou um jantar — alguém paga a conta inteira e o sistema calcula automaticamente quanto cada pessoa deve. Cada membro registra seu pagamento individualmente e um endpoint de saldo mostra em tempo real quem ainda deve e para quem.

---

## 🚀 Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 21 | Linguagem principal |
| Spring Boot | Framework web |
| Spring Security + JWT | Autenticação stateless |
| Spring Data JPA | Persistência |
| MySQL | Banco de dados relacional |
| Flyway | Migrations versionadas |
| Lombok | Redução de boilerplate |
| Springdoc OpenAPI | Documentação automática |
| Docker Compose | Ambiente local |

---

## ⚙️ Como rodar localmente

### Pré-requisitos
- Java 21
- Docker e Docker Compose
- Maven

### Passos

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/expense-splitter

# Copie o arquivo de exemplo e preencha com suas credenciais
cp application.yml.example src/main/resources/application.yml

# Suba o banco com Docker
docker-compose up -d

# Rode a aplicação
./mvnw spring-boot:run
```

---

## 📄 Documentação

A documentação completa da API é gerada automaticamente via Swagger e fica disponível após subir a aplicação:

```
http://localhost:8080/swagger-ui/index.html
```

Todos os endpoints estão documentados com exemplos de request e response. Para testar endpoints protegidos diretamente pelo Swagger, clique em **Authorize** e insira o token JWT obtido no login.

---

## 🗂️ Modelo de dados

```
User            → usuário da plataforma
Groups          → grupo de pessoas
GroupOfMembers  → relação usuário ↔ grupo
Cost            → despesa registrada por um membro
ExpenseDivision → quanto cada membro deve em uma despesa
Payment         → pagamento individual de uma dívida
```

---

## 🔁 Fluxo principal

```
1. Cadastro e login → recebe JWT
2. Criar grupo
3. Adicionar membros
4. Registrar despesa (EQUALITY ou BY_VALUE)
5. Sistema gera divisões automaticamente
6. Cada membro registra seu pagamento
7. Endpoint de saldo mostra quem deve quanto
```

---

## 📡 Endpoints

> Todos os endpoints exceto `/auth/register` e `/auth/login` exigem o header:
> ```
> Authorization: Bearer <token>
> ```

---

### 🔐 Autenticação

#### Registrar usuário
```
POST http://localhost:8080/auth/register
```

**Request body:**
```json
{
  "name": "David Santos",
  "email": "david@email.com",
  "password": "senha123"
}
```

**Response `201 Created`:**
```
Usuário cadastrado com sucesso.
```

---

#### Login
```
POST http://localhost:8080/auth/login
```

**Request body:**
```json
{
  "email": "david@email.com",
  "password": "senha123"
}
```

**Response `200 OK`:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

> Copie o token e use como `Bearer Token` nas próximas requisições.

---

### 👤 Usuários

#### Buscar usuário por ID
```
GET http://localhost:8080/users/1005
```

**Response `200 OK`:**
```json
{
  "id": 1005,
  "name": "David Santos",
  "email": "david@email.com",
  "createdIn": "2025-01-15T10:30:00",
  "enabled": true
}
```

---

#### Listar todos os usuários
```
GET http://localhost:8080/users?page=0&size=12&direction=asc
```

**Response `200 OK`:**
```json
{
  "_embedded": {
    "userDTOList": [
      { "id": 1001, "name": "Ana Lima", "email": "ana@email.com" },
      { "id": 1002, "name": "Carlos Souza", "email": "carlos@email.com" }
    ]
  },
  "page": {
    "size": 12,
    "totalElements": 2,
    "totalPages": 1,
    "number": 0
  }
}
```

---

#### Criar usuário
```
POST http://localhost:8080/users
```

**Request body:**
```json
{
  "name": "Maria Silva",
  "email": "maria@email.com",
  "password": "senha123"
}
```

**Response `200 OK`:**
```json
{
  "id": 1006,
  "name": "Maria Silva",
  "email": "maria@email.com",
  "enabled": true
}
```

---

#### Atualizar usuário
```
PUT http://localhost:8080/users
```

**Request body:**
```json
{
  "id": 1005,
  "name": "David Santos Atualizado",
  "email": "david.novo@email.com"
}
```

**Response `200 OK`:**
```json
{
  "id": 1005,
  "name": "David Santos Atualizado",
  "email": "david.novo@email.com"
}
```

---

#### Desabilitar usuário (soft-delete)
```
PATCH http://localhost:8080/users/1005
```

**Response `200 OK`:**
```json
{
  "id": 1005,
  "name": "David Santos",
  "enabled": false
}
```

---

#### Deletar usuário
```
DELETE http://localhost:8080/users/1014
```

**Response `204 No Content`**

---

### 👥 Grupos

#### Criar grupo
```
POST http://localhost:8080/groups
```

**Request body:**
```json
{
  "name": "Viagem Cancún"
}
```

**Response `200 OK`:**
```json
{
  "id": 1,
  "name": "Viagem Cancún",
  "createdBy": 1005,
  "enabled": true
}
```

---

#### Listar todos os grupos
```
GET http://localhost:8080/groups?page=0&size=12&direction=asc
```

**Response `200 OK`:**
```json
{
  "_embedded": {
    "groupsDTOList": [
      { "id": 1, "name": "Viagem Cancún" },
      { "id": 2, "name": "República" }
    ]
  }
}
```

---

#### Buscar grupo por ID
```
GET http://localhost:8080/groups/1
```

**Response `200 OK`:**
```json
{
  "id": 1,
  "name": "Viagem Cancún",
  "createdBy": 1005,
  "enabled": true
}
```

---

#### Atualizar grupo
```
PUT http://localhost:8080/groups
```

**Request body:**
```json
{
  "id": 1,
  "name": "Viagem Cancún 2025"
}
```

**Response `200 OK`:**
```json
{
  "id": 1,
  "name": "Viagem Cancún 2025"
}
```

---

#### Adicionar membro ao grupo
```
POST http://localhost:8080/groups/1/members/1016
```

Não precisa de body — `groupId` e `userId` vêm pela URL.

**Response `200 OK`:**
```json
{
  "id": 10,
  "group": 1,
  "user": 1016,
  "entered": "2025-01-15T11:00:00"
}
```

---

#### Desabilitar grupo (soft-delete)
```
PATCH http://localhost:8080/groups/2
```

**Response `200 OK`:**
```json
{
  "id": 2,
  "name": "República",
  "enabled": false
}
```

---

### 💸 Despesas

#### Listar despesas do grupo
```
GET http://localhost:8080/groups/1/costs
```

**Response `200 OK`:**
```json
[
  {
    "id": 1,
    "description": "Jantar no restaurante",
    "totalValue": 120.00,
    "type": "EQUALITY",
    "createdIn": "2025-01-15T20:00:00"
  },
  {
    "id": 2,
    "description": "Uber da volta",
    "totalValue": 30.00,
    "type": "BY_VALUE",
    "createdIn": "2025-01-15T23:00:00"
  }
]
```

---

#### Registrar despesa — divisão igualitária
```
POST http://localhost:8080/groups/1/costs
```

**Request body:**
```json
{
  "paidFor": 1005,
  "description": "Jantar no restaurante",
  "totalValue": 120.00,
  "type": "EQUALITY"
}
```

> O sistema divide R$120,00 igualmente entre todos os membros do grupo automaticamente.

**Response `201 Created`:**
```json
{
  "id": 1,
  "groupId": 1,
  "paidFor": 1005,
  "description": "Jantar no restaurante",
  "totalValue": 120.00,
  "type": "EQUALITY",
  "createdIn": "2025-01-15T20:00:00"
}
```

---

#### Registrar despesa — divisão por valor
```
POST http://localhost:8080/groups/1/costs
```

**Request body:**
```json
{
  "paidFor": 1005,
  "description": "Uber da volta",
  "totalValue": 30.00,
  "type": "BY_VALUE",
  "divisions": [
    { "userId": 1016, "value": 20.00 },
    { "userId": 1017, "value": 10.00 }
  ]
}
```

> A soma de `divisions` deve ser igual ao `totalValue`. Se não bater, a requisição é rejeitada.

**Response `201 Created`:**
```json
{
  "id": 2,
  "groupId": 1,
  "paidFor": 1005,
  "description": "Uber da volta",
  "totalValue": 30.00,
  "type": "BY_VALUE",
  "createdIn": "2025-01-15T23:00:00"
}
```

---

### 📊 Saldo

#### Ver saldo do grupo
```
GET http://localhost:8080/groups/1/balance
```

**Response `200 OK`:**
```json
[
  { "userName": "David Santos", "balance": 80.00 },
  { "userName": "Maria Silva",  "balance": -40.00 },
  { "userName": "Pedro Costa",  "balance": -40.00 }
]
```

> ✅ **Positivo** → outras pessoas te devem  
> ❌ **Negativo** → você ainda deve

---

## 💡 Decisões técnicas

- **Soft-delete** em usuários e grupos — histórico financeiro preservado
- **BigDecimal** para todos os valores monetários — sem erro de ponto flutuante
- **Constructor injection** em todos os services — sem `@Autowired` em campos
- **Status de despesa derivado** — calculado em runtime, nunca persistido, evitando inconsistência
- **Divisão em transação única** — `Cost` e `ExpenseDivision` são salvas juntas ou nenhuma é salva
- **JWT stateless** — sem sessão no servidor, escalável horizontalmente