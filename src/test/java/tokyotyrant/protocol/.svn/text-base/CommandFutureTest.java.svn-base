package tokyotyrant.protocol;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;

public class CommandFutureTest {
	private PingCommand command;
	private Future<Boolean> dut;

	@Before public void beforeEach() {
		command = new PingCommand(42);
		dut = command.writing(Long.MAX_VALUE);
	}
	
	@Test public void whenComplete() throws InterruptedException, ExecutionException {
		assertFalse(command.isReading());
		command.reading();
		assertTrue(command.isReading());
		assertFalse(dut.isDone());
		command.complete();
		assertTrue(dut.isDone());
		assertTrue(dut.get());
	}

	@Test(expected=ExecutionException.class) public void whenError() throws InterruptedException, ExecutionException {
		Exception exception = new Exception();
		command.error(exception);
		assertTrue(command.hasError());
		assertSame(exception, command.getErrorException());
		assertTrue(dut.isDone());
		dut.get();
	}
	
	@Test(expected=ExecutionException.class)
	public void whenCancel() throws InterruptedException, ExecutionException {
		dut.cancel(true);
		assertTrue(command.isCancelled());
		assertTrue(dut.isDone());
		dut.get();
	}
	
	@Test(expected=TimeoutException.class)
	public void getThrowsTimeoutExceptionWhenTimeoutExpired() throws InterruptedException, ExecutionException, TimeoutException {
		dut.get(10, TimeUnit.MILLISECONDS);
	}
}
