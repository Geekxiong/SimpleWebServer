package org.geekxiong.sws.tools;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.geekxiong.sws.ientity.ServerEntity;

/**
 * 
 * @author  作者 : Shiwei Xiong  
 * @version 创建时间 : 2018年2月12日 下午1:15:34 
 * 
 */
public class DOMUtil {

	public static void main(String[] args) throws Exception {
		ServerConfig c = ServerConfig.getConfig();
		System.out.println(c.getServerPort());
		Map m = c.getServersList();
		Set s = m.keySet();
		Iterator<String> i = s.iterator();
		while(i.hasNext()){
			String k = i.next();
			System.out.println(k);
			ServerEntity server = (ServerEntity)m.get(k);
			System.out.println(server.getHost()+" "+server.getType());
		}
		
		
	}

}
