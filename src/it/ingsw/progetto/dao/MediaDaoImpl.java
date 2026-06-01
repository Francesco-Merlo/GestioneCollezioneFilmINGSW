package it.ingsw.progetto.dao;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import it.ingsw.model.*;
import it.ingsw.progetto.factory.*;

import java.sql.*;

public class MediaDaoImpl implements MediaDao{
	public final DatabaseManager dbManager = DatabaseManager.getInstance();
	
	//Costruttore pubblico cosi il Controller può istanziarlo
	public MediaDaoImpl() {
		
	}
	
	@Override
	public void salva(ElementiMultimediali media) {
		String sql = """
				INSERT INTO media (
					id, tipo, titolo, regista, anno, valutazione, statoVisione, 
					posterUrl, note, genere, durata, numStagioni, numEpisodi, 
					stagioneCorrente, episodioCorrente, inCorso, 
					soggettoPrincipale, piattaforma, sottogenere, 
					dataCreazione, dataModifica
				) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";
		
		try(Connection conn = dbManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			// 1. Campi Comuni (Tutti gli elementi li hanno)
			pstmt.setString(1, media.getId());
			pstmt.setString(2, media.getTipoContenuto());
			pstmt.setString(3, media.getTitolo());
			pstmt.setString(4, media.getRegista());
			pstmt.setInt(5, media.getAnno());
			pstmt.setInt(6, media.getValutazione());
			pstmt.setString(7, media.getStatoVisione().name());
			pstmt.setString(8, media.getPosterUrl());
			pstmt.setString(9, media.getNote());
						
			// Il genere potrebbe essere null, lo gestiamo
			if(media.getGenere() != null) {
				pstmt.setString(10, media.getGenere().name());
			} else {
				pstmt.setString(10, Genere.ALTRO.name());
			}

			// 2. Inizializziamo a NULL o 0 i campi specifici (li riempiamo dopo se serve)
			pstmt.setObject(11, null); // durata
			pstmt.setObject(12, null); // numStagioni
			pstmt.setObject(13, null); // numEpisodi
			pstmt.setObject(14, null); // stagioneCorrente
			pstmt.setObject(15, null); // episodioCorrente
			pstmt.setObject(16, null); // inCorso
			pstmt.setObject(17, null); // soggettoPrincipale
			pstmt.setObject(18, null); // piattaforma
			pstmt.setObject(19, null); // sottogenere
						
			// 3. Controlliamo di che tipo è l'oggetto e riempiamo i campi specifici!
			if (media instanceof Film film) {
					pstmt.setInt(11, film.getDurata());
					
			}else if (media instanceof SerieTv serie) {
				pstmt.setInt(12, serie.getNumStagioni());
				pstmt.setInt(13, serie.getNumEpisodi());
				pstmt.setInt(14, serie.getStagioneCorrente());
				pstmt.setInt(15, serie.getEpisodioCorrente());
				pstmt.setBoolean(16, serie.isInCorso());
				
			} else if (media instanceof Documentario doc) {
				pstmt.setString(17, doc.getSoggettoPrincipale());
				pstmt.setString(18, doc.getPiattaforma());
				pstmt.setString(19, doc.getSottogenere() != null ? doc.getSottogenere().name() : Documentario.Sottogenere.ALTRO.name());
				pstmt.setInt(11, doc.getDurata());
			}

			// 4. Timestamp di sistema per creazione e modifica
			pstmt.setTimestamp(20, Timestamp.valueOf(media.getDataCreazione()));
			pstmt.setTimestamp(21, Timestamp.valueOf(media.getDataModifica()));

						// Eseguiamo il salvataggio!
						pstmt.executeUpdate();
		}catch (SQLException e) {
			throw new RuntimeException("Errore durante il salvataggio del media" + media.getTitolo(), e);
		}
	}

	@Override
	public Optional<ElementiMultimediali> trovaPerId(String id) {		
		String sql = "SELECT * FROM media WHERE id = ?";
		try(Connection conn = dbManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1,id);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					return Optional.ofNullable(estraiMediaDaResultSet(rs));
				}
			}
			return Optional.empty();
		}catch (SQLException e) {
			throw new RuntimeException("Errore ricerca per ID: " + id, e);
		}
	}
	
	//definiamo il metodo estraiMediaDaResultSet
	private ElementiMultimediali estraiMediaDaResultSet(ResultSet rs) throws SQLException {
		String id = rs.getString("id");
		String tipo = rs.getString("tipo");
		String titolo = rs.getString("titolo");
		String regista = rs.getString("regista");
		int anno = rs.getInt("anno");
		int valutazione = rs.getInt("valutazione");

		ElementiMultimediali media = null;

		// 1. Usiamo la Factory corretta in base alla stringa "tipo"
		switch (tipo.toUpperCase()) {
			case "FILM":
				media = FilmFactory.getInstance().crea(id, titolo, regista, anno, valutazione);
				break;
			case "SERIE TV":
				media = SerieTvFactory.getInstance().crea(id, titolo, regista, anno, valutazione);
				break;
			case "DOCUMENTARIO":
				media = DocumentarioFactory.getInstance().crea(id, titolo, regista, anno, valutazione);
				break;
			default:
				System.err.println("Tipo sconosciuto nel DB: " + tipo);
				return null;
		}

		// 2. Aggiungiamo i campi comuni
		media.setStatoVisione(StatoVisione.fromString(rs.getString("statoVisione")));
		media.setGenere(Genere.fromString(rs.getString("genere")));
		media.setPosterUrl(rs.getString("posterUrl"));
		media.setNote(rs.getString("note"));

		// 3. Aggiungiamo i campi specifici con i cast
		if (media instanceof Film film) {
			film.setDurata(rs.getInt("durata"));
		} 
		else if (media instanceof SerieTv serie) {
			serie.setNumeroStagioni(rs.getInt("numStagioni"));
			serie.setEpisodiTotali(rs.getInt("numEpisodi"));
			serie.setStagioneCorrente(rs.getInt("stagioneCorrente"));
			serie.setEpisodioCorrente(rs.getInt("episodioCorrente"));
			serie.setInCorso(rs.getBoolean("inCorso"));
		} 
		else if (media instanceof Documentario doc) {
			doc.setDurata(rs.getInt("durata"));
			doc.setSoggettoPrincipale(rs.getString("soggettoPrincipale"));
			doc.setPiattaforma(rs.getString("piattaforma"));
			doc.setSottogenere(Documentario.Sottogenere.fromString(rs.getString("sottogenere")));
		}

		return media;
	}
	@Override
	public List<ElementiMultimediali> trovaTutti() {
		List<ElementiMultimediali> lista = new ArrayList<>();
		String sql = "SELECT * FROM media ORDER BY dataCreazione DESC";
		try(Connection conn = dbManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			while(rs.next()) {
				//per ogni riga del database, usiamo il metodo per creare l'oggetto
				ElementiMultimediali media = estraiMediaDaResultSet(rs);
				if(media != null)
					lista.add(media);
			}
		}catch(SQLException e) {
			throw new RuntimeException("Errore durante il recupero di tutti i media", e);
		}
		return lista;
	}

	@Override
	public void aggiorna(ElementiMultimediali media) {
		String sql = """
				UPDATE media SET 
				titolo=?, regista=?, anno=?, valutazione=?, statoVisione=?, 
				posterUrl=?, note=?, genere=?, durata=?, numStagioni=?, 
				numEpisodi=?, stagioneCorrente=?, episodioCorrente=?, inCorso=?, 
				soggettoPrincipale=?, piattaforma=?, sottogenere=?, dataModifica=?
				WHERE id = ?
				""";
		try (Connection conn = dbManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			// Campi comuni
			pstmt.setString(1, media.getTitolo());
			pstmt.setString(2, media.getRegista());
			pstmt.setInt(3, media.getAnno());
			pstmt.setInt(4, media.getValutazione());
			pstmt.setString(5, media.getStatoVisione().name());
			pstmt.setString(6, media.getPosterUrl());
			pstmt.setString(7, media.getNote());
			pstmt.setString(8, media.getGenere() != null ? media.getGenere().name() : Genere.ALTRO.name());
			
			// Reset dei campi specifici a NULL prima di riempirli
			pstmt.setObject(9, null); pstmt.setObject(10, null); pstmt.setObject(11, null);
			pstmt.setObject(12, null); pstmt.setObject(13, null); pstmt.setObject(14, null);
			pstmt.setObject(15, null); pstmt.setObject(16, null); pstmt.setObject(17, null);
			
			// Campi specifici
			if (media instanceof Film film) {
				pstmt.setInt(9, film.getDurata());
			} else if (media instanceof SerieTv serie) {
				pstmt.setInt(10, serie.getNumStagioni());
				pstmt.setInt(11, serie.getNumEpisodi());
				pstmt.setInt(12, serie.getStagioneCorrente());
				pstmt.setInt(13, serie.getEpisodioCorrente());
				pstmt.setBoolean(14, serie.isInCorso());
			} else if (media instanceof Documentario doc) {
				pstmt.setInt(9, doc.getDurata());
				pstmt.setString(15, doc.getSoggettoPrincipale());
				pstmt.setString(16, doc.getPiattaforma());
				pstmt.setString(17, doc.getSottogenere() != null ? doc.getSottogenere().name() : Documentario.Sottogenere.ALTRO.name());
			}
			
			// Timestamp modifica e ID per la clausola WHERE
			pstmt.setTimestamp(18, Timestamp.valueOf(java.time.LocalDateTime.now()));
			pstmt.setString(19, media.getId());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Errore aggiornamento media: " + media.getTitolo(), e);
		}
	}

	@Override
	public void elimina(String id) {
		String sql = "DELETE FROM media WHERE id = ?";
		try (Connection conn = dbManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Errore eliminazione media con ID: " + id, e);
		}
	}

	@Override
	public List<ElementiMultimediali> filtraPerFilm(String titolo) {
		// Uso LOWER e LIKE per fare una ricerca flessibile (es. se cercando "bat", troviamo "Batman")
		return eseguiRicercaFiltro("SELECT * FROM media WHERE LOWER(titolo) LIKE ?", "%" + titolo.toLowerCase() + "%");
	}

	@Override
	public List<ElementiMultimediali> filtraPerRegista(String regista) {
		return eseguiRicercaFiltro("SELECT * FROM media WHERE LOWER(regista) LIKE ?", "%" + regista.toLowerCase() + "%");
	}

	@Override
	public List<ElementiMultimediali> filtraPerGenere(Genere genere) {
		return eseguiRicercaFiltro("SELECT * FROM media WHERE genere = ?", genere.name());
	}

	@Override
	public List<ElementiMultimediali> filtraPerStatoVisione(StatoVisione statoVisione) {
		return eseguiRicercaFiltro("SELECT * FROM media WHERE statoVisione = ?", statoVisione.name());
	}
	
	private List<ElementiMultimediali> eseguiRicercaFiltro(String sql, String parametro) {
		List<ElementiMultimediali> risultati = new ArrayList<>();
		try (Connection conn = dbManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, parametro);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ElementiMultimediali media = estraiMediaDaResultSet(rs);
					if (media != null) risultati.add(media);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Errore durante la ricerca/filtro", e);
		}
		return risultati;
	}

}//MediaDaoImpl