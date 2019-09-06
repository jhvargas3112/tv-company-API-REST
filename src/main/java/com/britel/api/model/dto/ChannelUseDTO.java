package com.britel.api.model.dto;

/**
 * @author Jhonny Vargas.
 */

public class ChannelUseDTO {
  private Integer logicalChannelNumber;
  private String channelName;
  private Long timeConsumed; // In seconds.

  public ChannelUseDTO(Integer logicalChannelNumber, String channelName, Long timeConsumed) {
    super();

    this.logicalChannelNumber = logicalChannelNumber;
    this.channelName = channelName;
    this.timeConsumed = timeConsumed;
  }

  public Integer getLogicalChannelNumber() {
    return logicalChannelNumber;
  }

  public void setLogicalChannelNumber(Integer logicalChannelNumber) {
    this.logicalChannelNumber = logicalChannelNumber;
  }

  public String getChannelName() {
    return channelName;
  }

  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }

  public Long getTimeConsumed() {
    return timeConsumed;
  }

  public void setTimeConsumed(Long timeConsumed) {
    this.timeConsumed = timeConsumed;
  }
}
