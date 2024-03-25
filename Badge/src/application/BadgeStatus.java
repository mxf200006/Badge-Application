package application;

public enum BadgeStatus {
    ACTIVE("Active"),
    DEACTIVE("Deactive"),
    MISSING("Missing"),
    ON_HAND("On Hand"),
    IN_USE("In Use");
     
    private final String value;
    BadgeStatus(String value){
        this.value = value;   
    }
    public String getValue(){
        return this.value;
    }
    
    public static BadgeStatus getType(String s) {
    	for (BadgeStatus b: BadgeStatus.values()){
    		if(b.value.equalsIgnoreCase(s))
    			return b;
    	}
    	return null;
    }
}
