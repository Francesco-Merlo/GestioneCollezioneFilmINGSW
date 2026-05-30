package it.ingsw.progetto.factory;

import it.ingsw.model.*;

public class DocumentarioFactory implements MediaFactory{
	private static DocumentarioFactory instance;
	
	public static DocumentarioFactory getInstance() {
		if(instance == null) {
			//così facendo può funzionare anche in ambiente multi thread
			synchronized(DocumentarioFactory.class) {
				if(instance == null)
					instance = new DocumentarioFactory();
			}
		}
		return instance;
	}
	
	private DocumentarioFactory() {}

	@Override
	public ElementiMultimediali crea(String id, String titolo, String regista, int anno, int valutazione) {
		return Documentario.builder()
				.id(id)
				.titolo(titolo)
				.regista(regista)
				.anno(anno)
				.valutazione(valutazione)
				.build();
	}

	@Override
	public String getTipoContenuto() {
		return Documentario.TIPO_CONTENUTO;
	}
	
	public Documentario creaVuoto() {
		return Documentario.builder().build();
	}
}//DocumentarioFactory