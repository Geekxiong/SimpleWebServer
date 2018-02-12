package org.geekxiong.sws.entity;

import org.geekxiong.sws.ientity.ServerEntity;

/**
 * 
 * @author  作者 : Shiwei Xiong  
 * @version 创建时间 : 2018年2月12日 下午1:17:30 
 * 
 */
public class LocalServerEntity extends ServerEntity{
	private String webroot;
	public String getWebroot() {
		return webroot;
	}
	public void setWebroot(String webroot) {
		this.webroot = webroot;
	}
}
