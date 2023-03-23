package pe.gob.essalud.pagosmanteapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PagosmanteapiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PagosmanteapiApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PagosmanteapiApplication.class);
    }

}
