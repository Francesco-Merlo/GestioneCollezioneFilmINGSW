package it.ingsw.progetto.dao;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
    
    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());
    private static volatile DatabaseManager instance;
    
    private final String dbUrl;
    private final String dbUser = "sa";
    private final String dbPassword = "";
    
    private DatabaseManager() {
        Path dbPath = Paths.get(System.getProperty("user.home"), ".gestioneCollezione", "database");
        dbPath.getParent().toFile().mkdirs();
        this.dbUrl = "jdbc:h2:" + dbPath.toString() + ";AUTO_SERVER=TRUE";
        
        initializeDatabase();
    }
    
    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
    
    /**
     * Inizializza lo schema del database.
     * Tabella unificata 'media' con colonna 'tipo'.
     */
    private void initializeDatabase() {
    	
        // Tabella unificata per tutti i tipi di media
        String createMediaTable = """
            CREATE TABLE IF NOT EXISTS media (
                id VARCHAR(36) PRIMARY KEY,
                tipo VARCHAR(20) NOT NULL,
                titolo VARCHAR(300) NOT NULL,
                regista VARCHAR(200) NOT NULL,
                anno INT NOT NULL,
                valutazione INT NOT NULL CHECK (valutazione >= 1 AND valutazione <= 5),
                statoVisione VARCHAR(20) NOT NULL,
                posterUrl VARCHAR(500),
                note VARCHAR(2000),
                genere VARCHAR(50),
                durata INT,
                
                -- Attributi specifici Serie TV
                numStagioni INT,
                numEpisodi INT,
                stagioneCorrente INT,
                episodioCorrente INT,
                inCorso BOOLEAN,
                
                -- Attributi specifici Documentario
                soggettoPrincipale VARCHAR(300),
                piattaforma VARCHAR(100),
                sottogenere VARCHAR(50),
                
                -- Timestamp
                dataCreazione TIMESTAMP NOT NULL,
                dataModifica TIMESTAMP NOT NULL
            )
            """;
        
        String createIndexTipo = """
        		CREATE INDEX IF NOT EXISTS idx_media_tipo ON media(tipo)
        		""";
        
        String createIndexTitolo = """
        		CREATE INDEX IF NOT EXISTS idx_media_titolo ON media(titolo)
        		""";
        
        String createIndexStato = """
        		CREATE INDEX IF NOT EXISTS idx_media_stato ON media(statoVisione)
        		""";
        
        String createIndexGenere = """
        		 CREATE INDEX IF NOT EXISTS idx_media_genere ON media(genere)
        		""";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(createMediaTable);
            stmt.execute(createIndexTipo);
            stmt.execute(createIndexTitolo);
            stmt.execute(createIndexStato);
            stmt.execute(createIndexGenere);
            
            LOGGER.info("Database inizializzato con successo: " + dbUrl);
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore inizializzazione database", e);
            throw new RuntimeException("Impossibile inizializzare il database", e);
        }
    }
    
    public void shutdown() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("SHUTDOWN");
            LOGGER.info("Database chiuso correttamente");
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Errore chiusura database", e);
        }
    }
    
    public String getDatabasePath() {
        return dbUrl;
    }
}//DatabaseManager