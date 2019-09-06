package com.britel.api.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.britel.api.model.Channel;
import com.britel.api.model.Zapping;
import com.britel.api.model.dto.ChannelUseDTO;
import com.britel.api.model.dto.DeviceTotalUseDTO;
import com.britel.api.repository.ChannelRepository;
import com.britel.api.repository.PackageRepository;
import com.britel.api.repository.ZappingRepository;
import com.britel.api.service.DeviceUseService;

/**
 * @author Jhonny Vargas.
 */

@Service
public class DeviceUseServiceImpl implements DeviceUseService {
  @Autowired
  private PackageRepository packageRepository;
  @Autowired
  private ZappingRepository zappingRepository;
  @Autowired
  private ChannelRepository channelRepository;

  /**
   * O(n*m) time complexity.
   * 
   * Calculates the consumption of the channels for all the MACS.
   */
  public List<ChannelUseDTO> calculateTheChannelsGlobalConsumption() {
    ArrayList<Channel> channels = getChannels();

    ArrayList<ChannelUseDTO> globalConsumption = new ArrayList<ChannelUseDTO>();

    for (Channel c : channels) {
      globalConsumption.add(calculateChannelConsumption(c.getIdChannel()));
    }

    return globalConsumption;
  }

  /**
   * O(n*m) time complexity.
   * 
   * Calculates the consumption of the channels for all the MACS of the given company ID.
   * 
   * @param idCompany - a company ID.
   */
  @Override
  public List<ChannelUseDTO> calculateGlobalChannelsConsumptionByCompany(int idCompany) {
    ArrayList<Channel> channels = getChannels();

    ArrayList<ChannelUseDTO> globalCompanyConsumption = new ArrayList<ChannelUseDTO>();

    for (Channel c : channels) {
      globalCompanyConsumption.add(calculateCompanyChannelConsumption(idCompany, c.getIdChannel()));
    }

    return globalCompanyConsumption;
  }

  /**
   * 
   * 
   */
  @Override
  public List<ChannelUseDTO> calculateChannelsGlobalCompanyConsumptionByDateRange(Integer idCompany, Timestamp startTimestamp, Timestamp endTimestamp) {
    ArrayList<Channel> channels = getChannels();
    
    //List<Zapping> zappingRecords = zappingRepository.findByCompanyAndIdChannelAndChangeChannelTimestampBetweenOrderByChangeChannelTimeStampAsc(idCompany, idChannel, new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()));

    // return calculateChannelConsumption(idChannel, zappingRecords);

    ArrayList<ChannelUseDTO> globalCompanyConsumptionByDateRange = new ArrayList<ChannelUseDTO>();

    for (Channel c : channels) {
      // globalCompanyConsumptionByDateRange.add(calculateChannelConsumption(idCompany, c.getIdChannel(), startTimestamp, endTimestamp));
    }

    return globalCompanyConsumptionByDateRange;
  }
  
  public List<ChannelUseDTO> calculateChannelsGlobalCompanyConsumptionByTimeSlot(Integer idCompany, String startTime, String endTime) {
    ArrayList<Channel> channels = getChannels();
    
    List<Zapping> zappingRecords = zappingRepository.findByCompanyAndChangeChannelTimeSlot(idCompany, startTime, endTime);

    ArrayList<ChannelUseDTO> globalCompanyConsumptionByTimeSlot = new ArrayList<ChannelUseDTO>();
    
    for (Channel c : channels) {
      globalCompanyConsumptionByTimeSlot.add(calculateChannelConsumption(c.getIdChannel(), zappingRecords));
    }

    return globalCompanyConsumptionByTimeSlot;
  }

  @Override
  public List<ChannelUseDTO> calculateChannelsGlobalConsumptionByDateRange(Timestamp startTimestamp, Timestamp endTimestamp) {
    ArrayList<Channel> channels = getChannels();
    
    List<Zapping> zappingRecords = zappingRepository.findByChangeChannelTimeStampBetweenOrderByChangeChannelTimeStampAsc(new Timestamp(startTimestamp.getTime()), new Timestamp(endTimestamp.getTime()));

    ArrayList<ChannelUseDTO> globalConsumptionByDateRange = new ArrayList<ChannelUseDTO>();

    for (Channel c : channels) {
      globalConsumptionByDateRange.add(calculateChannelConsumption(c.getIdChannel(), zappingRecords));
    }

    return globalConsumptionByDateRange;
  }
  
  public List<ChannelUseDTO> calculateChannelsGlobalConsumptionByTimeSlot(String startTime, String endTime) { //AQUÍ
    ArrayList<Channel> channels = getChannels();
    
    List<Zapping> zappingRecords = zappingRepository.findByChangeChannelTimeSlotOrderByChangeChannelTimeStampAsc(startTime, endTime);

    ArrayList<ChannelUseDTO> globalConsumptionByTimeSlot = new ArrayList<ChannelUseDTO>();
    
    for (Channel c : channels) {
      globalConsumptionByTimeSlot.add(calculateChannelConsumption(c.getIdChannel(), zappingRecords));
    }

    return globalConsumptionByTimeSlot;
  }

