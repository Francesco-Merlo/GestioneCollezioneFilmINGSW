package it.ingsw.progetto.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import it.ingsw.model.ElementiMultimediali;

public class MediaObservable{
	
	//Usiamo copyOnWriteArrayList per evitare errori quando aggiungiamo/rimuoviamo observer mentre stiamo notificando
	private final List<MediaObserver> observers = new CopyOnWriteArrayList<>();
	
	//metodo per iscriversi agli aggiornamenti
	public void aggiungiObserver(MediaObserver observer) {
		if(observer != null && !observers.contains(observer))
			observers.add(observer);
	}
	
	//Metodo per disiscriversi
	public void rimuoviObserver(MediaObserver observer) {
		observers.remove(observer);
	}
	
	//Notifica tutti gli observer di un cambiamento generale
	public void notificaCambiamentoMedia(List<ElementiMultimediali> mediaList) {
		for(MediaObserver observer : observers) {
			observer.onMediaCambia(mediaList);
		}
	}
	
	// Notifica tutti gli observer di una nuova aggiunta
		public void notificaAggiuntaMedia(ElementiMultimediali media) {
			for(MediaObserver observer : observers) {
				observer.onMediaAggiungi(media);
			}
		}
	
	//notifica tutti gli observer di una modifica
	public void notificaAggiornamentoMedia(ElementiMultimediali media) {
		for(MediaObserver observer : observers) {
			observer.onMediaAggiorna(media);
		}
	}
	
	//notifica tutti gli observer di un'eliminazione
	public void notificaEliminazioneMedia(String mediaId) {
		for(MediaObserver observer : observers) {
			observer.onMediaElimina(mediaId);
		}
	}
	
	//Numero observer registrati
	public int getObserversConteggio() {
		return observers.size();
	}
}//MediaObservable