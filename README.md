# Trabalhos para disciplina de Lógica e Matemática Discreta
Faculdade de Engenharia Elétrica<br />
Universidade Federal de Uberlândia

## Autor

- Murilo Rezende Montano

## Pré-requisitos

Para a execução desse projeto serão necessários o Java Runtime Environment, o Java SE Development Kit e a ferramenta de automação de compilação Maven.

### Instalação

Primeiramente, atualize a lista de pacotes:
```
$ sudo apt update
```

Instale o Java Runtime Environment com o comando:
```
$ sudo apt install default-jre
```

Instale o Java SE Development Kit:
```
$ sudo apt install default-jdk
```

Por fim, instale a ferramenta Maven com:
```
$ sudo apt install maven
```

## Execução:
Para instalar as dependências, use o comando:
```
$ mvn install
```

Para iniciar a execução do programa, utilize o comando: 
```
$ mvn exec:java -Dexec.mainClass="com.ufu.ProjetoLMD.ProjetoLMD"
```
