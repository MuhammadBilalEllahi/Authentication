package MyApp.sqlapp;

import MyApp.Authentication.Login;
import MyApp.SplashScreen.SplashScreen;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import static MyApp.WebEngine.WebEngine.*;

public class RandomClass {

    public static Scene secondScene;

    public static Scene secondScene(){

        Group root = new Group();
        secondScene = new Scene(root,WIDTH,HEIGHT);

        Button scene_button = new Button("Scene new");
        scene_button.setOnAction(event -> {
            SplashScreen.stage.setScene(Login.LoginPage());
        });
        scene_button.setLayoutY(90);
        scene_button.setLayoutX(90);
        System.out.println("Incorrect");



        root.getChildren().add(scene_button);
        return secondScene;
    }

}
