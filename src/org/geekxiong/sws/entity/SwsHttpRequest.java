package org.geekxiong.sws.entity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.geekxiong.sws.ientity.HttpRequest;

/**
 * 
 * @author 作者 : Shiwei Xiong
 * @version 创建时间 : 2018年2月11日 上午1:59:28
 * 
 */
public class SwsHttpRequest implements HttpRequest {
	private Socket requestSocket;
	private Map<String, Object> attributes = new HashMap<String, Object>();
	private String firstHead[];
	private ByteArrayOutputStream socketStream;

	
	public SwsHttpRequest(Socket socket) throws IOException {
		this.requestSocket = socket;
		
		//备份现有inputstream
		InputStream input0 =  requestSocket.getInputStream();  
		socketStream = new ByteArrayOutputStream();
		requestSocket.setSoTimeout(500);
		byte[] buffer = new byte[1024];  
		int len; 
		try{
			while ((len = input0.read(buffer))>-1 ) {  
				socketStream.write(buffer, 0, len);  
			}	
		}catch(Exception e){
			//e.printStackTrace();
		}finally{
			socketStream.flush(); 
		}
		
		//初始化request实体
		InputStream input = this.getInputStream();
		InputStreamReader isr = new InputStreamReader(input);
		BufferedReader br = new BufferedReader(isr);
		
		String s = null;
		firstHead = br.readLine().split(" ");
		while ((s = br.readLine()) != null) {
			String attr[] = s.split(":\\s{1,}");
			if (attr.length < 2) {
				break;
			}
			try {
				attributes.put(attr[0], attr[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getRequestMethod() {
		return firstHead[0];
	}

	@Override
	public String getRequestURI() {
		String tmp[] = firstHead[1].split("\\?");
		return tmp[0];
	}

	@Override
	public String getProtocol() {
		return firstHead[2];
	}

	@Override
	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	@Override
	public void setAttribute(String name, Object object) {
		attributes.put(name, object);
	}

	@Override
	public Set<String> getAttributeNames() {
		return attributes.keySet();
	}

	@Override
	public String getCharacterEncoding() {
		return null;
	}

	@Override
	public int getContentLength() {
		return 0;
	}

	@Override
	public String getContentType() {
		return null;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		InputStream input = new ByteArrayInputStream(socketStream.toByteArray());
		return input;
	}

	@Override
	public String getRemoteAddr() {
		return requestSocket.getInetAddress().getHostAddress();
	}

	@Override
	public String getRemoteHost() {
		return requestSocket.getInetAddress().getHostName();
	}

	@Override
	public String getScheme() {
		return firstHead[2].split("/")[0];
	}

	@Override
	public String getServerName() {
		return (String) attributes.get("Host");
	}

	@Override
	public int getServerPort() {
		return requestSocket.getPort();
	}

}
