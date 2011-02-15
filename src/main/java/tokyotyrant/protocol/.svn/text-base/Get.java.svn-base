package tokyotyrant.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

import tokyotyrant.helper.BufferHelper;
import tokyotyrant.transcoder.Transcoder;

public class Get extends Command<Object> {
	private final byte[] key;
	private byte[] value;

	public Get(Transcoder keyTranscoder, Transcoder valueTranscoder, Object key) {
		super((byte) 0x30, keyTranscoder, valueTranscoder);
		this.key = keyTranscoder.encode(key);
	}
	
	public Object getReturnValue() {
		return isSuccess() ? valueTranscoder.decode(value) : null;
	}

	public void encode(ChannelBuffer out) {
		out.writeBytes(magic);
		out.writeInt(key.length);
		out.writeBytes(key);
	}

	public boolean decode(ChannelBuffer in) {
		if (in.readableBytes() < 1) {
			return false;
		}
		code = in.readByte();
		if (!isSuccess()) {
			return true;
		}
		if (!BufferHelper.prefixedDataAvailable(in, 4)) {
			return false;
		}
		int vsiz = in.readInt();
		value = new byte[vsiz];
		in.readBytes(value);
		return true;
	}
}
