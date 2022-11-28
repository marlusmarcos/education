package com.labcomu.orcid;

import com.labcomu.orcid.resource.Researcher;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.labcomu.faultinjection.annotation.Throw;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v1/orcid")
@Validated
@RequiredArgsConstructor
public class OrcidController {
    private final OrcidService service;

    @GetMapping("active")
    public boolean isActive() {
        return service.isActive();
    }

    
    @GetMapping("researcher/{orcid}")
    @Throw(exception=RuntimeException.class, threshold=0.5)
    public Researcher getResearcher(@NotNull @PathVariable String orcid) {
        return service.getResearcher(orcid);
    }
}
