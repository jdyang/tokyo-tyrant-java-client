/**
 * 
 */
package com.holmes.tokyotable.service;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import tokyotyrant.RDB;

/**
 * @author yangjiandong
 * 
 */
public class TokyoTableServiceImpl implements TokyoTableService,
		InitializingBean, DisposableBean {

	RDB db;

	String ip;

	int port;

	public void insert(String key, List<Pair> pairs) {
		String name = "put";
		List<byte[]> col = new ArrayList<byte[]>();
		col.add(key.getBytes());
		for (Pair tmp : pairs) {
			col.add(tmp.key.getBytes());
			col.add(tmp.value.getBytes());
		}

		db.misc(name, col, 0);

	}

	public List<String> search(SearchRex rex) {
		String name = "search";
		List<byte[]> col = new ArrayList<byte[]>();

		String cond = "addcond\0";
		cond += rex.getTarget().key;
		cond += "\0";
		cond += rex.getOpt();
		cond += "\0";
		// String str = "addcond\0age\0NUMBT\012,20";
		byte[] hel = rex.getTarget().value.getBytes();
		byte[] tt = new byte[cond.getBytes().length + hel.length];
		System.arraycopy(cond.getBytes(), 0, tt, 0, cond.getBytes().length);
		System.arraycopy(hel, 0, tt, cond.getBytes().length, hel.length);
		col.add(tt);

		List<byte[]> res = db.misc(name, col, 1);
		List<String> ret = new ArrayList<String>();
		for (byte[] tmp : res) {
			ret.add(new String(tmp));
			System.out.println(new String(tmp));
		}
		return ret;
	}

	public void afterPropertiesSet() throws Exception {
		db = new RDB();
		db.open(new InetSocketAddress("10.150.39.25", 11211));

	}

	public RDB getDb() {
		return db;
	}

	public void setDb(RDB db) {
		this.db = db;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
		TokyoTableServiceImpl t = new TokyoTableServiceImpl();
		t.afterPropertiesSet();
		Pair p = new Pair();
		p.key = "age";
		p.value = "19,25";
		SearchRex s = new SearchRex();
		s.target = p;
		s.opt = SearchRex.NUMBT;
		t.search(s);
	}

	public void destroy() throws Exception {
		db.close();
		db = null;

	}

	public int delete(String key) {
		boolean ret = db.out(key);
		if (true == ret) {
			return 0;
		} 
		return -1;

	}

}
