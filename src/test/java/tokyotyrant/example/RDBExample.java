package tokyotyrant.example;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tokyotyrant.RDB;

import com.holmes.tokyotable.service.DefaultTokyoTableEngine;
import com.holmes.tokyotable.service.Pair;
import com.holmes.tokyotable.service.SearchRex;

public class RDBExample {
	public static void main(String[] args) throws IOException {
		//		add();
		// get();
		// bet();
		// del();
		// search();
		insert();
		// test();
	}

	static void test() {
		DefaultTokyoTableEngine s = new DefaultTokyoTableEngine();
		s.setIp("10.150.39.25");
		s.setPort(11211);
		try {
			s.afterPropertiesSet();

			List<Pair> l = new ArrayList<Pair>();

			l.add(new Pair("longTime", "124"));

			s.insert("youraremin", l);

			SearchRex rex = new SearchRex();
			rex.setOpt(SearchRex.NUMEQ);
			rex.setTarget(new Pair("longTime", "123"));

			System.out.println(s.search(rex));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void insert() {
		DefaultTokyoTableEngine s = new DefaultTokyoTableEngine();
		s.setIp("10.150.39.25");
		s.setPort(11211);
		try {
			s.afterPropertiesSet();

			List<Pair> l = new ArrayList<Pair>();

			l.add(new Pair("longTime", "123"));

			s.insert("youraremin", l);
			Map<String, String> map = s.get("youraremin");
			for (Entry<String, String> tmp : map.entrySet()) {
				System.out.println(tmp.getKey() + " " + tmp.getValue());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void search() {
		DefaultTokyoTableEngine s = new DefaultTokyoTableEngine();
		s.setIp("10.150.39.25");
		s.setPort(11211);
		try {
			s.afterPropertiesSet();
			SearchRex rex = new SearchRex();
			rex.setTarget(new Pair("longTime", "0,1298965353980"));
			rex.setOpt(SearchRex.NUMBT);

			SearchRex rex1 = new SearchRex();
			rex1.setTarget(new Pair("packetstate", "11".toString()));
			rex1.setOpt(SearchRex.STREQ);
			//

			// List<String> ret = s.search(rex, rex1);
			List<String> ret = s.search(rex);

			System.out.println(ret.size());
			for (String tmp : ret) {
				System.out.println(tmp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static void del() {
		RDB db = new RDB();

		db.open(new InetSocketAddress("10.150.39.25", 11211));

		// String name = "out";
		// List<byte[]> col = new ArrayList<byte[]>();
		// String cond = "addcond\0out\0table";
		// col.add(cond.getBytes());
		//
		// db.misc(name, col, 1);

		db.out("table");

		db.close();
	}

	static void bet() {
		RDB db = new RDB();

		db.open(new InetSocketAddress("10.150.39.25", 11211));

		String name = "search";
		List<byte[]> col = new ArrayList<byte[]>();
		String cond = "addcond\0age\0NUMGE\0";
		byte[] hel = "12,20".getBytes();
		byte[] tt = new byte[cond.getBytes().length + hel.length];
		System.arraycopy(cond.getBytes(), 0, tt, 0, cond.getBytes().length);
		System.arraycopy(hel, 0, tt, cond.getBytes().length, hel.length);
		col.add(tt);

		List<byte[]> res = db.misc(name, col, 1);
		System.out.println(res.size());
		for (byte[] tmp : res) {
			System.out.println(new String(tmp));
		}
		db.close();
	}

	static void get() throws IOException {

		RDB db = new RDB();

		db.open(new InetSocketAddress("10.150.39.25", 11211));

		String name = "search";
		List<byte[]> col = new ArrayList<byte[]>();
		String cond = "addcond\0age\0NUMGE\0";
		byte[] hel = "20".getBytes();
		byte[] tt = new byte[cond.getBytes().length + hel.length];
		System.arraycopy(cond.getBytes(), 0, tt, 0, cond.getBytes().length);
		System.arraycopy(hel, 0, tt, cond.getBytes().length, hel.length);
		col.add(tt);

		List<byte[]> res = db.misc(name, col, 1);
		System.out.println(res.size());
		for (byte[] tmp : res) {
			System.out.println(new String(tmp));
		}
		db.close();
	}

	static void add() {
		RDB db = new RDB();
		db.open(new InetSocketAddress("10.150.39.26", 11211));

		String name = "put";
		List<byte[]> col = new ArrayList<byte[]>();
		col.add("hello".getBytes());
		col.add("name".getBytes());
		col.add("tong".getBytes());
		col.add("age".getBytes());
		col.add("19".getBytes());
		// try {
		// col.add(l2b(20l));
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		List<byte[]> res = db.misc(name, col, 0);
		if (res == null) {
			res = new ArrayList<byte[]>();
		}
		for (byte[] tmp : res) {
			System.out.println(new String(tmp));
		}

		db.close();

	}

	static byte[] l2b(Long i) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeLong(i);
		return bos.toByteArray();
	}
}
