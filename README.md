# simian-api [![CI](https://github.com/m1theus/simian-api/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/m1theus/simian-api/actions/workflows/ci.yml) ![Coverage](.github/badges/jacoco.svg) ![Branches](.github/badges/branches.svg)



Api responsável por identificar se uma sequência de DNA pertence a um humano ou um símio.

Conforme a imagem abaixo, para um DNA ser identificado deve-se ter uma sequência de letras iguais
nas ordens, **horizontal**, **vertical** ou **diagonal**

![image](https://user-images.githubusercontent.com/43992531/135917704-96b1701f-0aa7-4117-9090-053585987f98.png)

---

### API

- [simian-api Swagger](http://simian-api.us-east-1.elasticbeanstalk.com)

A api se encontra na AWS, regisão us-east-1, publicada pelo [CI](https://github.com/m1theus/simian-api/actions/workflows/deploy.yml) no ambiente [AWS Elastic Beanstalk
](https://aws.amazon.com/pt/elasticbeanstalk/)

cURL:

```shell
curl -X POST "http://simian-api.us-east-1.elasticbeanstalk.com/dna/simian" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"dna\": [\"ATGCGA\", \"CAGTGC\", \"TTATGT\", \"AGAAGG\", \"CCCCTA\", \"TCACTG\"]}"
```

```shell
curl -X GET "http://simian-api.us-east-1.elasticbeanstalk.com/dna/stats" -H "accept: */*"

```

---

### Dependências

- Java 11
- Gradle 7+
- [Spring](https://spring.io/)
  - Spring Boot Web
  - Spring Data MongoDB
  - Spring Validation
  - Spring Actuator
- [TestContainers](https://www.testcontainers.org/modules/databases/mongodb/)
- [Swagger](https://springfox.github.io/springfox/docs/current/)
- [MongoDB](https://www.mongodb.com/cloud/atlas)
- [Docker](https://docs.docker.com/engine/)

---

### Executando o projeto localmente

Basta executar o shell `start.sh`

```bash
./start.sh
```

ou

```bash
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun
```

---

### Performance Test

[K6](https://k6.io/docs/);
![image](https://user-images.githubusercontent.com/43992531/135917596-ccb26186-138f-4e40-9eed-6ee961f869ba.png)

Monitoramento [ELB](https://aws.amazon.com/pt/elasticbeanstalk/);
![image](https://user-images.githubusercontent.com/43992531/135917514-5e103f7e-d224-4053-bdc1-412128ba66fa.png)
