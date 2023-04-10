package app;
import java.io.IOException;

import app.view.ISimulationUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.framework.Trace;
import simu.framework.Trace.Level;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

 
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Terminaalisimulaattori");
        
        //Pysäytä jos ikkuna suljetaan.
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent t) {
		        Platform.exit();
		        System.exit(0);
		    }
		});
        
        Trace.setTraceLevel(Level.INFO, true);

        initRootLayout();
        
        showBorderPane();

    }
    
    //Käynnistää Root-Layoutin.
    public void initRootLayout() {
        try {
            // Lataa root fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Näytä root layout scene.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void showBorderPane() {
        try {
            // Lataa itse ikkuna root sceneen.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/simulaatioBorderPane.fxml"));
            BorderPane simulationApp = (BorderPane) loader.load();
            	                   
            // Keskitä ikkuna
            rootLayout.setCenter(simulationApp);
            
            // Give the controller access to the main app.
            ISimulationUI ui = loader.getController();
            ui.setMainApp(this);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	// Palauttaa primary stagen.
    
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
}
