package it.ingsw.progetto;

import it.ingsw.progetto.controller.MediaController;
import it.ingsw.progetto.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Avvia DB, DAO, Cronologia e Notifiche
        MediaController controller = new MediaController();
        
        // Crea la grafica e la collega al cervello
        MainView root = new MainView(controller);
        
        // Mostra la finestra
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("Progetto Ingegneria del Software - Cinemateca");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); 
    }
}//AppMain