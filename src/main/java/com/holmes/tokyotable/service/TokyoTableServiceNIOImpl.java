/**
 * 
 */
package com.holmes.tokyotable.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import tokyotyrant.MRDB;

/**
 * @author yangjiandong
 * 
 */
public class TokyoTableServiceNIOImpl implements TokyoTableService,
		InitializingBean {

	NIOTableClient client;

	MRDB db;

	public void insert(String key, List<Pair> pairs) {
	}


	public void afterPropertiesSet() throws Exception {
		client.init();
		this.db = client.db;
	}

	public int delete(String key) {
		return -1;
	}


	public List<String> search(SearchRex... rexs) {
		// TODO Auto-generated method stub
		return null;
	}

}
