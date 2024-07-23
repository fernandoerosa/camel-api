# Camel - K

> Modo de operação do Camel nativamente no Kubernetes, atraves de uma CLI/ Linha de comando Kamel.
> 

> O Camel no Kubernetes roda em um projeto Quarkus, cada nova integração criada, gera um service que pode expor as rotas ou não.
> 

## Estrutura da Arquitetura

### Imagens de Arquitetura

- **Imagem 1**: Representa a arquitetura geral.
- **Imagem 2**: Detalhes dos componentes integrados.

## Desenvolvimento com Camel K

### Desafios do Camel K

- **Importações Manuais**: O desenvolvimento interno com Camel K é mais demorado, pois é necessário fazer os imports manualmente.
- **Ausência de Autocomplete**: Não há pacotes localmente disponíveis, o que dificulta o uso de autocomplete.
- **Verbosidade**: O código torna-se mais verboso.

### Estratégia de Uso

A proposta é utilizar duas instâncias do Camel:

1. **Camel na API**
    - **Descrição**: Esta instância substitui a API atual, caso haja necessidade de troca.
    - **Tecnologia**: Aplicação Quarkus.
    - **Funcionalidade**: Responsável por redirecionamentos, tratamento de JSON, ajustes de body, e integrações internas com serviços como Elasticsearch, Google, API de pagamento, etc.
    - **Desenvolvimento**: Interno.

2. **Camel K no Kubernetes**
    - **Descrição**: Permite subir integrações através do envio de arquivos em diversas linguagens (Java, Groovy, TypeScript, Kotlin, YAML, etc.).
    - **Funcionalidade**: Focado em integrações criadas por clientes via blocos do Blocky. Configuramos os componentes que serão liberados e os clientes podem criar suas integrações conforme desejarem.
    - **Integração**: Este Camel K azul se comunica com as rotas do Camel API (amarelo) interno.

### Detalhes dos Serviços

- **Services Criados pelo Camel K**: Cada integração criada gera um endpoint e um serviço exclusivo.

## Implementação Quarkus com Camel

### Criação de Projeto

- **Novo Projeto Quarkus**: Projeto criado para implementar a API com Camel.
- **Arquivo de Rotas**: Expondo rotas como REST e convertendo JSON para maps para tratamento em Java.

### Autenticação e Permissão

- **Autenticação JWT**: Implementada como middleware, similar à API desenvolvida em Deno.
- **Componentes Usados**:
    - **Shiro**: Componente pronto do Quarkus para autenticação.
    - **Componente Próprio**: Implementação personalizada para JWT.

### Processors

- **Definição**: Em cada rota, é possível instanciar um processor (existente ou personalizado) que recebe o contexto da requisição para customização.
- **Exemplo 1**: Processor criado para acessar um serviço de autenticação e criar um token.
    
- **Exemplo 2**: Processor comportando-se como middleware para validação e acesso a banco de dados, entre outras funções.

### Headers na Requisição

- **Headers Enviados**:
    - **CoreUrl**: Recebido pelo Camel para direcionar a rota dinamicamente.
    - **Flag Legacy**: Determina o caminho que o Camel deve seguir (passando por um processor para transformar o dado ou indo direto para a rota certa).
