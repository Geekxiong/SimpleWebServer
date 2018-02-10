package org.geekxiong.sws.ientity;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * 
 * @author  作者 : Shiwei Xiong  
 * @version 创建时间 : 2018年2月11日 上午3:34:39 
 * 
 */
public interface HttpResponse {

	//返回MIME实体的字符编码。这个字符编码可以是指定的类型，也可以是与请求头域所反映的客户端所能接受的字符编码最匹配的类型。在HTTP协议中，这个信息被通过Accept-Charset传送到Servlet引擎。
    public String getCharacterEncoding();

    //如果这个响应对象已经调用getWriter，将会抛出IllegalStateException。
    //返回一个记录二进制的响应数据的输出流。
    public OutputStream getOutputStream() throws IOException;

    //这个方法返回一个PringWriter对象用来记录格式化的响应实体。如果要反映使用的字符编码，必须修改响应的MIME类型。在调用这个方法之前，必须设定响应的content类型。
    //如果没有提供这样的编码类型，会抛出一个UnsupportedEncodingException，如果这个响应对象已调用getOutputStream，会抛出一个getOutputStream。
    public PrintWriter getWriter() throws IOException;

    //设置响应的内容的长度，这个方法会覆盖以前对内容长度的设定。
    //为了保证成功地设定响应头的内容长度，在响应被提交到输出流之前必须调用这个方法。
    public void setContentLength(int length);

    public void setContentType(String type);
}
