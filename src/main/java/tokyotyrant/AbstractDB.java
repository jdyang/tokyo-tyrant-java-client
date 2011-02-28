/**
 * 
 */
package tokyotyrant;

import tokyotyrant.networking.NodeAddress;
import tokyotyrant.transcoder.StringTranscoder;
import tokyotyrant.transcoder.Transcoder;

/**
 * @author yangjiandong
 * 
 */
public abstract class AbstractDB {

	protected Transcoder keyTranscoder = new StringTranscoder();
	protected Transcoder valueTranscoder = new StringTranscoder();
	
	/**
	 * nio client
	 * @param addresses
	 * @throws Exception
	 */
	abstract public void open(NodeAddress[] addresses) throws Exception;
	
	/**
	 * normal client 
	 * @param address
	 */
	abstract public void open(NodeAddress address);
	
	abstract public void close();
}
