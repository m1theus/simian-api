package io.github.m1theus.simianapi;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public abstract class AbstractBaseTestContainer {

    @Container
    final static MongoDBContainer MONGO_DB_CONTAINER = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))
            .withExposedPorts(27017);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", MONGO_DB_CONTAINER::getHost);
        registry.add("spring.data.mongodb.port", MONGO_DB_CONTAINER::getFirstMappedPort);
    }
}
