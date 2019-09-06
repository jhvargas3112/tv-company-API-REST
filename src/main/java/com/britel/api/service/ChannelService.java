package com.britel.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.britel.api.model.Channel;
import com.britel.api.model.Organization;
import com.britel.api.model.Package;
import com.britel.api.model.VodCat;

 public interface ChannelService {

   public ArrayList<Channel> findByOrganization(Set<Package> set);
   
   public List<Channel> findByMediaPlayer(String mediaPlayer);

   public ArrayList<VodCat> findvideoByOrganization(Organization select);

   public ArrayList<VodCat> findCategoryByIdCategory(String IdCategory);
 }