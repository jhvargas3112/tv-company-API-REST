package com.britel.api.model.dto;

import java.util.ArrayList;

/**
 * @author Jhonny Vargas.
 */

public class DeviceTotalUseDTO {
  private ArrayList<ChannelUseDTO> chanelUseList;
  private Long totalUse;

  public DeviceTotalUseDTO(ArrayList<ChannelUseDTO> chanelUseList, Long totalUse) {
    super();

    this.chanelUseList = chanelUseList;
    this.totalUse = totalUse;
  }

  public ArrayList<ChannelUseDTO> getChanelUseList() {
    return chanelUseList;
  }

  public void setChanelUseList(ArrayList<ChannelUseDTO> chanelUseList) {
    this.chanelUseList = chanelUseList;
  }

  public Long getTotalUse() {
    return totalUse;
  }

  public void setTotalUse(Long totalUse) {
    this.totalUse = totalUse;
  }
}