# ControleEstoqueAPI

API REST para controle de estoque (Spring Boot). Este README descreve como configurar, compilar, executar e testar a API no ambiente Windows.

## Visão geral
Projeto Spring Boot contendo endpoints para categorias, vendas, produtos, estoque e clientes. Endpoints principais:
- GET/POST/PUT/DELETE /api/categorias
- GET/POST/DELETE /api/vendas
(Ver pacotes `controller` para lista completa.)

## Requisitos
- Java 11+ (recomendado Java 17)
- Maven ou Gradle (o projeto pode ter wrapper: `mvnw.cmd` ou `gradlew.bat`)
- Git (opcional)
- Banco de dados configurado conforme `src/main/resources/application.properties` ou `application.yml`

Verifique qual build tool está presente (procure `pom.xml` para Maven ou `build.gradle` para Gradle).

## Configuração
1. Abra o projeto em VS Code.
2. Verifique conexões de banco em `src/main/resources/application.properties`. Ajuste se necessário:
   - Para H2 em memória (teste rápido) adicione/garanta:
     spring.datasource.url=jdbc:h2:mem:testdb
     spring.datasource.driverClassName=org.h2.Driver
     spring.h2.console.enabled=true
   - Para MySQL/Postgres, configure URL, usuário e senha apropriados.

3. Se usar Lombok, habilite Annotation Processing no IDE (VS Code: instale extensão Lombok/Java support).

## Executando (Windows PowerShell)
Abra o terminal integrado do VS Code (Ctrl+`) e vá para a pasta do projeto:
```powershell
cd C:\Users\Pedro\Desktop\ControleEstoqueAPI\api-version
```

Com Maven (se houver `mvnw.cmd` use o wrapper):
```powershell
# Com wrapper
.\mvnw.cmd spring-boot:run

# Ou com mvn instalado
mvn spring-boot:run
```

Com Gradle:
```powershell
# Com wrapper
.\gradlew.bat bootRun

# Ou com gradle instalado
gradle bootRun
```

Gerar JAR e rodar:
```powershell
.\mvnw.cmd clean package
java -jar .\target\<artifactId>-<version>.jar

# ou Gradle
.\gradlew.bat clean bootJar
java -jar .\build\libs\<nome>-<versao>.jar
```

Parar servidor: Ctrl+C no terminal onde está rodando.

## Testes básicos (curl / PowerShell)
Exemplos de requisições (assume servidor em http://localhost:8080).

Criar categoria:
```powershell
curl -X POST http://localhost:8080/api/categorias `
  -H "Content-Type: application/json" `
  -d '{"nome":"Bebidas","descricao":"Bebidas e sucos"}'
```

Listar categorias:
```powershell
curl http://localhost:8080/api/categorias
```

Criar venda (exemplo mínimo — ajuste IDs conforme dados existentes):
```powershell
curl -X POST http://localhost:8080/api/vendas `
  -H "Content-Type: application/json" `
  -d '{
    "clienteId": 1,
    "itens": [
      {"produtoId": 2, "quantidade": 3},
      {"produtoId": 4, "quantidade": 1}
    ]
  }'
```

Obter venda por id:
```powershell
curl http://localhost:8080/api/vendas/1
```

Use Postman / Insomnia se preferir interface gráfica.

## PS
Fiz a reprodução do código e a lógica das entidades Vendas e Cliente
Não consegui entregar o video por conta de problemas que tive com o XAMPP e uma falha em um dos arquivos do maven
