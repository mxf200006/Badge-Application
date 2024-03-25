package application;

public enum Regex {
	NUMBER("[0-9]{1,}"),
	LETTER("[A-Za-z]{1,}");
	
	private final String regex;
	private Regex(String regex){
        this.regex = regex;   
    }
    public String getRegex(){
        return this.regex;
    }
    
    public static Regex getType(String s) {
    	for (Regex r: Regex.values()){
    		if(r.regex.equalsIgnoreCase(s))
    			return r;
    	}
    	return null;
    }
	
}
