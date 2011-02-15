package tokyotyrant.protocol;

import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;

public class Misc extends Command<List<byte[]>> {
	private final byte[] name;
	private final List<byte[]> args;
	private final int opts;
	private List<byte[]> elements;
	
	public Misc(String name, List<byte[]> args, int opts) {
		super((byte) 0x90, null, null);
		this.name = name.getBytes();
		this.args = args;
		this.opts = opts;
	}
	
	public List<byte[]> getReturnValue() {
		return isSuccess() ? elements : null;
	}

	public void encode(ChannelBuffer out) {
		out.writeBytes(magic);
		out.writeInt(name.length);
		out.writeInt(opts);
		out.writeInt(args.size());
		out.writeBytes(name);
		for (byte[] abuf : args) {
			out.writeInt(abuf.length);
			out.writeBytes(abuf);
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

		elements = new ArrayList<byte[]>(rnum);
		for (int i = 0; i < rnum; i++) {
			if (in.readableBytes() < 4) {
				return false;
			}
			int esiz = in.readInt();
			if (in.readableBytes() < esiz) {
				return false;
			}
			byte[] ebuf = new byte[esiz];
			in.readBytes(ebuf);
			elements.add(ebuf);
		}
		return true;
	}
}
