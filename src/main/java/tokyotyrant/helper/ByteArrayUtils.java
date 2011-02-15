package tokyotyrant.helper;

import java.util.ArrayList;
import java.util.List;

public class ByteArrayUtils {
	public static int indexOf(byte[] b, int element, int fromIndex) {
		for (int i = fromIndex; i < b.length; i++) {
			if (b[i] == element) {
				return i;
			}
		}
		return -1;
	}

	public static List<byte[]> split(byte[] b, int separator) {
		List<byte[]> result = new ArrayList<byte[]>();
		int i = 0;
		while (i < b.length) {
			int e = indexOf(b, separator, i);
			if (e == -1) {
				break;
			}
			byte[] dest = new byte[e - i];
			System.arraycopy(b, i, dest, 0, dest.length);
			result.add(dest);
			i += dest.length + 1;
		}
		byte[] dest = new byte[b.length - i];
		System.arraycopy(b, i, dest, 0, dest.length);
		result.add(dest);
		return result;
	}
}
