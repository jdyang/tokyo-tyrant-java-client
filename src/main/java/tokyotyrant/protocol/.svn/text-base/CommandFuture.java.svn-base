package tokyotyrant.protocol;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CommandFuture<T> implements Future<T> {
	private final Command<T> command;
	private final CountDownLatch latch;
	private long globalTimeout;

	public CommandFuture(Command<T> command, CountDownLatch latch, long globalTimeout) {
		this.command = command;
		this.latch = latch;
		this.globalTimeout = globalTimeout;
	}

	public T get() throws InterruptedException, ExecutionException {
		try {
			return get(globalTimeout , TimeUnit.MILLISECONDS);
		} catch (TimeoutException e) {
			throw new RuntimeException("Timed out waiting for operation " + command.toString(), e);
		}
	}

	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		if (!latch.await(timeout, unit)) {
			throw new TimeoutException("Timed out waiting for operation " + command.toString());
		}
		if (command.hasError()) {
			throw new ExecutionException(command.getErrorException());
		}
		if (isCancelled()) {
			throw new ExecutionException(new RuntimeException("Cancelled " + command.toString()));
		}
		return (T) command.getReturnValue();
	}
	
	public boolean cancel(boolean mayInterruptIfRunning) {
		command.cancel();
		// This isn't exactly correct, but it's close enough.  If we're in
		// a writing state, we *probably* haven't started.
		return command.isWriting();
	}

	public boolean isCancelled() {
		return command.isCancelled();
	}

	public boolean isDone() {
		return command.isCompleted() || command.hasError() || command.isCancelled();
	}
}
