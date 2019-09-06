package com.britel.api.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.britel.api.model.Cat;
import com.britel.api.model.Category;
import com.britel.api.model.Channel;
import com.britel.api.model.Organization;
import com.britel.api.model.Package;
import com.britel.api.model.Video;
import com.britel.api.model.Vod;
import com.britel.api.model.VodCat;
import com.britel.api.repository.CategoryRepository;
import com.britel.api.repository.ChannelRepository;
import com.britel.api.repository.PackageRepository;
import com.britel.api.repository.VodRepository;
import com.britel.api.service.ChannelService;

@Service
public class ChannelServiceImpl implements ChannelService {

  @Autowired
  private PackageRepository packageRepository;

  @Autowired
  private CategoryRepository categoryRepo;

  @Autowired
  private VodRepository vodRepo;
  
  @Autowired
  private ChannelRepository channelRepository;

  @Override
  public ArrayList<Channel> findByOrganization(Set<Package> idOrganization) {
    // TODO Auto-generated method stub
    ArrayList<Channel> channels = getChannels(idOrganization);
    return channels;
  }

  /**
   * Obtener canales por organización 
   */
  private ArrayList<Channel> getChannels(Set<Package> set) {
    //TODO hay que mirar las organizaciones que paquetes tienen
    Iterator<Package> it = set.iterator();
    Organization select = null ;
    // mientras al iterador queda proximo juego
    ArrayList<Channel> channels = new ArrayList<Channel>();
    if(set.size()>1) {
      int bandera=0;
      //caso Youtube antes que nntv
      while(it.hasNext()){
        Package primero = it.next();
        if(primero.getName().equals("Youtube")) {
          while(it.hasNext()){
            List<Channel> nnChannels =  packageRepository.findById(it.next().getIdPackage()).get().getChannels();
            //List<Channel> nnChannels =  channelrepo.findByIdOrganization(Integer.parseInt(organizationID));
            channels.addAll(nnChannels);
          }
          List<Channel> nnChannels =  packageRepository.findById(primero.getIdPackage()).get().getChannels();
          //List<Channel> nnChannels =  channelrepo.findByIdOrganization(Integer.parseInt(organizationID));
          channels.addAll(nnChannels);
        }else {
          List<Channel> nnChannels =  packageRepository.findById(primero.getIdPackage()).get().getChannels();
          channels.addAll(nnChannels);
          while(it.hasNext()){
            List<Channel> nnChannels2 =  packageRepository.findById(it.next().getIdPackage()).get().getChannels();
            //List<Channel> nnChannels =  channelrepo.findByIdOrganization(Integer.parseInt(organizationID));
            channels.addAll(nnChannels2);
          }
        }   
      }
    }else {
      while(it.hasNext()){
        List<Channel> nnChannels =  packageRepository.findById(it.next().getIdPackage()).get().getChannels();
        //List<Channel> nnChannels =  channelrepo.findByIdOrganization(Integer.parseInt(organizationID));
        channels.addAll(nnChannels);
      }
    }


    return channels;
  }


