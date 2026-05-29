package it.ingsw.model;

/**
 * Enum per il tipo di contenuto multimediale.
 * Usato dal DAO per distinguere i record nel database.
 */
public enum TipoContenuto {
    FILM("Film"),
    SERIE_TV("Serie TV"),
    DOCUMENTARIO("Documentario");
    
    private final String label;
    
    TipoContenuto(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
    
    public static TipoContenuto fromString(String text) {
        if (text == null) return FILM;
        for (TipoContenuto tipo : TipoContenuto.values()) {
            if (tipo.name().equalsIgnoreCase(text) || tipo.label.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        return FILM;
    }
}//TipoContenuto