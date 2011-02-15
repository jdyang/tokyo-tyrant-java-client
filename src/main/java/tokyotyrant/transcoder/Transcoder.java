package tokyotyrant.transcoder;

/**
* Convert between byte arrays and objects for storage in the database.
*/
public interface Transcoder {
	/**
	 * Encode the given object for storage.
	 *
	 * @param decoded the object
	 * @return the byte array representation of the object
	 */	
	byte[] encode(Object decoded);

	/**
	 * Decode the cached object into the object it represents.
	 *
	 * @param encoded the byte array representation of the object
	 * @return the object
	 */
	Object decode(byte[] encoded);
}
