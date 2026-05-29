package it.ingsw.model;
import java.time.LocalDateTime;

/*Interfaccia base per tutti gli elementi della collezione
 * Sarà il tipo di ritorno utilizzato dal nostro Factory Pattern
 * - Definisce il contratto comune per Film, Serie Tv, Documentario.
 * - Permette polimorfismo ed estensibilità (Anime, Cortometraggi futuri ecc)
 */
public interface ElementiMultimediali {
	//return ID univoco dell'elemento
	String getId();
	
	//return Titolo dell'opera
	String getTitolo();
	
	//return Regista/Creatore dell'opera
	String getRegista();
	
	//return anno di uscita/inizio
	int getAnno();
	
	//return valutazione personale (da 1 a 5 stelle)
	int getValutazione();
	
	//usiamo l'oggetto LocalDateTime per memorizzare l'istante in cui l'utente ha inserito/modificato 
	LocalDateTime getDataCreazione();
	LocalDateTime getDataModifica();
	
	//return Tipo di contenuto (FILM, SERIE TV, DOCUMENTARIO)
	String getTipoContenuto();
	
	//return Stato di visione (VISTO, DA VEDERE, IN VISIONE)
	StatoVisione getStatoVisione();
	
	//return URL del poster/locandina (può essere null)
	String  getPosterUrl();
	
	//return note personali (può essere null)
	String getNote();
	
	//imposta il titolo e aggiorna dataModifica
	void setTitolo(String titolo);
	
	//imposta valutazione (da 1 a 5 stelle) e aggiorna dataModifica
	void setValutazione(int valutazione);
	
	//imposta stato di visione e aggiorna dataModifica
	void setStatoVisione(StatoVisione statoVisione);
	
	void setGenere(Genere genere);
 
    void setPosterUrl(String posterUrl);
    
    void setNote(String note);
    
	//rappresentazione testuale dell'oggetto
	String toString();

	Genere getGenere();
	
}//ElementiMultimediali