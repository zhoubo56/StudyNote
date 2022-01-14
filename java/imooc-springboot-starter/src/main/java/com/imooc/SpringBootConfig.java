package com.imooc;

import com.imooc.pojo.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBootConfig {

    @Bean
    public Student student() {
        return new Student("jack", 18);
    }
}
