package productos.Main;

import javafx.application.Application;
import javafx.stage.Stage;
import productos.view.MainFX;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        // Lanza la interfaz principal definida en MainFX
        new MainFX().start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
