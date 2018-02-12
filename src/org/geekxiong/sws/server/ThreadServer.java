package org.geekxiong.sws.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import org.geekxiong.sws.entity.LocalServerEntity;
import org.geekxiong.sws.entity.ProxyServerEntity;
import org.geekxiong.sws.entity.SwsHttpRequest;
import org.geekxiong.sws.ientity.HttpRequest;
import org.geekxiong.sws.ientity.ServerEntity;
import org.geekxiong.sws.tools.ServerConfig;

/**
 * 
 * @author  作者 : Shiwei Xiong  
 * @version 创建时间 : 2018年2月11日 上午3:55:56 
 * 
 */
public class ThreadServer implements Runnable{
	private final ServerConfig config = ServerConfig.getConfig();
	private Socket socket;
	
	ThreadServer(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			HttpRequest request = new SwsHttpRequest(socket);
			System.err.println(new Date().toString()+"\t"+request.getRemoteAddr()+"\t"+request.getRequestMethod()+"\t"+request.getRequestURI());
			String host = request.getServerName();
			ServerEntity server  = (ServerEntity)config.getServersList().get(host);
			String serverType = server.getType();
			
			if(serverType.equals("local")){
				String webroot = ((LocalServerEntity)server).getWebroot();
				new LocalServer(socket,request,webroot).start();
			}else{
				String proxy_host = ((ProxyServerEntity)server).getProxy_host();
				Integer proxy_port = ((ProxyServerEntity)server).getProxy_port();
				new ProxyServer(socket,request,proxy_host,proxy_port).start();
			}
			
		} catch (Exception e) {
			try {
				socket.getOutputStream().write("HTTP/1.1 500 ERROR\r\nContent-Type: text/html\r\n\r\n<h1>ERROR!!!</h1>".getBytes());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally{
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
