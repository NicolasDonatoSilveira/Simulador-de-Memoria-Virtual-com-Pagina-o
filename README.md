# Simulador de Memória Virtual com Paginação

Projeto desenvolvido para a disciplina de **Análise e Aplicação de Sistemas Operacionais** da **Universidade do Vale do Rio dos Sinos (UNISINOS)**.

## Objetivo

Implementar um simulador de gerenciamento de memória virtual utilizando paginação, representando o funcionamento de uma Unidade de Gerenciamento de Memória (MMU).

O simulador realiza:

- Tradução de endereços virtuais para físicos;
- Gerenciamento de tabela de páginas;
- Detecção de **Page Hit** e **Page Fault**;
- Carregamento de páginas na memória principal;
- Substituição de páginas utilizando o algoritmo **FIFO (First In, First Out)**.

---

# Especificações

O simulador segue os requisitos definidos no enunciado da atividade.

| Item | Valor |
|------|-------|
| Memória Virtual | 1 MB |
| Memória Física | 64 KB |
| Tamanho da Página | 8 KB |
| Quantidade de Páginas Virtuais | 128 |
| Quantidade de Frames | 8 |
| Algoritmo de Substituição | FIFO |

---

# Estrutura do Projeto

```
Projeto
│
├── data
│   ├── processo1.txt
│   └── processo2.txt
│
└── src
    ├── Main.java
    ├── MMU.java
    ├── Processo.java
    ├── Frame.java
    └── PaginaInfo.java
```

---

# Classes

## Main.java

Classe responsável por iniciar a simulação.

Cria a MMU e inicia a execução dos processos.

---

## MMU.java

Classe principal do projeto.

Responsável por:

- traduzir endereços;
- gerenciar a memória física;
- consultar as tabelas de páginas;
- tratar Page Fault;
- executar o algoritmo FIFO;
- calcular o endereço físico.

---

## Processo.java

Representa um processo que realiza acessos à memória virtual.

Cada processo lê seu respectivo arquivo de dados e gera acessos à MMU.

---

## Frame.java

Representa um frame da memória física.

Cada frame armazena:

- número do frame;
- processo proprietário;
- página carregada.

---

## PaginaInfo.java

Representa uma entrada da tabela de páginas.

Cada entrada contém:

- bit de presença;
- número do frame correspondente.

---

# Funcionamento

Para cada acesso à memória:

1. O processo gera um endereço virtual.
2. A MMU calcula:
   - Página;
   - Offset.
3. A tabela de páginas é consultada.
4. Caso a página esteja carregada:
   - ocorre um **Page Hit**.
5. Caso contrário:
   - ocorre um **Page Fault**;
   - a página é carregada em um frame livre;
   - caso não exista frame livre, o algoritmo FIFO substitui a página mais antiga.

---

# Algoritmo FIFO

Quando a memória física está completamente ocupada, o simulador utiliza o algoritmo **FIFO (First In, First Out)**.

A primeira página carregada na memória é a primeira a ser removida.

Exemplo:

```
PAGE FAULT

Substituindo pagina 3 do processo 1

Pagina carregada no Frame 0
```

---

# Exemplo de Saída

```
PROCESSO 1

Endereco Virtual: 26685

Pagina: 3

Offset: 2109

PAGE FAULT

Pagina carregada no Frame 0

Endereco Fisico: 2109
```

Após o carregamento:

```
Frames

Frame 0 -> Processo 1 Pagina 3

Frame 1 -> Processo -1 Pagina -1

...
```

---

# Tecnologias Utilizadas

- Java 21
- Programação Orientada a Objetos
- Estrutura de Dados (Queue para FIFO)
- Java Collections Framework

---

# Como Executar

## Compilar

```bash
cd src

javac *.java
```

## Executar

```bash
java Main
```

---

# Conceitos de Sistemas Operacionais Aplicados

- Memória Virtual
- Paginação
- Memória Física
- MMU (Memory Management Unit)
- Endereçamento Virtual
- Endereçamento Físico
- Tabela de Páginas
- Page Hit
- Page Fault
- Algoritmo FIFO

---

# Autor

**Nicolas Donato Silveira**

Projeto desenvolvido para a disciplina de **Análise e Aplicação de Sistemas Operacionais** – **UNISINOS**.
