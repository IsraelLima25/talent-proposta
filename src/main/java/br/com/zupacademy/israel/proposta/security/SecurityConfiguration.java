package br.com.zupacademy.israel.proposta.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                authorizeRequests.antMatchers(HttpMethod.GET, "/acompanhamentoPropostas/**")
                        .hasAuthority("SCOPE_proposta:read")
                        .antMatchers(HttpMethod.POST, "/propostas/**")
                        .hasAuthority("SCOPE_proposta:write")
                        .antMatchers(HttpMethod.GET, "/actuator/prometheus")
                        .permitAll()
                        .antMatchers(HttpMethod.PUT, "/cartoes/**")
                        .hasAuthority("SCOPE_cartao:write")
                        .antMatchers(HttpMethod.POST, "/biometrias/**")
                        .hasAuthority("SCOPE_cartao:write")
                        .antMatchers(HttpMethod.POST, "/viagens/**")
                        .hasAuthority("SCOPE_cartao:write")
                        .anyRequest().authenticated())
                .csrf().disable()
                /*Usar claims authorities keycloak ROLE_** */
                //.oauth2ResourceServer().jwt().jwtAuthenticationConverter(getJwtAuthenticationConverter());
                /*Usar claims JwtGrantedAuthoritiesConverter SCOPE_*/
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

    /* Keycloak está configurado para retornar os roles dentro do token gerado como
    authorities:[ROLE_*****]
    * getJwtAuthenticationConverter passa esses authorities como claims */
    private JwtAuthenticationConverter getJwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthoritiesClaimName("authorities");
        converter.setAuthorityPrefix("");
        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(converter);
        return authenticationConverter;
    }
}
