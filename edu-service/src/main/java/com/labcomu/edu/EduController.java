package com.labcomu.edu;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.labcomu.edu.resource.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v1/edu")
@Validated
@RequiredArgsConstructor
public class EduController {
  private final EduService service;

  @GetMapping("organization/{url}") 
  public ResponseEntity<?> getOrganization(@NotNull @PathVariable String url)  {
    Organization org =  service.getOrganization(url);
    if (org != null) {
      return ResponseEntity.ok(org);
    }
    //return new Exception ("VIXE, Não foi possível processsar sua informação no Controller!!");
    //return new ResponseEntity<>(HttpStatus.BAD_REQUEST, reason = "Erro no orgService");
     //return  ResponseEntity.notFound().build();
    String erro = "Erro no OrgService, retorno do controler";
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
  }
}
