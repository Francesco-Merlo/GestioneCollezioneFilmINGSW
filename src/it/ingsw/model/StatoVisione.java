package it.ingsw.model;

/*
 *Enum per lo stato di visione. 
 */
public enum StatoVisione {
	VISTO("Visto"),
	DA_VEDERE("Da vedere"),
	IN_VISIONE("In visione");
	
	private final String label;
	
	StatoVisione(String label){
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static StatoVisione fromString(String text) {
		if(text == null)
			return DA_VEDERE;
		for(StatoVisione sv : StatoVisione.values()) {
			if(sv.name().equalsIgnoreCase(text) || sv.label.equalsIgnoreCase(text)) {
				return sv;
			}
		}
		return DA_VEDERE;
	}
}//StatoVisione