# Usando o Docker oficial do OpenJDK 21
FROM openjdk:21

# Definindo o diretório de trabalho dentro do contêiner
WORKDIR /opt

# Expondo a porta do aplicativo
ENV PORT 8080
EXPOSE 8080

# Baixa o script wait-for-it.sh e define como executável
RUN curl -o /wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh && \
    chmod +x /wait-for-it.sh

# Copia o arquivo JAR gerado para o contêiner
COPY target/*.jar /opt/app.jar

# Define o comando de execução para iniciar a aplicação Java com o script wait-for-it.sh
ENTRYPOINT ["exec", "/wait-for-it.sh", "mysqldb:3306", "--", "java", "$JAVA_OPTS", "-jar", "app.jar"]
