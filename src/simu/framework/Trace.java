package simu.framework;

public class Trace {

	public enum Level{INFO, WAR, ERR}
	
	private static Level traceLevel;
	private static boolean showOriginComponent;
	
	public static void setTraceLevel(Level lvl, boolean showOriginComponent){
		traceLevel = lvl;
		Trace.showOriginComponent = showOriginComponent;
	}
	public static void out(Level lvl, String sender, String txt){
		if (lvl.ordinal() >= traceLevel.ordinal()){
			if(showOriginComponent) {
				System.out.println(sender + txt);
			} else {
				System.out.println(txt);
			}
		}
	}
	
	
	
}