package tokyotyrant.helper;

import org.jboss.netty.buffer.ChannelBuffer;

public class BufferHelper {
    public static boolean prefixedDataAvailable(ChannelBuffer in, int prefixLength) {
    	return prefixedDataAvailable(in, prefixLength, Integer.MAX_VALUE);
    }

    public static boolean prefixedDataAvailable(ChannelBuffer in, int prefixLength, int maxDataLength) {
        if (in.readableBytes() < prefixLength) {
            return false;
        }

        long dataLength;
        switch (prefixLength) {
        case 1:
            dataLength = in.getUnsignedByte(in.readerIndex());
            break;
        case 2:
            dataLength = in.getUnsignedShort(in.readerIndex());
            break;
        case 4:
            dataLength = in.getUnsignedInt(in.readerIndex());
            break;
        default:
            throw new IllegalArgumentException("prefixLength: " + prefixLength);
        }

        if (dataLength < 0 || dataLength > maxDataLength) {
            throw new IllegalStateException("dataLength: " + dataLength);
        }

        return in.readableBytes() >= dataLength + prefixLength;
	}

    private BufferHelper() {
	}
}
