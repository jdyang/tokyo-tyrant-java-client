package tokyotyrant.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

import tokyotyrant.transcoder.Transcoder;

public class Putnr extends PutCommandSupport {
	public Putnr(Transcoder keyTranscoder, Transcoder valueTranscoder, Object key, Object value) {
		super((byte) 0x18, keyTranscoder, valueTranscoder, key, value);
	}
	
	public boolean responseRequired() {
		return false;
	}
	
	public Boolean getReturnValue() {
		return Boolean.TRUE;
	}

	public boolean decode(ChannelBuffer in) {
		return true;
	}
}
