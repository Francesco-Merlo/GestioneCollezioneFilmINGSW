package it.ingsw.progetto.view;

import it.ingsw.model.*;
import it.ingsw.progetto.factory.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class MediaInput extends Dialog<ElementiMultimediali> {

    public MediaInput() {
        setTitle("Aggiungi alla Collezione");
        setHeaderText("Inserisci i dettagli del nuovo contenuto");

        // Stile base
        getDialogPane().setStyle("-fx-background-color: #181818;");
        getDialogPane().lookup(".header-panel").setStyle("-fx-background-color: #121212;");

        ButtonType loginButtonType = new ButtonType("Salva", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField titolo = new TextField();
        titolo.setPromptText("Titolo");
        
        ComboBox<TipoContenuto> tipo = new ComboBox<>();
        tipo.getItems().setAll(TipoContenuto.values());
        tipo.setValue(TipoContenuto.FILM);

        ComboBox<Genere> genere = new ComboBox<>();
        genere.getItems().setAll(Genere.values());

        ComboBox<StatoVisione> stato = new ComboBox<>();
        stato.getItems().setAll(StatoVisione.values());
        stato.setValue(StatoVisione.DA_VEDERE);

        TextField extra = new TextField(); 
        extra.setPromptText("Regista / Autore");

        // Applichiamo lo stile per le scritte
        Label lblTitolo = new Label("Titolo:"); lblTitolo.setStyle("-fx-text-fill: white;");
        Label lblTipo = new Label("Tipo:"); lblTipo.setStyle("-fx-text-fill: white;");
        Label lblGenere = new Label("Genere:"); lblGenere.setStyle("-fx-text-fill: white;");
        Label lblStato = new Label("Stato:"); lblStato.setStyle("-fx-text-fill: white;");
        Label lblExtra = new Label("Specifiche:"); lblExtra.setStyle("-fx-text-fill: white;");

        grid.add(lblTitolo, 0, 0); grid.add(titolo, 1, 0);
        grid.add(lblTipo, 0, 1); grid.add(tipo, 1, 1);
        grid.add(lblGenere, 0, 2); grid.add(genere, 1, 2);
        grid.add(lblStato, 0, 3); grid.add(stato, 1, 3);
        grid.add(lblExtra, 0, 4); grid.add(extra, 1, 4);

        getDialogPane().setContent(grid);

        setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                String id = "ID-" + System.currentTimeMillis();
                ElementiMultimediali nuovoMedia = null;
                
                if (tipo.getValue() == TipoContenuto.FILM) {
                    nuovoMedia = FilmFactory.getInstance().crea(id, titolo.getText(), extra.getText(), 2024, 1);
                } else if (tipo.getValue() == TipoContenuto.SERIE_TV) {
                    nuovoMedia = SerieTvFactory.getInstance().crea(id, titolo.getText(), extra.getText(), 2024, 1);
                } else if (tipo.getValue() == TipoContenuto.DOCUMENTARIO) {
                    nuovoMedia = DocumentarioFactory.getInstance().crea(id, titolo.getText(), extra.getText(), 2024, 1);
                }
                
                if (nuovoMedia != null) {
                    if (genere.getValue() != null) nuovoMedia.setGenere(genere.getValue());
                    if (stato.getValue() != null) nuovoMedia.setStatoVisione(stato.getValue());
                    return nuovoMedia;
                }
            }
            return null;
        });
    }
}//MediaInput
