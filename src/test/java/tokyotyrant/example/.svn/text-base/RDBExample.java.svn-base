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
		get();
	}

	static void get() throws IOException {

		RDB db = new RDB();

		db.open(new InetSocketAddress("10.150.39.25", 11211));

		String name = "search";
		List<byte[]> col = new ArrayList<byte[]>();
		String str = "addcond\0age\0NUMBT\012,20";
//		byte[] min = l2b(16l);
//		byte[] max = l2b(20l);
//		byte[] co = ",".getBytes();
//		byte[] tt = new byte[str.getBytes().length + min.length + co.length
//				+ max.length];
//		System.arraycopy(str.getBytes(), 0, tt, 0, str.getBytes().length);
//		System.arraycopy(min, 0, tt, str.getBytes().length, min.length);
//		System.arraycopy(co, 0, tt, str.getBytes().length + min.length,
//				co.length);
//		System.arraycopy(max, 0, tt, str.getBytes().length + co.length
//				+ min.length, max.length);
//		col.add(tt);
//		for (byte tmp : tt) {
//			System.out.printf("%c", tmp);
//		}
		col.add(str.getBytes());

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
