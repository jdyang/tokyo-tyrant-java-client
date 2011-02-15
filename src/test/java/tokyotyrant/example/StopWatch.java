package tokyotyrant.example;

public class StopWatch {
	private boolean started = false;
	private long start;
	private long stop;

	public StopWatch start() {
		if (started) {
			throw new IllegalStateException("Already started");
		}
		start = System.currentTimeMillis();
		return this;
	}
	
	public StopWatch stop() {
		if (started) {
			throw new IllegalStateException("Not started yet");
		}
		stop = System.currentTimeMillis();
		return this;
	}
	
	public long taken() {
		return stop - start;
	}
}
