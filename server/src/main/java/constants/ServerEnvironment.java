package constants;

public class ServerEnvironment {

	public static boolean debug;
	
	
	private ServerEnvironment(){
		
	}
	
	
	public static void loadEnvironment(){
		String str = System.getProperty("debug");
    	if(str != null && !str.isEmpty()){
    		debug = true;
    	}
	}
	
	
	public static boolean isDebugEnabled(){
		return debug;
	}


	public static boolean isSkillSavingEnabled() {
		return false;
	}
}
