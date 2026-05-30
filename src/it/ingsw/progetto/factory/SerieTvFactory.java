package it.ingsw.progetto.factory;

import it.ingsw.model.*;

public class SerieTvFactory implements MediaFactory{
	private static SerieTvFactory instance;
	
	public static SerieTvFactory getInstance() {
		if(instance == null) {
			//così facendo può funzionare anche in ambiente multi thread
			synchronized(SerieTvFactory.class) {
				if(instance == null)
					instance = new SerieTvFactory();
			}
		}
		return instance;
	}
	
	private SerieTvFactory() {}
	@Override
	public ElementiMultimediali crea(String id, String titolo, String regista, int anno, int valutazione) {
		return SerieTv.builder()
				.id(id)
				.titolo(titolo)
				.regista(regista)
				.anno(anno)
				.valutazione(valutazione)
				.build();
	}

	@Override
	public String getTipoContenuto() {
		return SerieTv.TIPO_CONTENUTO;
	}
	
	public SerieTv creaVuoto() {
		return SerieTv.builder().build();
	}
}//SerieTvFactory