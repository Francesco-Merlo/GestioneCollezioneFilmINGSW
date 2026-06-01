package it.ingsw.progetto.command;

import it.ingsw.model.ElementiMultimediali;
import it.ingsw.progetto.dao.MediaDao;

public class AggiungiMediaCommand implements MediaCommand{
	
	private final MediaDao dao;
	private final ElementiMultimediali media;
	private boolean eseguito = false;
	
	public AggiungiMediaCommand(MediaDao dao, ElementiMultimediali media) {
		this.dao = dao;
		this.media = media;
	}

	@Override
	public boolean esegui() {
		if(!eseguito) {
			try {
				dao.salva(media);
				eseguito = true;
				return true;
			}catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean annulla() {
		if(!eseguito) {
			try {
				dao.elimina(media.getId());
				eseguito = false;
				return true;
			}catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public String getDescrizione() {
		return "Aggiunta: " + media.getTitolo();
	}
	
	
}//AggiungiMediaCommand