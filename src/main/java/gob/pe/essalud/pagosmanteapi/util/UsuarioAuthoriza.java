package gob.pe.essalud.pagosmanteapi.util;

import gob.pe.essalud.pagosmanteapi.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAuthoriza {
    @Autowired
    private OAuth2RestTemplate template;

    @Value("${security.oauth2.resource.user-info-uri}")
    private String endpoint;

    public Usuario getUsuario(){
        return template.getForObject(endpoint, Usuario.class);
    }
}
