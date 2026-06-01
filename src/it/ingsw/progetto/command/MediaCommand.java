package it.ingsw.progetto.command;

/*
 * DESIGN PATTERN: COMMAND
 * Vado a definire il contratto per tutte le operazioni
 * che vanno a modificare la collezione (Aggiunta, Modifica, Eliminazione)
 * 
 */
public interface MediaCommand {
	//Esegue l'operazione (Es: salva nel DB)
	boolean esegui();
	
	//Annulla operazione (Es: elimina dal DB quello che aveva appena salvato)
	boolean annulla();
	
	//Ritorna una descrizione utile per la UI
	String getDescrizione();
}