  @Override
  public ArrayList<VodCat> findvideoByOrganization(Organization idOrganization) {
    // TODO Auto-generated method stub
    ArrayList<VodCat> result = new ArrayList<VodCat>();
    //Aqui ya tendria las Categorias de mi organización 
    //obtenemos la category vod realcionada con esa organización
    Vod vodinfo = vodRepo.findByOrganization(idOrganization.getIdOrganization()) ;

    //con vodinfo llamamos a las categorias que tengan ese padre 
    List<Category> padresVod = categoryRepo.findByIdCategoryFather(vodinfo.getCategory()); 


    Iterator<Category> it = padresVod.iterator();
    while(it.hasNext()){
      VodCat cat1= new VodCat(null, null);
      // getGroupItemChild(int fatherId); me retorna las categorias del padre seleccionado 
      Category father =it.next();
      if(father.getVisible()>0) {
        cat1.setPadre(father);
        List<Category> hijosp = categoryRepo.findByIdCategoryFather(cat1.getPadre().getIdCategory());
        //con esto ya tendriamos los hijos 
        //cat1.setNombres((ArrayList<Category>) hijosp);
        //ahora añadir los videos por categoria 
        Iterator<Category> it2 = hijosp.iterator();
        //hijos p tienen los videos ya puestos 

        ArrayList<Cat> hijosl = new ArrayList<Cat>();
        while(it2.hasNext()){
          // getListVideosAssets(String groupItemId); me retorna todos los videos de una organización
          Category hijoC = it2.next();
          //Descartar los que no sean visibles 
          if(hijoC.getVisible()>0) {
            //comprobación tema dobles hijos                
            List<Category> hijosdehijos = categoryRepo.findByIdCategoryFather(hijoC.getIdCategory());
            if(hijosdehijos.isEmpty()) {
              //caso sin hijos
              Set<Video> videos1 = hijoC.getVideos();
              //añadimos la lista de videos
              Cat hijo = new Cat(hijoC);
              hijo.setPadre(hijoC);
              //hijo.setVideos(videos1);
              hijosl.add(hijo);
            }else {
              //caso hijos de hijos 
              Iterator<Category> it3 = hijosdehijos.iterator();
              ArrayList<Category> sons = new ArrayList<Category>();
              while(it3.hasNext()){
                Category hijodehijo = it3.next();
                if(hijodehijo.getVisible()==1) {
                  sons.add(hijodehijo);   
                }
              }
              Cat hijo = new Cat(hijoC,sons);
              hijo.setPadre(hijoC);
              hijo.setHijos(sons);
              //es una variante de hijo que contiene las categorias del padre
              hijosl.add(hijo);
            }   
          }               
        }
        //cat1.setVideos(videos);
        cat1.setHijos(hijosl);
        result.add(cat1);
      }

    }

    //lista de vodCat
    return result;
  }

  @Override
  public ArrayList<VodCat> findCategoryByIdCategory(String IdCategory) {
    // TODO Auto-generated method stub
    ArrayList<VodCat> result = new ArrayList<VodCat>();
    //Aqui ya tendria las Categorias de mi organización 
    //obtenemos la category vod realcionada con esa organización
    int id = Integer.parseInt(IdCategory);
    List<Category> padrereal = categoryRepo.findByIdCategory(id) ;
    VodCat cat1= new VodCat(null, null);
    // getGroupItemChild(int fatherId); me retorna las categorias del padre seleccionado 
    Category father =padrereal.get(0);
    if(father.getVisible()>0) {
      cat1.setPadre(father);
      List<Category> hijosp = categoryRepo.findByIdCategoryFather(cat1.getPadre().getIdCategory());
      //con esto ya tendriamos los hijos 
      //cat1.setNombres((ArrayList<Category>) hijosp);
      //ahora añadir los videos por categoria 
      Iterator<Category> it2 = hijosp.iterator();
      //hijos p tienen los videos ya puestos 

      ArrayList<Cat> hijosl = new ArrayList<Cat>();
      while(it2.hasNext()){
        // getListVideosAssets(String groupItemId); me retorna todos los videos de una organización
        Category hijoC = it2.next();
        //Descartar los que no sean visibles 
        if(hijoC.getVisible()>0) {
          //comprobación tema dobles hijos                
          List<Category> hijosdehijos = categoryRepo.findByIdCategoryFather(hijoC.getIdCategory());
          if(hijosdehijos.isEmpty()) {
            //caso sin hijos
            Set<Video> videos1 = hijoC.getVideos();
            //añadimos la lista de videos
            Cat hijo = new Cat(hijoC);
            hijo.setPadre(hijoC);
            //hijo.setVideos(videos1);
            hijosl.add(hijo);
          }else {
            //caso hijos de hijos 
            Iterator<Category> it3 = hijosdehijos.iterator();
            ArrayList<Category> sons = new ArrayList<Category>();
            while(it3.hasNext()){
              Category hijodehijo = it3.next();
              if(hijodehijo.getVisible()==1) {
                sons.add(hijodehijo);   
              }
            }
            Cat hijo = new Cat(hijoC,sons);
            hijo.setPadre(hijoC);
            hijo.setHijos(sons);
            //es una variante de hijo que contiene las categorias del padre
            hijosl.add(hijo);
          }   
        }               
      }
      //cat1.setVideos(videos);
      cat1.setHijos(hijosl);
      result.add(cat1);
    }

    //lista de vodCat
    return result;
  }

  @Override
  public List<Channel> findByMediaPlayer(String mediaPlayer) {
    return channelRepository.findByMediaPlayer(mediaPlayer);
  }
}