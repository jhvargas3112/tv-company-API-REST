package com.britel.api.rest;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.britel.api.model.dto.ChannelUseDTO;
import com.britel.api.model.dto.DeviceTotalUseDTO;
import com.britel.api.service.DeviceUseService;

/**
 * @author Jhonny Vargas.
 */

@RestController
public class DeviceUseRestController {
  @Autowired
  private DeviceUseService deviceUseService;

  /**
   * Returns the consumption of the channels for the given MAC. 
   * 
   * @param MAC
   * @return the consumption of the channels.
   */
  @CrossOrigin
  @GetMapping(value = "/api/device/channels_consumption/{mac}")
  public ResponseEntity<DeviceTotalUseDTO> channelsConsumption(@PathVariable (value = "mac", required = true) String mac) {	
    DeviceTotalUseDTO deviceTotalUseDTO = deviceUseService.calculateChannelsConsumptionByMac(mac);

    return ResponseEntity.ok(deviceTotalUseDTO);
  }

  /**
   * Returns the consumption of the channels for all the MACS.
   * 
   * @return the global consumption for all the MACS.
   */
  @CrossOrigin
  @GetMapping(value = "/api/device/global_consumption")
  public ResponseEntity<List<ChannelUseDTO>> channelsGlobalConsumption() {
    return ResponseEntity.ok(deviceUseService.calculateTheChannelsGlobalConsumption());
  }

  @CrossOrigin
  @GetMapping(value = "/api/device/global_consumption_by_date_range/{start_date}/{end_date}")
  public ResponseEntity<List<ChannelUseDTO>> channelsGlobalConsumptionByDateRange(@PathVariable (value = "start_date", required = true) String startTimestamp,
      @PathVariable (value = "end_date", required = true) String endTimestamp) {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    ResponseEntity<List<ChannelUseDTO>> response = null;

    try {
      response = ResponseEntity.ok(
          deviceUseService.calculateChannelsGlobalConsumptionByDateRange(
              new Timestamp(dateFormat.parse(startTimestamp).getTime()),
              new Timestamp(dateFormat.parse(endTimestamp).getTime())
              )
          );
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return response;
  }
  
  @CrossOrigin
  @GetMapping(value = "/api/device/global_consumption_by_time_slot/{start_time}/{end_time}") // AQUÍ
  public ResponseEntity<List<ChannelUseDTO>> channelsGlobalConsumptionByTimeSlot(@PathVariable (value = "start_time", required = true) String startTime,
      @PathVariable (value = "end_time", required = true) String endTime) {

    return ResponseEntity.ok(
        deviceUseService.calculateChannelsGlobalConsumptionByTimeSlot(startTime, endTime));
  }

  /**
   * Returns the consumption of the channels for all the MACS of the given company ID.
   * 
   * @param idCompany
   * @return the consumption of the channels for all the MACS of the given company ID.
   */
  //TODO: Adaptarlo a un método para compañías tipo covent , es decir, con JWT; pero antes pensar si eso es necesario.
  @CrossOrigin
  @GetMapping(value = "/api/device/global_company_consumption/{id_company}")
  public ResponseEntity<List<ChannelUseDTO>> channelsGlobalConsumptionByCompany(@PathVariable(value = "id_company", required = true) Integer idCompany) {
    return ResponseEntity.ok(deviceUseService.calculateGlobalChannelsConsumptionByCompany(idCompany));
  }

  @CrossOrigin
  @GetMapping(value = "/api/device/global_company_consumption_by_date_range/{id_company}/{start_date}/{end_date}")
  public ResponseEntity<List<ChannelUseDTO>> channelsGlobalConsumptionByDateRange(@PathVariable(value = "id_company", required = true) Integer idCompany,
      @PathVariable (value = "start_date", required = true) String startTimestamp,
      @PathVariable (value = "end_date", required = true) String endTimestamp) {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    ResponseEntity<List<ChannelUseDTO>> response = null;

    try {
      response = ResponseEntity.ok(
          deviceUseService.calculateChannelsGlobalCompanyConsumptionByDateRange(
              idCompany,
              new Timestamp(dateFormat.parse(startTimestamp).getTime()),
              new Timestamp(dateFormat.parse(endTimestamp).getTime())
              )
          );
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return response;
  }
  
  @CrossOrigin
  @GetMapping(value = "/api/device/global_company_consumption_by_time_slot/{id_company}/{start_time}/{end_time}")
  public ResponseEntity<List<ChannelUseDTO>> channelsGlobalCompanyConsumptionByTimeSlot(@PathVariable(value = "id_company", required = true) Integer idCompany,
      @PathVariable (value = "start_time", required = true) String startTime,
      @PathVariable (value = "end_time", required = true) String endTime) {

    return ResponseEntity.ok(
        deviceUseService.calculateChannelsGlobalCompanyConsumptionByTimeSlot(idCompany, startTime, endTime));
  }
}
