package com.britel.api.rest;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.britel.api.model.Zapping;
import com.britel.api.model.ZappingId;
import com.britel.api.service.ZappingService;

/**
 * @author Jhonny Vargas.
 */

@RestController
public class ZappingRestController {
  @Autowired
  private ZappingService zappingService;

  @RequestMapping(value = "/api/zapping", method = RequestMethod.POST)
  public ResponseEntity<Void> zapping(@RequestParam(value="mac") String mac, @RequestParam(value="channel") Integer channel) {
    Zapping zapping = new Zapping();
    ZappingId zappingId = new ZappingId();

    zappingId.setUdid(mac);
    zappingId.setIdChannel(channel);
    zappingId.setChangeChannelTimeStamp(new Timestamp(System.currentTimeMillis()));

    zapping.setZappingId(zappingId);

    zappingService.save(zapping);

    return new ResponseEntity<Void>(HttpStatus.CREATED);
  }
}