package server.gachapon;

public enum GachaponLocation {
	HENESYS(9100100),
	PERION(9100102),
	KERNING(9100103),
	MUSHROM_SHRINE(9100105),
	ELLINIA(9100101),
	NLC(9100109),
	SLEEPYWOOD(9100104),
	NAUTILUS(9100117),
	GENERIC(0);
	
	private int location;
	
	GachaponLocation(int location){
		this.location = location;
	}

	public int getValue() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}
	
	public static GachaponLocation valueOf(int location){
		for(GachaponLocation l : GachaponLocation.values()){
			if(l.getValue() == location){
				return l;
			}
		}
		throw new IllegalArgumentException("Invalid location: " + location);
	}
	
	
	
}
