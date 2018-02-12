package org.geekxiong.sws.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import org.geekxiong.sws.ientity.HttpRequest;

/**
 * 
 * @author 作者 : Shiwei Xiong
 * @version 创建时间 : 2018年2月12日 下午2:27:27
 * 
 */
public class ProxyServer {
	private Socket socket;
	private HttpRequest request;
	private String proxy_host;
	private Integer proxy_port;

	public ProxyServer(Socket socket, HttpRequest request, String proxy_host, Integer proxy_port) {
		this.socket = socket;
		this.request = request;
		this.proxy_host = proxy_host;
		this.proxy_port = proxy_port;
	}

	public void start() throws Exception {
		System.out.println(proxy_host+" "+proxy_port);
		int cas=0;
		Socket proxySocket = new Socket(InetAddress.getByName(proxy_host), proxy_port);

		// 转发请求
		OutputStream out = proxySocket.getOutputStream();
		PrintWriter pw = new PrintWriter(out);

		InputStream input = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(input);
		BufferedReader br = new BufferedReader(isr);
		String s = null;
		while ((s = br.readLine()) != null) {
			if (s.indexOf("Host") == 0) {
				s = "Host: " + proxy_host;
			}

			pw.print(s + "\r\n");
			System.out.println(s);
			if (s.length() == 0) {
				break;
			}
		}
		pw.flush();

		System.out.println("************************************");

		// 转发响应
		OutputStream out2 = socket.getOutputStream();
		
		proxySocket.setSoTimeout(500);
		InputStream input2 = proxySocket.getInputStream();
		try{
			byte[] buffer = new byte[1024];
			int len; 
			while ((len = input2.read(buffer)) != -1) {
				out2.write(buffer,0,len);
			}
		}catch(Exception e){
			out2.flush();
		}
		
		System.out.println("case #"+(cas++) +" done!");
		proxySocket.close();
	}
}
