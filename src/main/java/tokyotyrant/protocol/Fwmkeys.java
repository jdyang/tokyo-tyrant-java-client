package tokyotyrant.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

import tokyotyrant.transcoder.Transcoder;

public class Fwmkeys extends Command<Object[]> {
	private final byte[] prefix;
	private final int max;
	private byte[][] keys;

	public Fwmkeys(Transcoder keyTranscoder, Transcoder valueTranscoder, Object prefix, int max) {
		super((byte) 0x58, keyTranscoder, valueTranscoder);
		this.prefix = keyTranscoder.encode(prefix);
		this.max = max;
	}
	
	public Object[] getReturnValue() {
		if (!isSuccess()) {
			return null;
		}
		Object[] result = new Object[keys.length];
		for (int i = 0; i < keys.length; i++) {
			result[i] = keyTranscoder.decode(keys[i]);
		}
		return result;
	}

	public void encode(ChannelBuffer out) {
		out.writeBytes(magic);
		out.writeInt(prefix.length);
		out.writeInt(max);
		out.writeBytes(prefix);
	}

	public boolean decode(ChannelBuffer in) {
		if (in.readableBytes() < 1) {
			return false;
		}
		code = in.readByte();

		if (in.readableBytes() < 4) {
			return false;
		}
		int knum = in.readInt();

		keys = new byte[knum][];
		for (int i = 0; i < knum; i++) {
			if (in.readableBytes() < 4) {
				return false;
			}
			int ksiz = in.readInt();
			if (in.readableBytes() < ksiz) {
				return false;
			}
			byte[] kbuf = new byte[ksiz];
			in.readBytes(kbuf);
			keys[i] = kbuf;
		}
		return true;
	}
}
