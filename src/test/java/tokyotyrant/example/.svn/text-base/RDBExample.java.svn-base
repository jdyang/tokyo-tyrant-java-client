package tokyotyrant.example;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.cglib.transform.impl.AddDelegateTransformer;

import tokyotyrant.RDB;
import tokyotyrant.transcoder.DoubleTranscoder;
import tokyotyrant.transcoder.IntegerTranscoder;

public class RDBExample {
	public static void main(String[] args) throws IOException {
		// add();
		// get();
		// bet();
		del();
	}

	static void del() {
		RDB db = new RDB();

		db.open(new InetSocketAddress("10.150.39.25", 11211));

//		String name = "out";
//		List<byte[]> col = new ArrayList<byte[]>();
//		String cond = "addcond\0out\0table";
//		col.add(cond.getBytes());
//
//		db.misc(name, col, 1);
		
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
