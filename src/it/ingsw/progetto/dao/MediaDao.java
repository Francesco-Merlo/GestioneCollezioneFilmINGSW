package it.ingsw.progetto.dao;
import java.util.List;
import java.util.Optional;

import it.ingsw.model.*;
public interface MediaDao {
	void salva(ElementiMultimediali media);
	
	Optional<ElementiMultimediali> trovaPerId(String id);
	
	List<ElementiMultimediali> trovaTutti();	
	
	void aggiorna(ElementiMultimediali media);
	
	void elimina(String id);
	
	/*Filtri di Ricerca*/
	
	List<ElementiMultimediali> filtraPerFilm(String titolo);
	
	List<ElementiMultimediali> filtraPerRegista(String regista);
	
	List<ElementiMultimediali> filtraPerGenere(Genere genere);
	
	List<ElementiMultimediali> filtraPerStatoVisione(StatoVisione statoVisione);
}//MediaDao