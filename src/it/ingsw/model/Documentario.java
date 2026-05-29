package it.ingsw.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Documentario implements ElementiMultimediali{
	public static final String TIPO_CONTENUTO = "DOCUMENTARIO";
	
	// Sottogeneri specifici per documentari
	public enum Sottogenere {
		NATURA("Natura"), 
		STORIA("Storia"), 
		SCIENZA("Scienza"),
		TRUE_CRIME("True Crime"), 
		BIOGRAFIA("Biografia"), 
		SOCIALE("Sociale"),
		SPORT("Sport"), 
		MUSICA("Musica"), 
		ARTE("Arte"),
		TECNOLOGIA("Tecnologia"), 
		VIAGGI("Viaggi"), 
		ALTRO("Altro");
		
		private final String label;
		Sottogenere(String label) { this.label = label; }
		public String getLabel() { return label; }
		
		public static Sottogenere fromString(String text) {
			if (text == null) return ALTRO;
			for (Sottogenere s : values()) {
				if (s.name().equalsIgnoreCase(text) || s.label.equalsIgnoreCase(text)) return s;
			}
			return ALTRO;
		}
	}
	
	private String id;
	private String titolo;
	private String regista;
	private int anno;
	private int valutazione;
	private LocalDateTime dataCreazione;
	private LocalDateTime dataModifica;
	
	//genere generico (dall'interfaccia)
	private Genere genere;
	
	private StatoVisione statoVisione;
	private String posterUrl;
	private String note;
	private int durata; //in minuti
	
	//attributi aggiuntivi specifici per Documentario
	private String soggettoPrincipale;
	private String piattaforma;
	private Sottogenere sottogenere;
	
	private Documentario(Builder builder) {
		if(builder.id == null) {
			this.id = UUID.randomUUID().toString();
		}else {
			this.id = builder.id;
		}
		this.titolo = builder.titolo;
		this.regista = builder.regista;
		this.anno = builder.anno;
		this.valutazione = builder.valutazione;
		this.posterUrl = builder.posterUrl;
		this.note = builder.note;
		this.durata = builder.durata;
		this.soggettoPrincipale = builder.soggettoPrincipale;
		this.piattaforma = builder.piattaforma;
		
		if(builder.genere == null) {
			this.genere = Genere.ALTRO;
		}else {
			this.genere = builder.genere;
		}
		if(builder.sottogenere == null) {
			this.sottogenere = Sottogenere.ALTRO;
		}else {
			this.sottogenere = builder.sottogenere;
		}
		if(builder.statoVisione == null) {
			this.statoVisione = StatoVisione.DA_VEDERE;
		}else {
			this.statoVisione = builder.statoVisione;
		}
		if(builder.dataCreazione == null) {
			this.dataCreazione = LocalDateTime.now();
		}else {
			this.dataCreazione = builder.dataCreazione;	
		}
		if(builder.dataModifica == null) {
			this.dataModifica = LocalDateTime.now();
		}else {
			this.dataModifica = builder.dataModifica;
		}
	}//costruttore
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder{
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
		private int durata; //in minuti
		private String soggettoPrincipale;
		private String piattaforma;
		private Sottogenere sottogenere;
		
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
		
		public Builder soggettoPrincipale(String soggettoPrincipale) {
			this.soggettoPrincipale = soggettoPrincipale;
			return this;
		}
		
		public Builder piattaforma(String piattaforma) {
			this.piattaforma = piattaforma;
			return this;
		}
		
		public Builder sottogenere(Sottogenere sottogenere) {
			this.sottogenere = sottogenere;
			return this;
		}
		
		public Documentario build() {
			return new Documentario(this);
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
	
	public String getSoggettoPrincipale() {
		return soggettoPrincipale;
	}
	
	public String getPiattaforma() {
		return piattaforma;
	}
	
	public Sottogenere getSottogenere() {
		return sottogenere;
	}
	
	public String getDurataFormattata() {
		int ore = durata / 60;
		int minuti = durata % 60;
		if(ore > 0) {
			return String.format("%dh %dmin", ore, minuti);
		}else {
			return String.format("%dmin", minuti);
		}
	}

	@Override
	public void setTitolo(String titolo) {
		this.titolo = titolo;
		this.dataModifica = LocalDateTime.now();
	}

	@Override
	public void setValutazione(int valutazione) {
		//valutazione da 1 a 5 stelle usiamo Math
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
	
	public void setDurata(int durata) {
		this.durata = durata;
		this.dataModifica = LocalDateTime.now();
	}
	
	public void setSoggettoPrincipale(String soggettoPrincipale) {
		this.soggettoPrincipale = soggettoPrincipale;
		this.dataModifica = LocalDateTime.now();
	}
	
	public void setPiattaforma(String piattaforma) {
		this.piattaforma = piattaforma;
		this.dataModifica = LocalDateTime.now();
	}
	
	public void setSottogenere(Sottogenere sottogenere) {
		this.sottogenere = sottogenere;
		this.dataModifica = LocalDateTime.now();
	}
	
	//METODI OBJECT
	
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		Documentario documentario = (Documentario) o;
		return Objects.equals(id, documentario.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public String toString() {
		return String.format("Documentario[id=%s, titolo='%s', soggetto='%s', sottogenere='%s', piattaforma='%s', regista='%s', anno=%d, genere=%s, voto=%d, stato=%s]",
				id, titolo, soggettoPrincipale, sottogenere, piattaforma, regista, anno, genere, valutazione, statoVisione);
	}
}//Documentario
