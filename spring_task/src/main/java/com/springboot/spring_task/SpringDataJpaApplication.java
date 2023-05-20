package com.springboot.spring_task;

import com.springboot.spring_task.configuration.FileStorage;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties({
        FileStorage.class
})
public class SpringDataJpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }
    @Bean
    public OpenAPI openAPI(){
        Info info = new Info().title("TaskService").description("Сервис управления задачами").version("V1");
        return new OpenAPI().info(info);
    }
}
