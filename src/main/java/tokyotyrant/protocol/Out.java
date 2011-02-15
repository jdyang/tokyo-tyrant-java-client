package tokyotyrant.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

import tokyotyrant.transcoder.Transcoder;

public class Out extends Command<Boolean> {
	private final byte[] key;

	public Out(Transcoder keyTranscoder, Transcoder valueTranscoder, Object key) {
		super((byte) 0x20, keyTranscoder, valueTranscoder);
		this.key = keyTranscoder.encode(key);
	}
	
	public Boolean getReturnValue() {
		return isSuccess();
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
		return true;
	}
}
