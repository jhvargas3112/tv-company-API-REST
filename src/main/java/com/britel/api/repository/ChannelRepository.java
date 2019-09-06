package com.britel.api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.britel.api.model.Category;
import com.britel.api.model.Channel;

/**
 * @author Jhonny Vargas.
 */

public interface ChannelRepository extends JpaRepository<Channel, Integer> {
  List<Channel> findByOrderByIdChannel();
  
  Optional<Channel> findByIdChannel(Integer idChannel);
  
  List<Channel> findByMediaPlayer(String mediaPlayer);
  
  /* @Query(value = "SELECT distinct IdChannel, LogicalChannelNumber, Url, Name, AspectRatio, Quality FROM channel c, containpackagechannel pc, containorganizationpackage cp where c.IdChannel = pc.Channel and pc.Package = cp.Package and cp.Organization = ?1;", nativeQuery = true)
  List<Channel> findByIdOrganization(Integer organizationId); */
  
  @Query(value = "SELECT LogicalChannelNumber, Name FROM channel", nativeQuery = true)
  List<Channel> getLogicalChannelNumbersAndNames();
}
