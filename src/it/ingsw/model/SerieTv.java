package it.ingsw.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class SerieTv implements ElementiMultimediali{
	public static final String TIPO_CONTENUTO = "SERIE TV";
	
	private String id;
	private String titolo;
	private String regista;
	private int anno;
	private int valutazione;
	private LocalDateTime dataCreazione;
	private LocalDateTime dataModifica;
	private Genere genere;
	private StatoVisione statoVisione;
	private String posterUrl;
	private String note;
	private int durata; //in minuti per episodio
	
	//attributi che servono per le serie tv
	private int numStagioni;
	private int numEpisodi;
	private int stagioneCorrente;
	private int episodioCorrente;
	private boolean inCorso;
	
	private SerieTv(Builder builder) {
		if(builder.id == null) {
			this.id = UUID.randomUUID().toString();
		} else {
			this.id = builder.id;
		}
		this.titolo = builder.titolo;
		this.regista = builder.regista;
		this.anno = builder.anno;
		this.valutazione = builder.valutazione;
		this.posterUrl = builder.posterUrl;
		this.note = builder.note;
		this.durata = builder.durata;
		this.numStagioni = builder.numStagioni;
		this.numEpisodi = builder.numEpisodi;
		
		if(builder.genere == null) {
			this.genere = Genere.ALTRO;
		} else {
			this.genere = builder.genere;
		}
		if(builder.statoVisione == null) {
			this.statoVisione = StatoVisione.DA_VEDERE;
		} else {
			this.statoVisione = builder.statoVisione;
		}
		if(builder.dataCreazione == null) {
			this.dataCreazione = LocalDateTime.now();
		} else {
			this.dataCreazione = builder.dataCreazione;	
		}
		if(builder.dataModifica == null) {
			this.dataModifica = LocalDateTime.now();
		} else {
			this.dataModifica = builder.dataModifica;
		}
		this.stagioneCorrente = Math.max(0, builder.stagioneCorrente);
        this.episodioCorrente = Math.max(0, builder.episodioCorrente);
        this.inCorso = builder.inCorso;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private String id;
		private String titolo;
		private String regista;
		private int anno;
		private int valutazione;
		private LocalDateTime dataCreazione;
		private LocalDateTime dataModifica;
		private Genere genere;
		private StatoVisione statoVisione;
		private String posterUrl;
		private String note;
		private int durata; //in minuti per episodio
		
		//attributi che servono per le serie tv
		private int numStagioni;
		private int numEpisodi;
		private int stagioneCorrente;
		private int episodioCorrente;
		private boolean inCorso;
		
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		
		public Builder titolo(String titolo) {
			this.titolo = titolo;
			return this;
		}
		
		public Builder regista(String regista) {
			this.regista = regista;
			return this;
		}
		
		public Builder anno(int anno) {
			this.anno = anno;
			return this;
		}
		
		public Builder valutazione(int valutazione) {
			this.valutazione = valutazione;
			return this;
		}
		
		public Builder posterUrl(String posterUrl){
			this.posterUrl = posterUrl;
			return this;
		}
		
		public Builder note(String note){
			this.note = note;
			return this;
		}
		
		public Builder durata(int durata){
			this.durata = durata;
			return this;
		}
		
		public Builder genere(Genere genere) {
			this.genere = genere;
			return this;
		}
		
		public Builder statoVisione(StatoVisione statoVisione){
			this.statoVisione = statoVisione;
			return this;
		}
		
		public Builder dataCreazione(LocalDateTime dataCreazione){
			this.dataCreazione = dataCreazione;
			return this;
		}
		
		public Builder dataModifica(LocalDateTime dataModifica) {
			this.dataModifica = dataModifica;
			return this;
		}
		
		public Builder numStagioni(int numStagioni) {
			this.numStagioni = numStagioni;
			return this;
		}
		
		public Builder numEpisodi(int numEpisodi) {
			this.numEpisodi = numEpisodi;
			return this;
		}
		
		public Builder stagioneCorrente(int stagioneCorrente){
			this.stagioneCorrente = stagioneCorrente;
			return this;
		}
		
		public Builder episodioCorrente(int episodioCorrente) {
			this.episodioCorrente = episodioCorrente;
			return this;
		}
		
		public Builder inCorso(boolean inCorso) {
			this.inCorso = inCorso;
			return this;
		}
		public SerieTv build() {
			return new SerieTv(this);
		}
	}
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getTitolo() {
		return titolo;
	}

	@Override
	public String getRegista() {
		return regista;
	}

	@Override
	public int getAnno() {
		return anno;
	}

	@Override
	public int getValutazione() {
		return valutazione;
	}

	@Override
	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	@Override
	public LocalDateTime getDataModifica() {
		return dataModifica;
	}

	@Override
	public String getTipoContenuto() {
		return TIPO_CONTENUTO;
	}

	@Override
	public StatoVisione getStatoVisione() {
		return statoVisione;
	}

	@Override
	public String getPosterUrl() {
		return posterUrl;
	}

	@Override
	public String getNote() {
		return note;
	}
	
	public int getDurata() {
		return durata;
	}
	
	public Genere getGenere() {
		return genere;
	}
	
	public int getNumEpisodi() {
		return numEpisodi;
	}
	
	public int getNumStagioni() {
		return numStagioni;
	}
	
	public int getStagioneCorrente() {
		return stagioneCorrente;
	}
	
	public int getEpisodioCorrente() {
		return episodioCorrente;
	}
	
	public boolean isInCorso() {
		return inCorso;
	}

	@Override
	public void setTitolo(String titolo) {
		this.titolo = titolo;
		this.dataModifica = LocalDateTime.now();
	}

	@Override
	public void setValutazione(int valutazione) {
		//valutazione da 1 a 5 stelle usiamo  Math
		this.valutazione = Math.max(1, Math.min(5, valutazione));
		this.dataModifica = LocalDateTime.now();
	}

	@Override
	public void setStatoVisione(StatoVisione statoVisione) {
		this.statoVisione = statoVisione;
		this.dataModifica = LocalDateTime.now();
	}
	
	public void setRegista(String regista) { 
		this.regista = regista; 
		this.dataModifica = LocalDateTime.now(); 
	}
	
    public void setAnno(int anno) { 
    	this.anno = anno; 
    	this.dataModifica = LocalDateTime.now(); 
    }
    
    public void setGenere(Genere genere) { 
    	this.genere = genere; 
    	this.dataModifica = LocalDateTime.now(); 
    }
    
    public void setPosterUrl(String posterUrl) { 
    	this.posterUrl = posterUrl; 
    	this.dataModifica = LocalDateTime.now(); 
    }
    
    public void setNote(String note) { 
    	this.note = note; 
    	this.dataModifica = LocalDateTime.now(); 
    }
    public void setNumeroStagioni(int numStagioni) { 
    	this.numStagioni = numStagioni; 
    	this.dataModifica = LocalDateTime.now(); 
    }
    public void setEpisodiTotali(int numEpisodi) { 
    	this.numEpisodi = numEpisodi; 
    	this.dataModifica = LocalDateTime.now(); 
    }
    public void setStagioneCorrente(int stagioneCorrente) { 
    	this.stagioneCorrente = stagioneCorrente; 
    	this.dataModifica = LocalDateTime.now(); 
    }
    
    public void setEpisodioCorrente(int episodioCorrente) { 
    	this.episodioCorrente = episodioCorrente; 
    	this.dataModifica = LocalDateTime.now(); 
    }
    
    public void setInCorso(boolean inCorso) { 
    	this.inCorso = inCorso; 
    	this.dataModifica = LocalDateTime.now(); 
    }
    
	//METODI OBJECT
	
		@Override
		public boolean equals(Object o) {
			if(this == o)
				return true;
			if(o == null || getClass() != o.getClass())
				return false;
			SerieTv serieTv = (SerieTv) o;
			return Objects.equals(id, serieTv.id);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(id);
		}
		
		@Override
		public String toString() {
			return String.format("SerieTv[id=%s, titolo='%s', stagioni=%d, episodi=%d, st.Corrente=%d, ep.Corrente=%d, inCorso=%b, regista='%s', anno=%d, genere=%s, voto=%d, stato=%s]",
					id, titolo, numStagioni, numEpisodi, stagioneCorrente, episodioCorrente, inCorso, regista, anno, genere, valutazione, statoVisione);
		}
}//Serie tv

