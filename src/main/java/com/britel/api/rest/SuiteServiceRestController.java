package com.britel.api.rest;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.britel.api.model.Organization;
import com.britel.api.service.ChannelService;
import com.britel.api.service.OrganizationService;

@RestController
public class SuiteServiceRestController {

  @Autowired
  ChannelService sercanales;
  @Autowired
  OrganizationService organizationservice;
  /**
   * La idea seria crear un Token con la organización y el dia actual para que no se pueda entrar de forma sencilla. 
   * el emisor tendra que comprimir en sha256 (one way hash) - y nosotros compararemos el resultado con los posibles resultados. 
   * @param Token
   * @return la idea es que retorne todos los canales que tengan esa organización.
   */
  @SuppressWarnings("unchecked")
  @GetMapping(value = "/api/canalesL/{token}")
  public ResponseEntity<Object> channelsGet(
      @PathVariable(value = "token", required = true) String token) {
    Integer idOrganization = 0;
    //comparar el Token con las posibles organizaciones 
    //llamada para obtener todas las organizaciones 
    //--
    // no lo pilla organizationservice.findOrganizations()

    List<Organization> orgsChan = organizationservice.findOrganizations();
    //recorrer org por org y ver has es igual   
    Iterator<Organization> it = orgsChan.iterator();
    Organization select = null ;
    // mientras al iterador queda proximo juego
    while(it.hasNext()){
      Organization orgi = it.next();
      if(compararorg(orgi.getIdOrganization(),token)!=0){
        idOrganization=orgi.getIdOrganization();
        select = orgi;
      }
    }
    //Si existe retornar todos los canales de esa organizacion 
    //llamada para obtener todos los canales de una organización
    if(idOrganization!=0) {
      return ResponseEntity.ok(sercanales.findByOrganization(select.getPackages()));
    }else {
      return ResponseEntity.noContent().build();
    }   
    //Si no existe retornar error 
  }
  //http://91.126.138.137/api/channels?organizationId=14925&action=getChannels&userId&orderBy=logicalChannelNumber

  /**
   * 
   * @param idorgin
   * @param token
   * @return 0 si no es , el valor si es 
   */
  public static int compararorg(Integer idorgin, String token) {
    String idorg = idorgin.toString(); 
    Calendar fecha = Calendar.getInstance();
    int dia = fecha.get(Calendar.DAY_OF_MONTH);
    String depurar= idorg+dia;
    if(sha256(depurar).equals(token)) {
      return idorgin;
    }else {
      return 0;   
    }

  }

