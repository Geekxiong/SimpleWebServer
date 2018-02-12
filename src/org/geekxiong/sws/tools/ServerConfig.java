package org.geekxiong.sws.tools;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.geekxiong.sws.entity.LocalServerEntity;
import org.geekxiong.sws.entity.ProxyServerEntity;

/**
 * 
 * @author  作者 : Shiwei Xiong  
 * @version 创建时间 : 2018年2月12日 下午1:52:04 
 * 
 */
public class ServerConfig {
	private static ServerConfig config;
	private Map<String,Object> serversList;
	private Integer serverPort;
	
	public static ServerConfig getConfig() {
        if (config == null) {
            synchronized (ServerConfig.class) {
                if (config == null) {
                	config = new ServerConfig();
                }
            }
        }
        return config;
    }
	
	private ServerConfig(){
		String xmlPath=System.getProperty("user.dir")+"\\src\\webserver.xml";
		System.out.println(xmlPath);
		File configFile = new File(xmlPath);
		if(!configFile.exists()){
			System.err.println("Configuration file does not exist!!!");
			return;
		}
		
		SAXReader reader = new SAXReader();  
		Document document = null;
		try {
			document = reader.read(configFile);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		
        if(document == null){
        	System.err.println("The configuration file is not a XML file!!!");
        	return;
        }
        
        Element root = document.getRootElement();  

        Element port = root.element("listen");
        if(port==null){
        	serverPort = 80;
        }else {
        	serverPort = Integer.parseInt(port.getTextTrim());
        }
        serversList = new HashMap<String,Object>();
        
        List<Attribute> list = root.attributes();  
        Iterator<Element> it = root.elementIterator();  
        
        while (it.hasNext()) { 
            Element e = it.next(); 
            if(!e.getName().equals("server")){
            	continue;
            }
            String type = e.attributeValue("type");
            if(type.equals("local")){
            	String host = e.attributeValue("host");
            	String webroot = e.elementTextTrim("webroot");
            	
            	LocalServerEntity server = new LocalServerEntity();
            	server.setHost(host);
            	server.setType(type);
            	server.setWebroot(webroot);
            	serversList.put(host, server);
            }else if(type.equals("proxy")){
            	String host = e.attributeValue("host");
            	String proxy_host = e.elementTextTrim("proxy_host");
            	String proxy_port = e.elementTextTrim("proxy_port");
            	
            	ProxyServerEntity server = new ProxyServerEntity();
            	server.setHost(host);
            	server.setType(type);
            	server.setProxy_host(proxy_host);
            	server.setProxy_port(Integer.parseInt(proxy_port));
            	serversList.put(host, server);
            }
        }  

	}

	public Map<String, Object> getServersList() {
		return serversList;
	}

	public Integer getServerPort() {
		return serverPort;
	}

}
