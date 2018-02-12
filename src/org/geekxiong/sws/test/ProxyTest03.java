package org.geekxiong.sws.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 
 * @author 作者 : Shiwei Xiong
 * @version 创建时间 : 2018年2月11日 下午9:20:27
 * 
 */
public class ProxyTest03 {
	public final static String HOST = "v3.bootcss.com";
	public final static int PORT = 80;

	public static void main(String[] args) throws Exception {
		int cas = 0;
		ServerSocket server = new ServerSocket(80);
		System.err.println(new Date().toString() + "\t" + "Started!!!" + "\tport:" + server.getLocalPort());
		while (!server.isClosed()) {
			Socket socket = server.accept();
			Socket remoteSocket = new Socket(InetAddress.getByName(HOST), PORT);

			// 转发请求
			OutputStream out = remoteSocket.getOutputStream();
			PrintWriter pw = new PrintWriter(out);

			InputStream input = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(input);
			BufferedReader br = new BufferedReader(isr);
			String s = null;
			while ((s = br.readLine()) != null) {
				if (s.indexOf("Host") == 0) {
					s = "Host: " + HOST;
				}

				pw.print(s + "\r\n");
				//System.out.println(s);
				if (s.length() == 0) {
					break;
				}
			}
			pw.flush();

			System.out.println("************************************");

			// 转发响应
			OutputStream out2 = socket.getOutputStream();
			
			remoteSocket.setSoTimeout(500);
			InputStream input2 = remoteSocket.getInputStream();
			try{
				byte[] buffer = new byte[2048];
				int len; 
				while ((len = input2.read(buffer)) != -1) {
					out2.write(buffer,0,len);
				}
			}catch(Exception e){
				out2.flush();
			}
			System.out.println("case #"+(cas++) +" done!");
			remoteSocket.close();
			socket.close();

		}
		server.close();
	}

}
