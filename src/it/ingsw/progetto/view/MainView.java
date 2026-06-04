package it.ingsw.progetto.view;

import it.ingsw.model.*;
import it.ingsw.progetto.controller.MediaController;
import it.ingsw.progetto.observer.MediaObserver;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class MainView extends VBox implements MediaObserver {
    private final MediaController controller;
    private ListView<String> listView;
    private Button btnUndo;
    
    private ComboBox<Genere> filterGenere;
    private ComboBox<StatoVisione> filterStato;

    public MainView(MediaController controller) {
        this.controller = controller;
        this.controller.registraObserver(this);
        
        //Sfondo scuro
        this.setStyle("-fx-background-color: #121212; -fx-font-family: 'Segoe UI', Helvetica, sans-serif;");
        setPadding(new Insets(20));
        setSpacing(20); 
        
        //Titolo
        Label lbl = new Label("La mia Cinemateca");
        lbl.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 26px; -fx-font-weight: bold;");
        
        //Barra dei filtri
        filterGenere = new ComboBox<>();
        filterGenere.getItems().add(null);
        filterGenere.getItems().addAll(Genere.values());
        filterGenere.setPromptText("Genere");
        filterGenere.setStyle("-fx-background-color: #282828; -fx-text-fill: white;");

        filterStato = new ComboBox<>();
        filterStato.getItems().add(null); 
        filterStato.getItems().addAll(StatoVisione.values());
        filterStato.setPromptText("Stato");
        filterStato.setStyle("-fx-background-color: #282828; -fx-text-fill: white;");

        Button btnClear = new Button("Pulisci");
        applicaStileBottoneNormale(btnClear);

        Label filterLabel = new Label("Filtra:");
        filterLabel.setStyle("-fx-text-fill: #B3B3B3;");
        
        HBox filterBar = new HBox(12, filterLabel, filterGenere, filterStato, btnClear);
        filterBar.setAlignment(Pos.CENTER_LEFT);

        filterGenere.setOnAction(e -> riapplicaFiltri());
        filterStato.setOnAction(e -> riapplicaFiltri());
        btnClear.setOnAction(e -> {
            filterGenere.setValue(null);
            filterStato.setValue(null);
            riapplicaFiltri();
        });

        //Lista con stile manuale delle celle
        listView = new ListView<>();
        listView.setStyle("-fx-background-color: #121212; -fx-control-inner-background: #121212; -fx-background-insets: 0;");
        VBox.setVgrow(listView, Priority.ALWAYS);
        
        // Personalizziamo la grafica delle singole righe della lista
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: #121212;");
                } else {
                    setText(item);
                    aggiornaStileCella(this, isSelected(), isHover());
                    
                    // Gestione Eventi Mouse
                    setOnMouseEntered(e -> aggiornaStileCella(this, isSelected(), true));
                    setOnMouseExited(e -> aggiornaStileCella(this, isSelected(), false));
                }
            }
            @Override
            public void updateSelected(boolean selected) {
                super.updateSelected(selected);
                if (!isEmpty()) aggiornaStileCella(this, selected, isHover());
            }
        });
        
        //Bottoni in basso
        btnUndo = new Button("Annulla");
        applicaStileBottoneAnnulla(btnUndo);
        btnUndo.setDisable(true);
        btnUndo.setOnAction(e -> controller.annullaUltimaAzione());
        
        Button btnAdd = new Button("+ Aggiungi Media");
        applicaStileBottoneAggiungi(btnAdd);
        btnAdd.setOnAction(e -> {
            MediaInput dialog = new MediaInput();
            Optional<ElementiMultimediali> result = dialog.showAndWait();
            result.ifPresent(controller::aggiungiNuovoMedia);
        });

        HBox tools = new HBox(15, btnUndo, btnAdd);
        tools.setAlignment(Pos.CENTER_RIGHT); 

        getChildren().addAll(lbl, filterBar, listView, tools);
        aggiornaInterfaccia(controller.getCollezioneCompleta());
    }

    private void riapplicaFiltri() {
        Genere g = filterGenere.getValue();
        StatoVisione s = filterStato.getValue();

        List<ElementiMultimediali> filtrata = controller.getCollezioneCompleta().stream()
            .filter(m -> (g == null || m.getGenere() == g))
            .filter(m -> (s == null || m.getStatoVisione() == s))
            .toList();
            
        aggiornaInterfaccia(filtrata);
    }

    private void aggiornaInterfaccia(List<ElementiMultimediali> lista) {
        listView.getItems().clear();
        for (ElementiMultimediali m : lista) {
            String genereStr = m.getGenere() != null ? m.getGenere().name() : "N/D";
            String statoStr = m.getStatoVisione() != null ? m.getStatoVisione().name() : "N/D";
            listView.getItems().add(String.format("%s  (%d)   |   %s   |   %s", 
                m.getTitolo(), m.getAnno(), genereStr, statoStr));
        }
        btnUndo.setDisable(!controller.puoAnnullare());
    }

    @Override 
    public void onMediaCambia(List<ElementiMultimediali> mediaList) { 
        Platform.runLater(this::riapplicaFiltri);
    }
    
    @Override 
    public void onMediaAggiungi(ElementiMultimediali media) { 
        onMediaCambia(controller.getCollezioneCompleta()); 
    }
    
    @Override 
    public void onMediaAggiorna(ElementiMultimediali media) { 
        onMediaCambia(controller.getCollezioneCompleta()); 
    }
    
    @Override 
    public void onMediaElimina(String mediaId) { 
        onMediaCambia(controller.getCollezioneCompleta()); 
    }

    //Metodi privati per la grafica

    private void applicaStileBottoneNormale(Button btn) {
        String base = "-fx-background-color: #282828; -fx-text-fill: #FFFFFF; -fx-background-radius: 20px; -fx-padding: 8px 18px; -fx-font-weight: bold; -fx-cursor: hand;";
        String hover = "-fx-background-color: #3E3E3E; -fx-text-fill: #FFFFFF; -fx-background-radius: 20px; -fx-padding: 8px 18px; -fx-font-weight: bold; -fx-cursor: hand;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(base));
    }

    private void applicaStileBottoneAggiungi(Button btn) {
        String base = "-fx-background-color: #1DB954; -fx-text-fill: #000000; -fx-background-radius: 20px; -fx-padding: 8px 18px; -fx-font-weight: bold; -fx-cursor: hand;";
        String hover = "-fx-background-color: #1ED760; -fx-text-fill: #000000; -fx-background-radius: 20px; -fx-padding: 8px 18px; -fx-font-weight: bold; -fx-cursor: hand;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(base));
    }

    private void applicaStileBottoneAnnulla(Button btn) {
        String base = "-fx-background-color: transparent; -fx-border-color: #B3B3B3; -fx-border-radius: 20px; -fx-text-fill: #B3B3B3; -fx-padding: 7px 17px; -fx-font-weight: bold; -fx-cursor: hand;";
        String hover = "-fx-background-color: transparent; -fx-border-color: #FFFFFF; -fx-border-radius: 20px; -fx-text-fill: #FFFFFF; -fx-padding: 7px 17px; -fx-font-weight: bold; -fx-cursor: hand;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> { if(!btn.isDisabled()) btn.setStyle(hover); });
        btn.setOnMouseExited(e -> btn.setStyle(base));
    }

    private void aggiornaStileCella(ListCell<String> cell, boolean isSelected, boolean isHover) {
        if (isSelected) {
            cell.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12px 15px;");
        } else if (isHover) {
            cell.setStyle("-fx-background-color: #282828; -fx-text-fill: #FFFFFF; -fx-padding: 12px 15px; -fx-border-color: #282828; -fx-border-width: 0 0 1 0; -fx-cursor: hand;");
        } else {
            cell.setStyle("-fx-background-color: #181818; -fx-text-fill: #B3B3B3; -fx-padding: 12px 15px; -fx-border-color: #282828; -fx-border-width: 0 0 1 0;");
        }
    }
}//MainView