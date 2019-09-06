package com.britel.api.rest;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.britel.api.service.ElectronicProgramGuideService;

@RestController
public class ElectronicProgramGuideRestController {
  @Autowired
  private ElectronicProgramGuideService electronicProgramGuideService;
  
  @CrossOrigin
  @GetMapping(value = "/api/epg")
  public ResponseEntity<JSONObject> getEpg() { 
    return ResponseEntity.ok(electronicProgramGuideService.getElectronicProgramGuide());
  }
}