  /**
   * O(n*m) time complexity.
   * 
   *  Calculates the current use of a specific MAC.
   *  
   *  @param mac - the MAC of a specific device.
   */
  public DeviceTotalUseDTO calculateChannelsConsumptionByMac(String mac) {
    ArrayList<Channel> channels = getChannels();

    ArrayList<ChannelUseDTO> chanelUseList = new ArrayList<ChannelUseDTO>();

    long totalUse = 0;

    for (Channel c : channels) {
      int logicalChannelNumber = c.getIdChannel();

      ChannelUseDTO channelUseDTO = calculateMacChannelConsumption(mac, logicalChannelNumber);

      chanelUseList.add(channelUseDTO);
      totalUse += channelUseDTO.getTimeConsumed();
    }

    return new DeviceTotalUseDTO(chanelUseList, totalUse);
  }

  /**
   * O(m) time complexity.
   * 
   *  Calculates the consumption (in seconds) of the of the given channel.
   *  
   *  @param idChannel - a logical channel number.
   */
  public ChannelUseDTO calculateChannelConsumption(Integer idChannel) {
    List<Zapping> zappingRecords = zappingRepository.findByIdChannelOrderByChangeChannelTimeStampAsc(idChannel);

    return calculateChannelConsumption(idChannel, zappingRecords);
  }

  /**
   * O(m) time complexity.
   * 
   *  Calculates the consumption (in seconds) of the of the given company ID and channel.
   *  
   *  @param idCompany - a company ID.
   *  @param idChannel - a logical channel number.
   */
  public ChannelUseDTO calculateCompanyChannelConsumption(Integer idCompany, Integer idChannel) {
    List<Zapping> zappingRecords = zappingRepository.findByCompanyAndIdChannelOrderByChangeChannelTimeStampAsc(idCompany, idChannel);

    return calculateChannelConsumption(idChannel, zappingRecords);
  }

  public ChannelUseDTO calculateChannelConsumptionByCompanyAndDateRange(Integer idCompany, Integer idChannel, Date startDate, Date endDate) {
    List<Zapping> zappingRecords = zappingRepository.findByCompanyAndIdChannelAndChangeChannelTimestampBetweenOrderByChangeChannelTimeStampAsc(idCompany, idChannel, new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()));

    return calculateChannelConsumption(idChannel, zappingRecords);
  }
  
  /**
   * O(m) time complexity.
   * 
   *  Calculates the consumption (in seconds) of the of the given MAC and channel.
   *  
   *  @param mac - the MAC of a specific device.
   *  @param idChannel - a logical channel number.
   */
  public ChannelUseDTO calculateMacChannelConsumption(String mac, Integer idChannel) {
    List<Zapping> zappingRecords = zappingRepository.findByUdidOrderByChangeChannelTimeStampAsc(mac);

    return calculateChannelConsumption(idChannel, zappingRecords);
  }

  /**
   * Gets all the channels.
   */
  private ArrayList<Channel> getChannels() {
    List<Channel> nnChannels = packageRepository.findById(1).get().getChannels();
    List<Channel> youtubeChannels = packageRepository.findById(3).get().getChannels();

    ArrayList<Channel> channels = new ArrayList<Channel>();
    channels.addAll(nnChannels);
    channels.addAll(youtubeChannels);

    return channels;
  }

  /**
   * O(m) time complexity.
   * 
   * Calculates the consumption of the channels for the given channel ID and the zapping list. 
   * 
   * @param idChannel - a logical channel number.
   * @param zappingRecords - a zapping list.
   */
  private ChannelUseDTO calculateChannelConsumption(int idChannel, List<Zapping> zappingRecords) {
    long timeConsumed = 0;
    long usedTime = 0;

    String channelName = channelRepository.findById(idChannel).get().getName();

    for (int i = 0; i < zappingRecords.size() - 1; i++) {
      Zapping z = zappingRecords.get(i);

      usedTime = DateUtils.getFragmentInSeconds(zappingRecords.get(i + 1).getChangeChannelTimeStamp(), Calendar.DAY_OF_YEAR) -
          DateUtils.getFragmentInSeconds(z.getChangeChannelTimeStamp(), Calendar.DAY_OF_YEAR);

      if (usedTime < 0) {
        usedTime += 86400;
      }

      /*
       * Compensar tiempo para casos en los que por ejemplo se apaga el decodificador y
       * no se vuelve a encender hasta pasado un día, por ejemplo.
       */
      if (z.getIdChannel().intValue() == idChannel) {
        if (usedTime > 630) {
          timeConsumed += 630;
        } else {
          timeConsumed += usedTime;
        }
      }
    }

    return new ChannelUseDTO(idChannel, channelName, timeConsumed);
  }
}