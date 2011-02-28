package tokyotyrant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import tokyotyrant.networking.ActiveStandbyNodeLocator;
import tokyotyrant.networking.Networking;
import tokyotyrant.networking.NodeAddress;
import tokyotyrant.networking.NodeSelector;
import tokyotyrant.networking.ServerNode;
import tokyotyrant.networking.nio.NioNetworking;
import tokyotyrant.protocol.Adddouble;
import tokyotyrant.protocol.Addint;
import tokyotyrant.protocol.Command;
import tokyotyrant.protocol.CommandFuture;
import tokyotyrant.protocol.Ext;
import tokyotyrant.protocol.Fwmkeys;
import tokyotyrant.protocol.Get;
import tokyotyrant.protocol.Mget;
import tokyotyrant.protocol.Misc;
import tokyotyrant.protocol.Out;
import tokyotyrant.protocol.Put;
import tokyotyrant.protocol.Putcat;
import tokyotyrant.protocol.Putkeep;
import tokyotyrant.protocol.Putnr;
import tokyotyrant.protocol.Putshl;
import tokyotyrant.protocol.Rnum;
import tokyotyrant.protocol.Size;
import tokyotyrant.protocol.Stat;
import tokyotyrant.protocol.Sync;
import tokyotyrant.protocol.Vanish;
import tokyotyrant.protocol.Vsiz;
import tokyotyrant.transcoder.Transcoder;

/**
 * Multiple Rs DB. Replicated, Reliable, Responsive, Remote, etc.
 * API is similar to {@link RDB}, but {@link MRDB} returns {@link Future}.
 */
public class MRDB extends AbstractDB{
	private long globalTimeout = 1000L;
	private Networking networking;
	
    public MRDB() throws Exception {
    	this(new NioNetworking(new ActiveStandbyNodeLocator(), new NodeSelector()));
    }
	
    public MRDB(Networking networking) throws Exception {
		this.networking = networking;
	}
    
    /**
     * Open connections.
     * 
     * @param addresses the addresses of the servers to connect.
     */
    public void open(NodeAddress[] addresses) throws Exception {
		if (addresses.length == 0) {
			throw new IllegalArgumentException("Requires at least 1 node");
		}
		networking.initialize(addresses);
		networking.start();
    }

    /**
     * Close all connections.
     */
	public void close() {
		networking.stop();
	}
	
	public void setGlobalTimeout(long timeout) {
		this.globalTimeout = timeout;
	}
	
	/**
	 * Set the {@link Transcoder} to be used to transcode keys.
	 * Not required: @{link StringTranscoder} is the default.
	 */
	public void setKeyTranscoder(Transcoder transcoder) {
		this.keyTranscoder = transcoder;
	}
	
	/**
	 * Set the {@link Transcoder} to be used to transcode values.
	 * Not required: @{link SerializingTranscoder} is the default.
	 */
	public void setValueTranscoder(Transcoder transcoder) {
		this.valueTranscoder = transcoder;
	}
	
	/**
	 * Get the {@link Transcoder} to be used to transcode keys.
	 */
	public Transcoder getKeyTranscoder() {
		return keyTranscoder;
	}
	
	/**
	 * Get the {@link Transcoder} to be used to transcode values.
	 */
	public Transcoder getValueTranscoder() {
		return valueTranscoder;
	}

	protected <T> CommandFuture<T> execute(Command<T> command) {
		CommandFuture<T> future = command.writing(globalTimeout);
		networking.send(command);
		return future;
	}

	protected <T> CommandFuture<T> execute(ServerNode node, Command<T> command) {
		CommandFuture<T> future = command.writing(globalTimeout);
		networking.send(node, command);
		return future;
	}
	/**
	 * Await the future gracefully.
	 * 
	 * @param future the future to wait
	 */
	public <T> T await(Future<T> future) throws RuntimeException {
		try {
			return future.get();
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted", e);
		} catch (ExecutionException e) {
			throw new RuntimeException("Exception while executing", e);
		}
	}

	/**
	 * Await the future gracefully with timeout.
	 * 
	 * @param future the future to wait
	 * @param timeout the maximum time to wait
	 * @param unit the time unit of the timeout argument
	 */
	public <T> T await(Future<T> future, long timeout, TimeUnit unit) throws RuntimeException {
		try {
			return future.get(timeout, unit);
		} catch (TimeoutException e) {
			throw new RuntimeException("Timeout", e);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted", e);
		} catch (ExecutionException e) {
			throw new RuntimeException("Exception while executing", e);
		}
	}

