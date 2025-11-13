package com.MicroservicePractice.CloudGateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.function.Consumer;

@Configuration
@EnableWebFluxSecurity
public class OAuth2WebSecurity {

    private final org.springframework.cloud.client.circuitbreaker.Customizer customizer;

    @Value("${auth0.audience}")
    private String audience;

    private final ReactiveClientRegistrationRepository clientRegistrationRepository;

    public OAuth2WebSecurity(ReactiveClientRegistrationRepository clientRegistrationRepository,
                             org.springframework.cloud.client.circuitbreaker.Customizer customizer) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.customizer = customizer;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(authorizeExchangeBuilder ->
                        authorizeExchangeBuilder.anyExchange().authenticated())
                .oauth2Login(oAuth2LoginSpec ->
                        oAuth2LoginSpec.authorizationRequestResolver(authorizeExchangeBuilder(clientRegistrationRepository)));
        return http.build();
    }

    private ServerOAuth2AuthorizationRequestResolver authorizeExchangeBuilder(
            ReactiveClientRegistrationRepository clientRegistrationRepository
    ) {
        DefaultServerOAuth2AuthorizationRequestResolver resolver
                = new DefaultServerOAuth2AuthorizationRequestResolver(clientRegistrationRepository);

        resolver.setAuthorizationRequestCustomizer(authorizationRequestCustomizer());
        return resolver;
    }

    private Consumer<OAuth2AuthorizationRequest.Builder> authorizationRequestCustomizer() {
        return customizer -> customizer
                .additionalParameters(
                        params ->
                                params.put("audience", audience));
    }
}
