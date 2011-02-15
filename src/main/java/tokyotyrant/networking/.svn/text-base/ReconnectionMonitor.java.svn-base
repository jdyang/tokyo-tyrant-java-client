package tokyotyrant.networking;

public class ReconnectionMonitor implements Runnable {
	private final Reconnections reconnections;
	private boolean running = false;
	
	public ReconnectionMonitor(Reconnections reconnections) {
		this.reconnections = reconnections;
	}

	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop() {
		running = false;
		notifyAll();
	}
	
	public synchronized void run() {
		while (running) {
			try {
				try {
					wait(reconnections.getTimeToNextAttempt());
				} catch (InterruptedException e) {
				}
				reconnections.reconnectDelayed();
			} catch (Exception e) {
				//ignore
			}
		}
	}
	
	public synchronized void reconnect(ServerNode node) {
		reconnections.reconnect(node);
		notifyAll();
	}
}
