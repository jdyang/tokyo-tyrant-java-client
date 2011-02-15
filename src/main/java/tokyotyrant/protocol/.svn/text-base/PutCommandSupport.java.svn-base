package tokyotyrant.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

import tokyotyrant.transcoder.Transcoder;

public abstract class PutCommandSupport extends Command<Boolean> {
	private final byte[] key;
	private final byte[] value;

	public PutCommandSupport(byte commandId, Transcoder keyTranscoder, Transcoder valueTranscoder, Object key, Object value) {
		super(commandId, keyTranscoder, valueTranscoder);
		this.key = keyTranscoder.encode(key);
		this.value = valueTranscoder.encode(value);
	}

	public Boolean getReturnValue() {
		return isSuccess();
	}
	
	public void encode(ChannelBuffer out) {
		out.writeBytes(magic);
		out.writeInt(key.length);
		out.writeInt(value.length);
		out.writeBytes(key);
		out.writeBytes(value);
	}

	public boolean decode(ChannelBuffer in) {
		if (in.readableBytes() < 1) {
			return false;
		}
		code = in.readByte();
		return true;
	}
}
