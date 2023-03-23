package pe.gob.essalud.pagosmanteapi.config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableOAuth2Sso
@Configuration
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {
    @Value("${auth-server}")
    private String logoutUrl;

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.client.access-token-uri}")
    private String accessTokenUri;

    public void configure(HttpSecurity http) throws Exception {

        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/js/**", "/login**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().
                logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl(logoutUrl+"/exit")
                .permitAll()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        ;
    }
    @Bean
    public OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        details.setAccessTokenUri(accessTokenUri);
        return details;
    }
    @Bean
    public OAuth2RestTemplate createRestTemplate(@Qualifier("oauth2ClientContext") OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(oAuth2ProtectedResourceDetails(), clientContext);
    }

}
