package it.ingsw.model;

/*
 * Enum per i genere cinematografico
 */
public enum Genere {
	AZIONE("Azione"),
	AVVENTURA("Avventura"),
	COMMEDIA("Commedia"),
	DRAMMA("Dramma"),
	FANTASCIENZA("Fantascienza"),
	FANTASY("Fantasy"),
	HORROR("Horror"),
	ROMANTICO("Romantico"),
	THRILLER("Thriller"),
	ANIMAZIONE("Animazione"),
	DOCUMENTARIO("Documentario"),
	ALTRO("Altro");
			
	private final String label;
	
	Genere(String label){
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static Genere fromString(String text) {
        if (text == null) 
        	return ALTRO;
        for (Genere genere : Genere.values()) {
            if (genere.name().equalsIgnoreCase(text) || genere.label.equalsIgnoreCase(text)) {
                return genere;
            }
        }
        return ALTRO;
    }
}
