package com.britel.api.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.britel.api.model.Channel;
import com.britel.api.service.ChannelService;

@RestController
public class ChannelRestController {
  @Autowired
  private ChannelService channelService;
  
  /**
   * Gets all the channels of the given media player.
   * 
   * @return
   */
  @GetMapping(value = "/api/channels")
  public ResponseEntity<List<Channel>> getChannelsByMediaPlayer(@RequestParam(name = "media_player", required = false) String mediaPlayer) {
    return ResponseEntity.ok(channelService.findByMediaPlayer(mediaPlayer));
  }
}
