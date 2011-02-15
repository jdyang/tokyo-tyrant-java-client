package tokyotyrant.protocol;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;

import tokyotyrant.transcoder.Transcoder;

public class Mget extends Command<Map<Object, Object>> {
	private final byte[][] keys;
	private Map<byte[], byte[]> values;

	public Mget(Transcoder keyTranscoder, Transcoder valueTranscoder, Object[] keys) {
		super((byte)0x31, keyTranscoder, valueTranscoder);
		this.keys = new byte[keys.length][];
		for (int i = 0; i < keys.length; i++) {
			this.keys[i] = keyTranscoder.encode(keys[i]);
		}
	}
	
	public Map<Object, Object> getReturnValue() {
		if (!isSuccess()) {
			return null;
		}
		Map<Object, Object> result = new HashMap<Object, Object>(values.size());
		for (Map.Entry<byte[], byte[]> each : values.entrySet()) {
			result.put(keyTranscoder.decode(each.getKey()), valueTranscoder.decode(each.getValue()));
		}
		return result;
	}

	public void encode(ChannelBuffer out) {
		out.writeBytes(magic);
		out.writeInt(keys.length);
		for (byte[] kbuf : keys) {
			out.writeInt(kbuf.length);
			out.writeBytes(kbuf);
		}
	}

	public boolean decode(ChannelBuffer in) {
		if (in.readableBytes() < 1) {
			return false;
		}
		code = in.readByte();

		if (in.readableBytes() < 4) {
			return false;
		}
		int rnum = in.readInt();

		values = new HashMap<byte[], byte[]>(rnum);
		for (int i = 0; i < rnum; i++) {
			if (in.readableBytes() < 4 + 4) {
				return false;
			}
			int ksiz = in.readInt();
			int vsiz = in.readInt();
			if (in.readableBytes() < ksiz + vsiz) {
				return false;
			}
			byte[] kbuf = new byte[ksiz];
			in.readBytes(kbuf);
			byte[] vbuf = new byte[vsiz];
			in.readBytes(vbuf);
			values.put(kbuf, vbuf);
		}
		return true;
	}
}
