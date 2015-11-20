package ro.fils.semanticweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author andre
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class SemanticAppConfig {

    public static void main(String[] args) {
        SpringApplication.run(SemanticAppConfig.class, args);
    }
}
