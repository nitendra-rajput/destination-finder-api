package com.afkl.cases.destination.finder;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class DestinationFinderApplication {
    public static void main(String[] args) {
        SpringApplication.run(DestinationFinderApplication.class, args);
    }
}
