# Invext

### Resumo
Projeto criado utilizando o framework Spring (versão 3.4.0) e Java 22 com o objetivo de gerenciar uma fila de solicitações e a distribuição entre times de atendimento.

### Estrutura
| Pacote     | Descrição                                                                |
|------------|--------------------------------------------------------------------------|
| config     | Classes de configuração do projeto                                       |
| controller | Mapeamento dos endpoints disponibilizados na API                         |
| dto        | DTOs usados para expor os dados ou trafegar entre as diferentes camadas  |
| exception  | Exceptions customizadas criadas para o projeto                           |
| model      | Mapeamento das entidades do banco                                        |
| repository | Classes para manipulação do banco                                        |
| service    | Camada de negócio. Classes utilizadas pelo controller (API) ou scheduler |


### Banco
Foi utilizado um banco H2 em memória para simplificar o processo de execução do projeto. A estrutura inicial é criada em memória com base nas entidades mapeadas no package **model**.
Uma carga inicial de dados é feita utilizando o arquivo **/resources/data.sql** para popular as tabelas de times, atendentes e tipos de solicitação.

A configuração para distribuição de solicitações aos times/atendentes é feita a partir da tabela **time_tipo_solicitacao**. Qualquer solicitação de um tipo que não estiver mapeado nessa tabela será distribuída ao time configurado com a flag **time_padrao=true**.

### Regras de negócio
- A distribuição das solicitações é feita com base nos atendentes vinculados a cada time e considerando a regra de negócio de que cada atendente pode ter no máximo 3 solicitações em atendimento
- Quando uma solicitação é criada, ela entra no sistema com status **ABERTO**
- Quando a solicitação é distribuída para um atendente seu status muda para **EM_ATENDIMENTO** e o atendente é vinculado a solicitação
- Caso os atendentes já tenham atingido o limite de solicitações em andamento, outras solicitações permanecerão no status **ABERTO** até que uma posição nos times de atendimento fique disponível

### Rotina de distribuição
A rotina que faz a distribuição das solicitações é disparada de 1 em 1 minuto. O código que faz essa distribuição está localizado na classe **DistribuicaoService** e centraliza toda a lógica de negócio referente a distribuição de solicitações.

A distribuição é feita com base na data de abertura, garantindo que solicitações mais antigas serão distribuídas primeiro.

### Como executar o projeto
Rodar a classe InvextApplication. Ao executar, a API será disponibilizada na porta 8080 (http) e um console H2 ficará disponível na url http://localhost:8080/h2-console (Usuário: sa, sem senha, Url: jdbc:h2:mem:testdb)

### API
Para comodidade, uma collection do postman foi incluída na raíz do projeto. Os métodos podem ser utilizados para manipulações dos dados de solicitações e acompanhamento da distribuição.

| Método     | URL                         | Descrição                                                                                                     |
|------------|-----------------------------|---------------------------------------------------------------------------------------------------------------|
| GET        | /solicitacoes               | Lista solicitações e aceita filtro de status                                                                  |
| POST       | /solicitacoes               | Cria solicitações, descrição é opcional e os demais campos (titulo, tipo) são obrigatórios                    |
| PATCH      | /solicitacoes/{id}/concluir | Fecha a solicitação e libera o atendente para receber uma nova solicitação                                    |
| GET        | /solicitacoes/atendimento   | Método principal para listar os times de atendimento, atendentes e acompanhar a distribuição das solicitações |

### Links
- https://start.spring.io
- https://spring.io/projects/spring-framework
- https://www.h2database.com/html/main.html

