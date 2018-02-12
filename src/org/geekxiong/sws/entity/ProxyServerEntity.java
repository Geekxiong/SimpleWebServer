package org.geekxiong.sws.entity;

import org.geekxiong.sws.ientity.ServerEntity;

/**
 * 
 * @author  作者 : Shiwei Xiong  
 * @version 创建时间 : 2018年2月12日 下午1:28:06 
 * 
 */
public class ProxyServerEntity extends ServerEntity{
	private String proxy_host;
	private Integer proxy_port;
	public String getProxy_host() {
		return proxy_host;
	}
	public void setProxy_host(String proxy_host) {
		this.proxy_host = proxy_host;
	}
	public Integer getProxy_port() {
		return proxy_port;
	}
	public void setProxy_port(Integer proxy_port) {
		this.proxy_port = proxy_port;
	}
}
