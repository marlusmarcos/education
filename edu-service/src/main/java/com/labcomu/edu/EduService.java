package com.labcomu.edu;

import com.labcomu.edu.client.OrcidGateway;
import com.labcomu.edu.client.OrgGateway;
import com.labcomu.edu.resource.Organization;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Validated
@RequiredArgsConstructor
public class EduService {
    private final OrgGateway orgGateway;
    private final OrcidGateway orcidGateway;
    private Logger logger = LoggerFactory.getLogger(OrgGateway.class);
    
    public Organization getOrganization(String url) {
        Organization organization = setResearchersOrcid (url);
        if (organization != null) {
             organization.setResearchers(organization.getResearchers().stream().map(researcher -> orcidGateway.getResearcher(researcher.getOrcid())).toList());
             //setResearchersOrcid(organization);
             if (organization.getResearchers() == null) {
                return null;
             }
             return organization;
            //return setResearchersOrcid (organization);
        }
        return null;
    }

    @RateLimiter (name = "missao3", fallbackMethod = "fallbackRateLimiter")
    public Organization setResearchersOrcid (String url) {
        //organization.setResearchers(organization.getResearchers().stream().map(researcher -> orcidGateway.getResearcher(researcher.getOrcid())).toList());
        Organization organization = orgGateway.getOrganization(url);
        return organization;
    }
    public Organization fallbackRateLimiter (Exception e) {
        //System.out.println ("USANDO PRINT: VIXE, Não foi possível processsar sua informação");
        this.logger.error("VIXE, Não foi possível processsar sua informação!!\nELa demorou mais de 3 segundos!\n");
        return null;
     }
}
