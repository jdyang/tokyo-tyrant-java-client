package tokyotyrant.tester;

import tokyotyrant.MRDB;
import tokyotyrant.networking.NodeAddress;

import java.util.Arrays;

public class MRDBShell extends Shell {
	private MRDB db;
	private NodeAddress[] addresses;
	
	protected void options(String[] args) {
		addresses = new NodeAddress[args.length];
		int i = 0;
		for (String each : args) {
			addresses[i++] = new NodeAddress(each);
		}
	}

	protected void openConnection() throws Exception {
		db = new MRDB();
		db.open(addresses);
	}

	protected void closeConnection() {
		db.close();
	}
	
	public Object repl(String input) throws Exception {
		String command = command(input);
		String[] args = arguments(input);
		Object result = null;
		if ("put".equals(command)) {
			result = db.put(args[0], args[1]).get();
		} else if ("putkeep".equals(command)) {
			result = db.putkeep(args[0], args[1]).get();
		} else if ("putcat".equals(command)) {
			result = db.putcat(args[0], args[1]).get();
		} else if ("putshl".equals(command)) {
			result = db.putshl(args[0], args[1], Integer.parseInt(args[2])).get();
		} else if ("putnr".equals(command)) {
			db.putnr(args[0], args[1]);
		} else if ("out".equals(command)) {
			result = db.out(args[0]).get();
		} else if ("get".equals(command)) {
			result = args[0] + "\t" + db.get(args[0]).get();
		} else if ("mget".equals(command)) {
			Object[] keys = args;
			result = db.mget(keys).get();
		} else if ("vsiz".equals(command)) {
			result = db.vsiz(args[0]).get();
		} else if ("list".equals(command)) {
			result = Arrays.asList(db.fwmkeys("", Integer.MAX_VALUE).get());
		} else if ("fwmkeys".equals(command)) {
			result = Arrays.asList(db.fwmkeys(args[0], Integer.parseInt(args[1])).get());
		} else if ("addint".equals(command)) {
			result = db.addint(args[0], Integer.parseInt(args[1])).get();
		} else if ("adddouble".equals(command)) {
			result = db.adddouble(args[0], Double.parseDouble(args[1])).get();
		} else if ("ext".equals(command)) {
			result = db.ext(args[0], args[1], args[2], Integer.parseInt(args[3])).get();
		} else if ("sync".equals(command)) {
			result = db.sync().get();
		} else if ("vanish".equals(command)) {
			result = db.vanish().get();
		} else if ("rnum".equals(command)) {
			result = db.rnum().get();
		} else if ("size".equals(command)) {
			result = db.size().get();
		} else if ("stat".equals(command)) {
			result = db.stat();
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		MRDBShell shell = new MRDBShell();
		System.exit(shell.run(args));
	}
}