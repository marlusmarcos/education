package com.labcomu.edu;

import com.labcomu.edu.client.OrcidGateway;
import com.labcomu.edu.client.OrgGateway;
import com.labcomu.edu.resource.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class EduService {
    private final OrgGateway orgGateway;
    private final OrcidGateway orcidGateway;

    public Organization getOrganization(String url) {
        Organization organization = orgGateway.getOrganization(url);
        if (organization != null) {
            organization.setResearchers(organization.getResearchers().stream().map(researcher -> orcidGateway.getResearcher(researcher.getOrcid())).toList());
            return organization;
        }
        return null;
        
        
    }
}
