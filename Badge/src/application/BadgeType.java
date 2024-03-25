package application;

public enum BadgeType {
    EMPLYEE("Employee"),
    STUDENT("Student"),
    VISITOR("Visitor"),
    CONTRACTORS("Contractor"),
    IT("IT"),
    WAREHOUSE("Warehouse"),
    SECURITS("Security"),
    OTHER("Other");
     
    private final String value;
    
    private BadgeType(String value){
        this.value = value;   
    }
    public String getValue(){
        return this.value;
    }
    public static BadgeType getType(String s) {
    	for (BadgeType b: BadgeType.values()){
    		if(b.value.equalsIgnoreCase(s))
    			return b;
    	}
    	return null;
    }
}
