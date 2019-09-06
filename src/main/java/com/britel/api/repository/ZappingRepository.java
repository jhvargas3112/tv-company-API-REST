package com.britel.api.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.britel.api.model.Zapping;
import com.britel.api.model.ZappingId;

/**
 * @author Jhonny Vargas.
 */

public interface ZappingRepository extends JpaRepository<Zapping, ZappingId> {
  List<Zapping> findAllByOrderByChangeChannelTimeStampAsc();
  List<Zapping> findByIdChannelOrderByChangeChannelTimeStampAsc(Integer idChannel);
  List<Zapping> findByUdidOrderByChangeChannelTimeStampAsc(String udid);

  @Query(value = "SELECT * FROM zapping z, device d, user u WHERE z.Udid = d.Udid AND d.Company = ?1 AND d.Company = u.IdUser AND z.idChannel = ?2", nativeQuery = true)
  List<Zapping> findByCompanyAndIdChannelOrderByChangeChannelTimeStampAsc(Integer idCompany, Integer idChannel);

  @Query(value = "SELECT * FROM zapping z, device d, user u WHERE z.Udid = d.Udid AND d.Company = ?1 AND d.Company = u.IdUser AND z.idChannel = ?2 AND z.changeChannelTimeStamp BETWEEN ?3 AND ?4", nativeQuery = true)
  List<Zapping> findByCompanyAndIdChannelAndChangeChannelTimestampBetweenOrderByChangeChannelTimeStampAsc(Integer idCompany, Integer idChannel, Timestamp startChangeChannelTimeStamp, Timestamp endChangeChannelTimeStamp);
  
  @Query(value = "SELECT * FROM zapping z, device d, user u WHERE z.Udid = d.Udid AND d.Company = ?1 AND d.Company = u.IdUser AND TIME_TO_SEC(time(z.changeChannelTimeStamp)) >= TIME_TO_SEC(time(?2)) AND TIME_TO_SEC(time(z.changeChannelTimeStamp)) <= TIME_TO_SEC(time(?3)) ORDER BY z.changeChannelTimeStamp ASC", nativeQuery = true)
  List<Zapping> findByCompanyAndChangeChannelTimeSlot(Integer idCompany, String startTime, String endTime);
  
  @Query(value = "SELECT * FROM zapping WHERE TIME_TO_SEC(time(changeChannelTimeStamp)) >= TIME_TO_SEC(time(?1)) AND TIME_TO_SEC(time(changeChannelTimeStamp)) <= TIME_TO_SEC(time(?2))", nativeQuery = true)
  List<Zapping> findByChangeChannelTimeSlotOrderByChangeChannelTimeStampAsc(String startTime, String endTime);
  
  List<Zapping> findByChangeChannelTimeStampBetweenOrderByChangeChannelTimeStampAsc(Timestamp startChangeChannelTimeStamp, Timestamp endChangeChannelTimeStamp);
}
