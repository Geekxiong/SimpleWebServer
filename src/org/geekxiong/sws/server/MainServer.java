package org.geekxiong.sws.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 
 * @author  作者 : Shiwei Xiong  
 * @version 创建时间 : 2018年2月11日 上午3:53:32 
 * 
 */
public class MainServer {
	public static final String webroot = "G:/testwebroot";
	
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(80);
		System.err.println(new Date().toString()+"\t"+"Started!!!"+"\tport:"+server.getLocalPort());
		while (!server.isClosed()) {
			Socket socket = server.accept();
			ThreadServer newRequest = new ThreadServer(socket);
			new Thread(newRequest).start();
		}
		server.close();
	}

}
