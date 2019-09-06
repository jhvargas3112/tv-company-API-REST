package com.britel.api.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;
import com.britel.api.model.Channel;
import com.britel.api.repository.ChannelRepository;
import com.britel.api.service.ElectronicProgramGuideService;

@Service
public class ElectronicProgramGuideServiceImpl implements ElectronicProgramGuideService {
  @Autowired
  private ChannelRepository ChannelRespository;

  public JSONObject getElectronicProgramGuide() {
    List<Channel> channels = ChannelRespository.findByOrderByIdChannel();

    JSONObject electronicProgramGuide = new JSONObject(); // Resultaado final: programación de todos los canales.
    electronicProgramGuide.setEscapeForwardSlashAlways(false);
    ArrayList<JSONObject> events = new ArrayList<JSONObject>(); // Lista de eventos = programación de un canal.

    ArrayList<JSONObject> channelsEPG = new ArrayList<JSONObject>();

    try {
      electronicProgramGuide.accumulate("status", "ok");
    } catch (JSONException e) {
      e.printStackTrace();
    }

    ClassLoader classLoader = getClass().getClassLoader();
    Properties appProps = new Properties();

    String resource = classLoader.getResource("config.properties").getPath();
    try {
      appProps.load(new FileInputStream(resource));
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    String filepath = appProps.getProperty("auction_winners_path_file") + "/guide.xml";
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null;

    Document document = null;

    try {
      docBuilder = docFactory.newDocumentBuilder();
    } catch (ParserConfigurationException e1) {
      e1.printStackTrace();
    }
    try {
      document = docBuilder.parse(filepath);
    } catch (SAXException | IOException e1) {
      e1.printStackTrace();
    }

    DocumentTraversal traversal = (DocumentTraversal) document;

    Node root = document.getDocumentElement();

    NodeIterator it1 = traversal.createNodeIterator(root, NodeFilter.SHOW_ELEMENT, null,
        true);
    
    JSONObject channelJSONObject = null;

    for (Channel channel : channels) {
      channelJSONObject = new JSONObject();
      try {
        channelJSONObject.accumulate("idChannel", channel.getIdChannel());
      } catch (JSONException e1) {
        e1.printStackTrace();
      }
      
      for (Node node = it1.nextNode(); node != null; node = it1.nextNode()) {
        Element element = (Element) node;
        
        // channel.getName();
        
        // element.getAttributeNode("channel").getNodeValue();
        
        if ("programme".equals(element.getTagName()) && StringUtils.equals(element.getAttribute("channel"), channel.getEpgName())) {
          JSONObject eventJSONObject = new JSONObject();
          
          try {
            String startTime = getParsedTime(element.getAttribute("start"));
            
            int duration = getDuration(startTime, getParsedTime(element.getAttribute("stop")));
            
            eventJSONObject
            .accumulate("startTime", getDate(element.getAttribute("start")) + "T" +  startTime + "Z")
            .accumulate("duration", duration);

            //FIXME: NO SÉ SI ESTA ITERACIÓN VA A FUNCIONAR.
            
            NodeList childNodes = element.getChildNodes();
            
            for (int i = 0; i < childNodes.getLength(); i++) {
              if (StringUtils.equals(childNodes.item(i).getNodeName(), "title")) {
                eventJSONObject.accumulate("description", childNodes.item(i).getTextContent());
                break;
              }
            }

          } catch (JSONException e) {
            e.printStackTrace();
          }
          
          events.add(eventJSONObject);
        }
      }
      try {
        channelJSONObject.accumulate("events", events);
      } catch (JSONException e) {
        e.printStackTrace();
      }
      
      channelsEPG.add(channelJSONObject);
    }

    try {
      electronicProgramGuide.accumulate("epg", channelsEPG);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    
    return electronicProgramGuide;
  }

  private static String getDate(String startTime){
    String date = startTime.split(" ")[0];  //20180301063000
    String yyyy = date.substring(0,4);
    String mm = date.substring(4,6);
    String dd = date.substring(6,8);      

    return yyyy+"-"+mm+"-"+dd;
  }

  private static String getParsedTime(String startTime){
    String date = startTime.split(" ")[0];  //20180301063000
    String hh = date.substring(8,10);
    String mm = date.substring(10,12);
    String ss = date.substring(12,14); 
    return hh+":"+mm+":"+ss;
  }

  //convert to seconds
  private static int getDuration(String startTime, String stopTime){
    int duration =  (Integer.parseInt(stopTime.split(":")[0])*60*60+
        Integer.parseInt(stopTime.split(":")[1])*60+
        Integer.parseInt(stopTime.split(":")[2]))
        -
        (Integer.parseInt(startTime.split(":")[0])*60*60+
            Integer.parseInt(startTime.split(":")[1])*60+
            Integer.parseInt(startTime.split(":")[2]));
    
    if (duration < 0) {
      duration += 86400;
    }

    return duration;
  }    
}
