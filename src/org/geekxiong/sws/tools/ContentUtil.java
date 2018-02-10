package org.geekxiong.sws.tools;

/**
 * 
 * @author 作者 : Shiwei Xiong
 * @version 创建时间 : 2018年2月11日 上午3:59:12
 * 
 */
public class ContentUtil {

	public static String getContentType(String prefix) {
		String contentType = null ;
		try {
			contentType = PropertiesUtil.getProperty(prefix);
			if(contentType==null){
				contentType="*";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "Content-Type: "+contentType+"\r\n";
	}

}