  /**
   * 
   * @param base
   * @return sha256 digerido
   */
  public static String sha256(String base) {
    try{
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(base.getBytes("UTF-8"));
      StringBuffer hexString = new StringBuffer();

      for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if(hex.length() == 1) hexString.append('0');
        hexString.append(hex);
      }

      return hexString.toString();
    } catch(Exception ex){
      throw new RuntimeException(ex);
    }
  }
  //http://91.126.138.137/api/channels?organizationId=14925&action=getChannels&userId&orderBy=logicalChannelNumber

  /**
   * Mismo concepto con el Token comparar las posibles opciones
   * una vez conocido todos los retornar todos los videos del VoD
   * esto es para evitar tener que realizar mil llamadas por cada sección
   * @param action
   * @param organizationId
   * @param token
   * @param language
   * @param groupTypeId
   * @return
   * @throws Exception
   */
  @GetMapping(value = "/api/vodL/{token}")
  public ResponseEntity<Object> archiveVODGet(
      @PathVariable(value = "token", required = true) String token) throws Exception {
    //realizar la misma prueba del token
    Integer idOrganization = 0;
    //comparar el Token con las posibles organizaciones 
    //llamada para obtener todas las organizaciones 
    //--
    List<Organization> orgsChan = organizationservice.findOrganizations();
    //recorrer org por org y ver has es igual   
    Iterator<Organization> it = orgsChan.iterator();
    // mientras al iterador queda proximo juego
    Organization select = null ;
    // mientras al iterador queda proximo juego
    while(it.hasNext()){
      Organization orgi = it.next();
      if(compararorg(orgi.getIdOrganization(),token)!=0){
        idOrganization=orgi.getIdOrganization();
        select = orgi;
      }
    }
    //habria que mirar idorganizacion para hacer la llamada a hotflix
    if(idOrganization!=0) {
      if(idOrganization!=20593) {
        return ResponseEntity.ok(sercanales.findvideoByOrganization(select));
      }else {
        return gethotflix();
      }   
    }else {
      return ResponseEntity.noContent().build();
    }

  } 

  @SuppressWarnings("unchecked")
  private static ResponseEntity<Object> gethotflix(){
    //http://apibritel.placertv.com/api/archive?action=getGroups&pid="+hash+"&deviceId=B4200044F0A0
    String hash;
    String token;
    Calendar fecha = Calendar.getInstance();
    int dia = fecha.get(Calendar.DAY_OF_MONTH);
    int mes = fecha.get(Calendar.MONTH)+1;
    String depurar;
    if(mes<10 && dia<10) {
      depurar= "0"+dia+"-0"+mes+"M347#890uF";
    }else if(mes<10) {
      depurar= dia+"-0"+mes+"M347#890uF";
    }else if(dia <10) {
      depurar= "0"+dia+"-"+mes+"M347#890uF";
    }else {
      depurar= dia+"-"+mes+"M347#890uF";
    }
    token = sha256(depurar);
    final String uri = "http://apibritel.placertv.com/api/archive?action=getGroups&pid="+token+"&deviceId=B4200044F0A0";
    //uri preparada , realizar llamada 
    RestTemplate restTemplate = new RestTemplate();
    Object result = restTemplate.getForObject(uri, Object.class);
    return ResponseEntity.accepted().body(result);
  }

  @GetMapping(value = "/api/vodL/cat/{token}/{cat}")
  public ResponseEntity<Object> archiveVODhotGet(
      @PathVariable(value = "token", required = true) String token,
      @PathVariable(value = "cat", required = true) String cat
      ) throws Exception {
    //realizar la misma prueba del token
    Integer idOrganization = 0;
    //comparar el Token con las posibles organizaciones 
    //llamada para obtener todas las organizaciones 
    //--
    List<Organization> orgsChan = organizationservice.findOrganizations();
    //recorrer org por org y ver has es igual   
    Iterator<Organization> it = orgsChan.iterator();
    // mientras al iterador queda proximo juego
    Organization select = null ;
    // mientras al iterador queda proximo juego
    while(it.hasNext()){
      Organization orgi = it.next();
      if(compararorg(orgi.getIdOrganization(),token)!=0){
        idOrganization=orgi.getIdOrganization();
        select = orgi;
      }
    }
    //habria que mirar idorganizacion para hacer la llamada a hotflix
    if(idOrganization!=0) {     
      if(idOrganization!=20593) {
        return ResponseEntity.ok(sercanales.findCategoryByIdCategory(cat));
      }else {
        return gethotflixcat(cat);
      }               
    }else {
      return ResponseEntity.noContent().build();
    }           
  }

  @SuppressWarnings("unchecked")
  private static ResponseEntity<Object> gethotflixcat(String categoria){
    //http://apibritel.placertv.com/api/archive?action=getGroups&pid="+hash+"&deviceId=B4200044F0A0
    String hash;
    String token;
    Calendar fecha = Calendar.getInstance();
    int dia = fecha.get(Calendar.DAY_OF_MONTH);
    int mes = fecha.get(Calendar.MONTH)+1;
    String depurar;
    if(mes<10 && dia<10) {
      depurar= "0"+dia+"-0"+mes+"M347#890uF";
    }else if(mes<10) {
      depurar= dia+"-0"+mes+"M347#890uF";
    }else if(dia <10) {
      depurar= "0"+dia+"-"+mes+"M347#890uF";
    }else {
      depurar= dia+"-"+mes+"M347#890uF";
    }
    token = sha256(depurar);
    final String uri = "http://apibritel.placertv.com/api/publishing?action=getAssets&pid="+token+"&deviceId=B4200044F0A0&cat="+categoria+"&from=0&to=2000";
    //uri preparada , realizar llamada 
    RestTemplate restTemplate = new RestTemplate();
    Object result = restTemplate.getForObject(uri, Object.class);
    return ResponseEntity.accepted().body(result);
  }
  /**
   * La idea es pasar solo el organization id para así poder traer solo esa sección de videos. 
   * @param action
   * @param organizationId
   * @param token
   * @param language
   * @param version
   * @param groupTypeId
   * @return
   * @throws Exception
   */
  @GetMapping(value = "/api/publishing")
  public ResponseEntity<Object> publishingVideosGet(
      @RequestParam(value = "token", required = true) String token) throws Exception {
    return ResponseEntity.ok(token);    
  } 

}