package com.study.fang.springbootall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 *
 * @author bestFang666
 * @date 2022/8/24 16:10
 */
@MapperScan("com.study.fang.springbootall.mapper")
@SpringBootApplication
public class SpringbootallApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootallApplication.class, args);
    }

}