	public Future<Boolean> put(Object key, Object value) {
		return execute(new Put(keyTranscoder, valueTranscoder, key, value));
	}

	public Future<Boolean> put(Object key, Object value, Transcoder valueTranscoder) {
		return execute(new Put(keyTranscoder, valueTranscoder, key, value));
	}

	public Future<Boolean> putkeep(Object key, Object value) {
		return execute(new Putkeep(keyTranscoder, valueTranscoder, key, value));
	}

	public Future<Boolean> putkeep(Object key, Object value, Transcoder valueTranscoder) {
		return execute(new Putkeep(keyTranscoder, valueTranscoder, key, value));
	}

	public Future<Boolean> putcat(Object key, Object value) {
		return execute(new Putcat(keyTranscoder, valueTranscoder, key, value));
	}

	public Future<Boolean> putcat(Object key, Object value, Transcoder valueTranscoder) {
		return execute(new Putcat(keyTranscoder, valueTranscoder, key, value));
	}

	public Future<Boolean> putshl(Object key, Object value, int width) {
		return execute(new Putshl(keyTranscoder, valueTranscoder, key, value, width));
	}

	public Future<Boolean> putshl(Object key, Object value, int width, Transcoder valueTranscoder) {
		return execute(new Putshl(keyTranscoder, valueTranscoder, key, value, width));
	}

	public void putnr(Object key, Object value) {
		execute(new Putnr(keyTranscoder, valueTranscoder, key, value));
	}

	public void putnr(Object key, Object value, Transcoder valueTranscoder) {
		execute(new Putnr(keyTranscoder, valueTranscoder, key, value));
	}

	public Future<Boolean> out(Object key) {
		return execute(new Out(keyTranscoder, valueTranscoder, key));
	}
	
	public Future<Object> get(Object key) {
		return execute(new Get(keyTranscoder, valueTranscoder, key));
	}
	
	public Future<Object> get(Object key, Transcoder valueTranscoder) {
		return execute(new Get(keyTranscoder, valueTranscoder, key));
	}

	public Future<Map<Object, Object>> mget(Object[] keys) {
		return execute(new Mget(keyTranscoder, valueTranscoder, keys));
	}

	public Future<Map<Object, Object>> mget(Object[] keys, Transcoder valueTranscoder) {
		return execute(new Mget(keyTranscoder, valueTranscoder, keys));
	}

	public Future<Integer> vsiz(Object key) {
		return execute(new Vsiz(keyTranscoder, valueTranscoder, key));
	}

	public Future<Object[]> fwmkeys(Object prefix, int max) {
		return execute(new Fwmkeys(keyTranscoder, valueTranscoder, prefix, max));
	}

	public Future<Integer> addint(Object key, int num) {
		return execute(new Addint(keyTranscoder, valueTranscoder, key, num));
	}

	public Future<Double> adddouble(Object key, double num) {
		return execute(new Adddouble(keyTranscoder, valueTranscoder, key, num));
	}

	public Future<Object> ext(String name, Object key, Object value, int opts) {
		return execute(new Ext(keyTranscoder, valueTranscoder, name, key, value, opts));
	}

	public Future<Object> ext(String name, Object key, Object value, int opts, Transcoder valueTranscoder) {
		return execute(new Ext(keyTranscoder, valueTranscoder, name, key, value, opts));
	}

	public Future<Boolean> sync() {
		return execute(new Sync());
	}

	public Future<Boolean> vanish() {
		return execute(new Vanish());
	}

	public Future<Long> rnum() {
		return execute(new Rnum());
	}

	public Future<Long> size() {
		return execute(new Size());
	}

	public Map<NodeAddress, Map<String, String>> stat() {
		Map<ServerNode, Future<Map<String, String>>> futures = new HashMap<ServerNode, Future<Map<String, String>>>();
		for (ServerNode each : networking.getNodeLocator().getAll()) {
			futures.put(each, execute(each, new Stat()));
		}
		Map<NodeAddress, Map<String, String>> result = new HashMap<NodeAddress, Map<String, String>>();
		for (ServerNode each : networking.getNodeLocator().getAll()) {
			result.put(each.getAddress(), await(futures.get(each)));
		}
		return result;
	}

	@Override
	public void open(NodeAddress address) {
		
	}
}
