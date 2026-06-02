package it.ingsw.progetto.observer;

import java.util.List;

import it.ingsw.model.ElementiMultimediali;

/*
 * DESIGN PATTERN: OBSERVER
 * Siccome l'interfaccia grafica (View) deve aggiornarsi quando
 * i dati cambiano (aggiunta,modifica,eliminazione di un media).
 * Invece di accoppiare View e Model, usiamo l'Observer per
 * notificare i cambiamenti
 *
 */
public interface MediaObserver {
	
	//Chiamato quando la collezione di media viene modificata
	void onMediaCambia(List<ElementiMultimediali> mediaList);
	
	//chiamato quando un singolo elemento viene aggiunto
	void onMediaAggiungi(ElementiMultimediali media);
	
	//Chiamato quando un singolo elemento viene modificato
	void onMediaAggiorna(ElementiMultimediali media);
	
	//Chiamato quando un singolo elemento viene eliminato
	void onMediaElimina(String mediaId);
}//MediaObserver

