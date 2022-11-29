package com.labcomu.edu.client;

import com.labcomu.edu.configuration.EduProperties;
import com.labcomu.edu.resource.Organization;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Validated
public class OrgGateway {
    private final String fetchOrganizationUrl;

    private final WebClient.Builder webClientBuilder;
    private Logger logger = LoggerFactory.getLogger(OrgGateway.class);

    public OrgGateway(final WebClient.Builder webClientBuilder,
            final EduProperties properties) {
        this.webClientBuilder = webClientBuilder;
        this.fetchOrganizationUrl = properties.getUrl().getFetchOrganizationDetails();
        //this.logger = LoggerFactory.getLogger(OrgGateway.class);
    }

    @CircuitBreaker(name = "missaoOne", fallbackMethod = "getOrganizationFallback")
    public Organization getOrganization(@NotNull final String url) {
        return webClientBuilder.build()
                .get()
                .uri(fetchOrganizationUrl, url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Organization.class)
                .block();
    }

    public Organization getOrganizationFallback (Exception e) {
        //System.out.println ("USANDO PRINT: VIXE, Não foi possível processsar sua informação");
        //Organization org = new Organization ();
        this.logger.error("VIXE, Não foi possível processsar sua informação!!\norgService CAIU");
        return null;
     }

    //  public Organization fallbackRateLimiter (Exception e) {
    //     //System.out.println ("USANDO PRINT: VIXE, Não foi possível processsar sua informação");
    //     this.logger.error("VIXE, Não foi possível processsar sua informação!!\nELa demorou mais de 3 segundos!\n");
    //     return null;
    //  }
}
