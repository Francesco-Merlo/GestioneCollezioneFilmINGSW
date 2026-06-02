package it.ingsw.progetto.controller;

import java.util.List;

import it.ingsw.model.ElementiMultimediali;
import it.ingsw.progetto.command.CommandCronologia;
import it.ingsw.progetto.command.MediaCommand;
import it.ingsw.progetto.command.AggiungiMediaCommand;
import it.ingsw.progetto.dao.MediaDao;
import it.ingsw.progetto.dao.MediaDaoImpl;
import it.ingsw.progetto.observer.MediaObservable;
import it.ingsw.progetto.observer.MediaObserver;

/*
 * Design pattern: Facade
 * vado a crearlo visto che semplifica l'accesso al sistema per la view
 * 
 */
public class MediaController {
	
	private final MediaDao dao;
	private final CommandCronologia commandCronologia;
	private final MediaObservable observable;
	
	public MediaController() {
		this.dao = new MediaDaoImpl();
		this.commandCronologia = new CommandCronologia();
		this.observable = new MediaObservable();
	}
	
	// Gestione Observer
	public void registraObserver(MediaObserver observer) {
		observable.aggiungiObserver(observer);
	}
	
	public void aggiungiNuovoMedia(ElementiMultimediali media) {
		MediaCommand comando = new AggiungiMediaCommand(dao, media);
		if(commandCronologia.eseguiCommand(comando)) {
			observable.notificaAggiuntaMedia(media);
			observable.notificaCambiamentoMedia(dao.trovaTutti());
		}
	}
	
	public void annullaUltimaAzione() {
        MediaCommand comandoAnnullato = commandCronologia.annulla();
        if (comandoAnnullato != null) {
            // Ricarichiamo la lista completa dopo l'annulla
            observable.notificaCambiamentoMedia(dao.trovaTutti());
        }
    }

    public List<ElementiMultimediali> getCollezioneCompleta() {
        return dao.trovaTutti();
    }
    
    public boolean puoAnnullare() {
        return commandCronologia.canUndo();
    }
}//MediaController
