package it.ingsw.progetto.factory;

import it.ingsw.model.ElementiMultimediali;

/*
 * Design Pattern: Abstract Factory
 * - Definisce il contratto per tutte le factory concrete e permette l'estensibilità(nuovi tipi di media) 
 */
public interface MediaFactory {
	
	//creo un elemento multimediale con parametri base
	ElementiMultimediali crea(String id, String titolo, String regista, int anno, int valutazione);
	
	String getTipoContenuto();
}//MediaFactory
