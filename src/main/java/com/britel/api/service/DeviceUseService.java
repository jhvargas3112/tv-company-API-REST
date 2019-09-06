package com.britel.api.service;

import java.sql.Timestamp;
import java.util.List;

import com.britel.api.model.dto.ChannelUseDTO;
import com.britel.api.model.dto.DeviceTotalUseDTO;

/**
 * @author Jhonny Vargas.
 */

public interface DeviceUseService {
  /**
   * Calculates the consumption of the channels for all the MACS.
   */
  public List<ChannelUseDTO> calculateTheChannelsGlobalConsumption();

  /**
   * Calculates the consumption of the channels for all the MACS of the given company ID.
   * 
   * @param idCompany - a company ID.
   */
  public List<ChannelUseDTO> calculateGlobalChannelsConsumptionByCompany(int idCompany);

  public List<ChannelUseDTO> calculateChannelsGlobalCompanyConsumptionByDateRange(Integer idCompany, Timestamp startTimestamp, Timestamp endTimestamp);
  
  public List<ChannelUseDTO> calculateChannelsGlobalCompanyConsumptionByTimeSlot(Integer idCompany, String startTime, String endTime);

  public List<ChannelUseDTO> calculateChannelsGlobalConsumptionByDateRange(Timestamp startTimestamp, Timestamp endTimestamp);
  
  public List<ChannelUseDTO> calculateChannelsGlobalConsumptionByTimeSlot(String startTime, String endTime);
  
  /**
   * Calculates the current use of a specific MAC.
   * 
   * @param mac - the MAC of a specific device.
   * @return the current use of a specific MAC.
   */
  public DeviceTotalUseDTO calculateChannelsConsumptionByMac(String mac);

  /**
   *  Calculates the consumption (in seconds) of the of the given channel.
   *  
   *  @param idChannel - the logical channel number.
   */
  public ChannelUseDTO calculateChannelConsumption(Integer idChannel);

  /**
   *  Returns the consumption (in seconds) of the of the given MAC and channel.
   * 
   * @param mac - the MAC of a specific device.
   * @param idChannel - a logical channel number.
   * @return the current use of a channel for a specific MAC.
   */
  public ChannelUseDTO calculateMacChannelConsumption(String mac, Integer idChannel);
}
