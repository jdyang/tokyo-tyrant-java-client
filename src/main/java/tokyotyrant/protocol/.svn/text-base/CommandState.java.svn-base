package tokyotyrant.protocol;

/**
 * Possible transitions:
 * <ul>
 * <li>{@link #WRITING} -{@link Command#reading}-&gt; {@link #READING}</li>
 * <li>{@link #READING} -{@link Command#complete}-&gt; {@link #COMPLETE}</li>
 * <li>{@link #WRITING} -{@link Command#cancel}-&gt; {@link #CANCELLED}</li>
 * <li>{@link #WRITING} -{@link Command#error}-&gt; {@link #ERROR}</li>
 * <li>{@link #READING} -{@link Command#cancel}-&gt; {@link #CANCELLED}</li>
 * <li>{@link #READING} -{@link Command#error}-&gt; {@link #ERROR}</li>
 * </ul>
 */
public enum CommandState {
	/**
	 * State indicating this operation is writing data to the server.
	 */
	WRITING,
	/**
	 * State indicating this operation is reading data from the server.
	 */
	READING,
	/**
	 * State indicating this operation is complete.
	 */
	COMPLETE,
	/**
	 * State indicating this operation is cancelled.
	 */
	CANCELLED,
	/**
	 * State indicating this operation has an error;
	 */
	ERROR
}