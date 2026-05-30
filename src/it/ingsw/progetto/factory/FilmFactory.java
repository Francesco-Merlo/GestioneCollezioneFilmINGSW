package it.ingsw.progetto.factory;

import it.ingsw.model.*;

public class FilmFactory implements MediaFactory{
	private static FilmFactory instance;
	
	public static FilmFactory getInstance() {
		if(instance == null) {
			//così facendo può funzionare anche in ambiente multi thread
			synchronized(FilmFactory.class) {
				if(instance == null)
					instance = new FilmFactory();
			}
		}
		return instance;
	}
	
	private FilmFactory() {}
	
	@Override
	public ElementiMultimediali crea(String id, String titolo, String regista, int anno, int valutazione) {
		return Film.builder()
				.id(id)
				.titolo(titolo)
				.regista(regista)
				.anno(anno)
				.valutazione(valutazione)
				.build();
	}

	@Override
	public String getTipoContenuto() {
		return Film.TIPO_CONTENUTO;
	}
	
	//creiamo un film vuoto per il form di inserimento
	public Film creaVuoto() {
		return Film.builder().build();
	}
}//FilmFactory