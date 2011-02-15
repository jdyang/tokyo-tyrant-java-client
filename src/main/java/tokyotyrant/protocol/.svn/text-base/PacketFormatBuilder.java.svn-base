package tokyotyrant.protocol;

import java.util.ArrayList;
import java.util.List;

import tokyotyrant.protocol.PacketFormat.CodeField;
import tokyotyrant.protocol.PacketFormat.Field;

public class PacketFormatBuilder {
	private List<Field> fields = new ArrayList<Field>();

	protected PacketFormatBuilder add(Field field) {
		fields.add(field);
		return this;
	}
	
	public PacketFormatBuilder magic() {
		return bytes("magic", 2);
	}
	
	public PacketFormatBuilder code(boolean stopWhenError) {
		return add(new CodeField(stopWhenError));
	}
	
	public PacketFormatBuilder int8(String name) {
		return add(new Field(name, Byte.class, 1));
	}

	public PacketFormatBuilder int32(String name) {
		return add(new Field(name, Integer.class, 4));
	}

	public PacketFormatBuilder int64(String name) {
		return add(new Field(name, Long.class, 8));
	}
	
	public PacketFormatBuilder bytes(String name, int size) {
		return add(new Field(name, byte[].class, size));
	}

	public PacketFormatBuilder bytes(String name, String sizeVariable) {
		return add(new Field(name, byte[].class, sizeVariable));
	}
	
	public PacketFormat end() {
		return new PacketFormat(fields.toArray(new Field[fields.size()]));
	}
}
