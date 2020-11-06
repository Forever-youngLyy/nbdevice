package com.lcp.nbdevice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class NbdeviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NbdeviceApplication.class, args);
    }

}
