package org.geekxiong.sws.entity;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.geekxiong.sws.ientity.HttpResponse;

/**
 * 
 * @author  作者 : Shiwei Xiong  
 * @version 创建时间 : 2018年2月11日 上午3:38:40 
 * 
 */
public class SwsHttpResponse implements HttpResponse{
	private Socket requestSocket;
	
	public SwsHttpResponse(Socket socket){
		this.requestSocket = socket;
	}
	
	@Override
	public String getCharacterEncoding() {
		return null;
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return requestSocket.getOutputStream();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		OutputStream out = requestSocket.getOutputStream();
		PrintWriter writer = new PrintWriter(out);
		return writer;
	}

	@Override
	public void setContentLength(int length) {
		try {
			PrintWriter writer = getWriter();
			writer.print("Content-Length: "+length+"\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void setContentType(String type) {
		try {
			PrintWriter writer = getWriter();
			writer.print("Content-Type: "+type+"\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
