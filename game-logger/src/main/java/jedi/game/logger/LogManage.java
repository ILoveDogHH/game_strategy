package jedi.game.logger;

public class LogManage {
	private static final char [] LOG_SEPARATOR_ARR =  new char []{',',';',':','@','#','^','*','=','~','-','&','.','_'};
	public static char getGlueCharByLevel(int lv) {
		if (lv >= LOG_SEPARATOR_ARR.length) {
			lv = LOG_SEPARATOR_ARR.length - 1;
		}
		return LOG_SEPARATOR_ARR[lv];
	}
	int curLv;
	private LogManage(int lv) {
		this.curLv = lv;
	}
	public LogManage() {
		this(0);
	}
	public int getCurChar() {
		return getGlueCharByLevel(curLv);
	}
	public String[] splitString(String str) {
		if (str == null) {
			return new String[0];
		}
		return str.split(String.format("%c", getCurChar()));
	}
	public LogManage getNextLogManage() {
		return new LogManage(curLv+1);
	}
}
