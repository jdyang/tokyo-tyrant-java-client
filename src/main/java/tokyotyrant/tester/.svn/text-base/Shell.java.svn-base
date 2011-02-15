package tokyotyrant.tester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public abstract class Shell {
	protected BufferedReader stdin;
	protected PrintWriter stdout;
	protected PrintWriter stderr;
	
	public Shell() {
		stdin = new BufferedReader(new InputStreamReader(System.in));
		stdout = new PrintWriter(System.out);
		stderr = new PrintWriter(System.err);
	}
	
	protected String prompt() throws IOException {
		stdout.print(">>> ");
		stdout.flush();
		return stdin.readLine();
	}
	
	protected abstract void options(String[] args);
	
	public int run(String[] args) throws Exception {
		options(args);
		openConnection();
		while (true) {
			try {
				String input = prompt();
				if ("quit".equals(input)) {
					break;
				}
				long s = System.currentTimeMillis();
				Object result = repl(input);
				long e = System.currentTimeMillis();
				stdout.println("(" + (e - s) + "ms)" + "\t" + result);
			} catch (Exception e) {
				e.printStackTrace(stderr);
				stderr.flush();
			}
		}
		closeConnection();
		return 0;
	}
	
	protected String command(String input) {
		return input.split("\\s")[0];
	}
	
	protected String[] arguments(String input) {
		String[] tokens = input.split("\\s");
		String[] args = null;
		if (tokens.length > 0) {
			args = new String[tokens.length - 1];
			System.arraycopy(tokens, 1, args, 0, args.length);
		} else {
			args = new String[0]; 
		}
		return args;
	}

	protected abstract void openConnection() throws Exception;

	protected abstract void closeConnection();

	public abstract Object repl(String input) throws Exception;
}
