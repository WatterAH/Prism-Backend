package com.prism;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Clase principal de la aplicación Prism.
 *
 * @SpringBootApplication activa tres configuraciones a la vez:
 *   - @ComponentScan: detecta y registra automáticamente controladores, servicios y repositorios.
 *   - @EnableAutoConfiguration: configura Spring según las dependencias del proyecto (JPA, web, etc.).
 *   - @Configuration: permite definir beans adicionales si se requieren.
 *
 * Extiende SpringBootServletInitializer para que la aplicación pueda desplegarse
 * como archivo WAR en un servidor Tomcat externo, además de ejecutarse de forma
 * embebida durante el desarrollo con el método main.
 */
@SpringBootApplication
public class PrismApplication extends SpringBootServletInitializer {

    /**
     * Configura la aplicación para el despliegue en Tomcat externo (WAR).
     * Spring invoca este método automáticamente al arrancar desde el servidor.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PrismApplication.class);
    }

    /**
     * Punto de entrada para ejecutar la aplicación con Tomcat embebido (modo desarrollo).
     */
    public static void main(String[] args) {
        SpringApplication.run(PrismApplication.class, args);
    }
}
