# Plano de formação
Este projeto é um plano de formação, para exercer habilidades que estão no dia a dia de um projeto real, implementando features reais, e com isto impactar
diretamente os usuários finais. Este plano de formação tem o objetivo de ajudar a desenvolver e evoluir profissionalmente.
## FASE 1 - Desenvolvimento de um projeto do Zero
Tecnologias utilizadas:
- Maven
- GIT
- Spring MVC
- Spring Boot
- Mock MVC
- Kotlin

### Objetivo:
Criar um sistema que simule um Banco
### Descrição: 
O sistema deve permitir cadastrar cliente/conta. Após o cliente cadastrado deve
ser possível realizar as seguintes operações:
- Depósito
- Saque
- Transferência
- Saldo
- Extrato
### Regras de negócio
- O saldo da conta não pode ficar negativo
- Nas operações de transferência deverá ser verificado se existe saldo suficiente na
conta
- Cada cliente pode ter apenas uma conta
- O serviço de criar uma conta deverá retornar um identificador da conta para futuras
movimentações
- Ao solicitar um extrato, deverá constar toda movimentação da conta, como
transferência, depósito e saque
- Em uma operação de transferência tanto a conta de destino quanto a de origem
devem ser válidas, e não podem ser iguais
- Deverá existir um serviço para o cliente consultar sua conta
Requisitos técnicos
- Os dados deverão ser persistidos em banco de dados (utilizar container)
- Os serviços devem ser expostos no formato RESTFull com as respostas em Json
- O gerenciamento das dependências do projeto deve ser feita utilizando Maven
- A linguagem utilizada deve ser Java/Kotlin
- O sistema deve ser inicializado usando Spring Boot
- O projeto deverá estar versionado em seu GitHub, em um repositório privado
- A cobertura de testes deve ser de no mínimo 60% (Utilizar o codecov no GitHub)
- Testes integrados serão bem vindos nessa fase.
