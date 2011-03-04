/**
 * 
 */
package com.holmes.tokyotable.service;

/**
 * @author yangjiandong
 * 
 */
public class SearchRex {

	// string equeal
	public static final String STREQ = "STREQ";

	// between
	public static final String NUMBT = "NUMBT";

	// >
	public static final String NUMGT = "NUMGT";

	// >=
	public static final String NUMGE = "NUMGE";

	// <
	public static final String NUMLT = "NUMLT";

	// <=
	public static final String NUMLE = "NUMLE";

	// number=
	public static final String NUMEQ = "NUMEQ";

	Pair target;

	String opt;

	public Pair getTarget() {
		return target;
	}

	public void setTarget(Pair target) {
		this.target = target;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public static String getStreq() {
		return STREQ;
	}

	public static String getNumbt() {
		return NUMBT;
	}

	public static String getNumgt() {
		return NUMGT;
	}

	public static String getNumge() {
		return NUMGE;
	}

	public static String getNumlt() {
		return NUMLT;
	}

	public static String getNumle() {
		return NUMLE;
	}

	public static String getNumeq() {
		return NUMEQ;
	}

}
