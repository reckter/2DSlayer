package me.reckter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 9/24/13
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Log {

	public static final int DEBUG = 10;
	public static final int VERBOSE = 9;
	public static final int SUCCESS = 8;
	public static final int DETAIL = 7;
	public static final int NOTE = 6;
	public static final int INFO = 5;
	public static final int ANNOTATION = 4;
	public static final int STATUS = 3;
	public static final int IMPORANT = 2;
	public static final int ERROR = 1;

	public static final int DEFAULT_LEVEL = INFO;


	public static final String[] LEVEL_NAMES = {"", "ERROR", "IMPORTANT", "STATUS", "ANNOTATION", "INFO", "NOTES", "DETAILS", "SUCCESS", "VERBOSE", "DEBUG"};
	public static int VERBOSE_LEVEL = DEFAULT_LEVEL; // 10 is all outputs, 1 as quite as possible

	/**
	 *  Sets the Console level
	 * @param level the level
	 */
	public static void setConsoleLevel(int level){
		if(level < 1 || level >= LEVEL_NAMES.length){
			throw new IllegalArgumentException("level has to be > 0 and < " + LEVEL_NAMES.length);
		}
		VERBOSE_LEVEL = level;
	}


	/**
	 * a clear way of printing something on the console
	 * @param module The module which wants to print something
	 * @param title The title of the print ( reason ) [example: "DEBUG"]
	 * @param text The text that contains the message of the print
	 * @param level the verbose level of the message
	 */
	public static void log(String module, String title, String text, int level) {
		if(level < 1 || level >= LEVEL_NAMES.length){
			throw new IllegalArgumentException("level has to be > 0 and < " + LEVEL_NAMES.length);
		}

		if(level <= VERBOSE_LEVEL) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String time = sdf.format(new Date());
			System.out.println("[" + time + "][" + module + "][" + title + "][" + LEVEL_NAMES[level] + "]: " + text);
		}
	}


	/**
	 * Usses the default Verbose level (INFO)
	 * @param module The module which wants to print something
	 * @param title The title of the print ( reason ) [example: "DEBUG"]
	 * @param text The text that contains the message of the print
	 * @see #log(String, String, String, int)
	 */
	public static void log(String module, String title, String text){
		log(module, title, text,DEFAULT_LEVEL);
	}


	/**
	 * Print a message to the console and to a log. Guess the module and the title from a stack trace
	 * @param level The verbose lvl of the message
	 * @param text The message
	 * @see #log(String, String, String, int)
	 */
	public static void log(int level, String text){
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		String classname = "";
		String methodname = "";
		for(int i = 1; i < trace.length; i++) {
			if(!trace[i].getMethodName().toLowerCase().equals("log")) {
				classname = trace[i].getClassName();
				methodname = trace[i].getMethodName();
				break;
			}
		}
		log(classname, methodname, text, level);
	}

	/**
	 * Usses the default lvl to display the message
	 * @param text the message
	 * @see #log(int, String)
	 */
	public static void log(String text) {
		log(DEFAULT_LEVEL, text);
	}


	/**
	 * calls log with DEBUG level
	 * @param text the message
	 */
	public static void debug(String text) {
		log(DEBUG, text);
	}

	/**
	 * calls log with VERBOSE level
	 * @param text the message
	 */
	public static void verbose(String text) {
		log(VERBOSE, text);
	}

	/**
	 * calls log with SUCCESS level
	 * @param text the message
	 */
	public static void success(String text) {
		log(SUCCESS, text);
	}

	/**
	 * calls log with DETAIL level
	 * @param text the message
	 */
	public static void detail(String text) {
		log(DETAIL, text);
	}

	/**
	 * calls log with NOTE level
	 * @param text the message
	 */
	public static void note(String text) {
		log(NOTE, text);
	}

	/**
	 * calls log with INFO level
	 * @param text the message
	 */
	public static void info(String text) {
		log(INFO, text);
	}

	/**
	 * calls log with ANNOTATION level
	 * @param text the message
	 */
	public static void annotation(String text) {
		log(ANNOTATION, text);
	}

	/**
	 * calls log with STATUS level
	 * @param text the message
	 */
	public static void status(String text) {
		log(STATUS, text);
	}

	/**
	 * calls log with IMPORANT level
	 * @param text the message
	 */
	public static void important(String text) {
		log(IMPORANT, text);
	}

	/**
	 * calls log with ERROR level
	 * @param text the message
	 */
	public static void error(String text) {
		log(ERROR, text);
	}
}
