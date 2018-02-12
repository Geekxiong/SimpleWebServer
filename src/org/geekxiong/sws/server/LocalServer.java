package org.geekxiong.sws.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import org.geekxiong.sws.entity.SwsHttpRequest;
import org.geekxiong.sws.entity.SwsHttpResponse;
import org.geekxiong.sws.ientity.HttpRequest;
import org.geekxiong.sws.ientity.HttpResponse;
import org.geekxiong.sws.tools.ContentUtil;

/**
 * 
 * @author  作者 : Shiwei Xiong  
 * @version 创建时间 : 2018年2月12日 下午2:27:13 
 * 
 */
public class LocalServer {
	private Socket socket;
	private HttpRequest request;
	private String webroot;
	
	public LocalServer(Socket socket,HttpRequest request,String webroot){
		this.socket = socket;
		this.request = request;
		this.webroot = webroot;
	}
	
	public void start() {
		try {
			File reqPath = new File(webroot,request.getRequestURI());
			if (reqPath.isDirectory()) {
				reqPath = new File(reqPath + "/index.html");
			}
			
			HttpResponse response = new SwsHttpResponse(socket);
			PrintWriter writer = response.getWriter();
			
			if (reqPath.exists()) {
				String successMessage = "HTTP/1.1 200 OK\r\n";
				writer.write(successMessage);
				
				String fileName = reqPath.getName();
				String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
				String contentType = ContentUtil.getContentType(prefix);
				writer.write(contentType);
				
				writer.write("\r\n");
				writer.flush();
				OutputStream os = response.getOutputStream();
				FileInputStream fis = new FileInputStream(reqPath);
				byte[] tmp = new byte[2048];
				while (fis.read(tmp) != -1) {
					os.write(tmp);
				}
				fis.close();
			} else {
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n";
				writer.write(errorMessage);
				String contentType = "Content-Type: text/html\r\n";
				writer.write(contentType);
				String content = "\r\n" + "<h1>File Not Found</h1>";
				writer.write(content);
				writer.flush();
			}
			
		} catch (IOException e) {
			try {
				socket.getOutputStream().write("HTTP/1.1 500 ERROR\r\nContent-Type: text/html\r\n\r\n<h1>SERVER ERROR!!!</h1>".getBytes());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
