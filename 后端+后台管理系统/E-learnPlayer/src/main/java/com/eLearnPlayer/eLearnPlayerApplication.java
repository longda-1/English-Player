package com.eLearnPlayer;

//import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.eLearnPlayer.mapper") // 指定扫描 Mapper 接口的包路径
public class eLearnPlayerApplication {

    public static void main(String[] args) {
        SpringApplication.run(eLearnPlayerApplication.class, args);
    }

}

