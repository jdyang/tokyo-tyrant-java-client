/**
 * 
 */
package com.holmes.tokyotable.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tokyotyrant.MRDB;
import tokyotyrant.networking.NodeAddress;

/**
 * @author yangjiandong
 * 
 */
public class NIOTableClient extends AbstractTokyoTableClient {

	String addressList;

	MRDB db;

	private final Logger logger = LoggerFactory.getLogger(NIOTableClient.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.holmes.tokyotable.service.AbstractTokyoTableClient#init()
	 */
	@Override
	void init() {
		try {
			db = new MRDB();
			db
					.open(NodeAddress
							.addresses("tcp://10.150.39.25:11211 tcp://10.150.39.26:11211"));
		} catch (Exception e) {
			logger.error("init error", e);
			db.close();
		}
	}

	public String getAddressList() {
		return addressList;
	}

	public void setAddressList(String addressList) {
		this.addressList = addressList;
	}

}
