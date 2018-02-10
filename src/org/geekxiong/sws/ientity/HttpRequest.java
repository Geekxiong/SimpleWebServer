package org.geekxiong.sws.ientity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * 
 * @author 作者 : Shiwei Xiong
 * @version 创建时间 : 2018年2月11日 上午1:52:33
 * 
 */
public interface HttpRequest {
	
	// 返回这个请求的请求方法
	public String getRequestMethod();
	
	// 返回这个请求的文件路径
	public String getRequestURI();
	
	// 返回这个请求所用的协议，其形式是协议/主版本号.次版本号。例如对于一个HTTP1.0的请求，该方法返回HTTP/1.0。
	public String getProtocol();
	
	
	// 返回请求中指定属性的值，如果这个属性不存在，就返回一个空值。这个方法允许访问一些不提供给这个接口中其他方法的请求信息以及其他Servlet放置在这个请求对象内的数据。
	public Object getAttribute(String name);

	public void setAttribute(String name, Object object);

	// 返回包含在这个请求中的所有属性名的列表。
	public Set<String> getAttributeNames();

	// 返回请求中输入内容的字符编码类型，如果没有定义字符编码类型就返回空值。
	public String getCharacterEncoding();

	// 请求内容的长度，如果长度未知就返回-1。
	public int getContentLength();

	// 返回请求数据体的MIME类型，如果类型未知返回空值。
	public String getContentType();

	// 返回一个输入流用来从请求体读取二进制数据。如果在此之前已经通过getReader方法获得了要读取的结果，这个方法会抛出一个IllegalStateException。
	public InputStream getInputStream() throws IOException;


	// 返回发送请求者的IP地址。
	public String getRemoteAddr();

	// 返回发送请求者的主机名称。如果引擎不能或者选择不解析主机名（为了改善性能），这个方法会直接返回IP地址。
	public String getRemoteHost();

	// 返回请求所使用的URL的模式。例如，对于一个HTTP请求，这个模式就是http。
	public String getScheme();

	// 返回接收请求的服务器的主机名。
	public String getServerName();

	// 返回接收请求的端口号。
	public int getServerPort();

}
