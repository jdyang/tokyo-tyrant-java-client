/**
 * 
 */
package com.holmes.tokyotable.service;

/**
 * @author yangjiandong
 * 
 */
public class Pair {

	String key;

	String value;

	public Pair(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public Pair() {

	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	

}
