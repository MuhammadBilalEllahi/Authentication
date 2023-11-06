package MyApp.SplashScreen;

import MyApp.Authentication.Login;
import MyApp.Authentication.OneTimePassword.OneTimePassword;
import MyApp.JDBC.SingeltonJDBC;
import MyApp.UserProfile.UserProfile;
import MyApp.WebEngine.WebEngine;
import javafx.animation.*;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import static MyApp.WebEngine.WebEngine.HEIGHT;
import static MyApp.WebEngine.WebEngine.WIDTH;

public class SplashScreen extends Application {
/// --module-path ".../Downloads/javafx-sdk-20.0.1/lib" --add-modules="javafx.controls,javafx.fxml"
    private boolean jdbcConnected = false;

    public static Stage stage;
    public static  Label loadingLabel;
    public static  Label promptlabel ;
    public static int counter = 0;

    private double  mouseX, mouseY,offsetX,offsetY;
    @Override
    public void start(Stage stage) {

        SplashScreen.stage = stage;

       //Image View (Added to GridPane)
        ImageView splashImageView = new ImageView();
        splashImageView.setStyle("-fx-image: url(WhiteGhost.png);");
        splashImageView.setFitWidth(120);
        splashImageView.setFitHeight(120);

        imageMove(splashImageView);

        //Label
        loadingLabel = new Label("Please Wait While Database is Connecting" );
        loadingLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: white;");


        //GridPane for Image
        GridPane labelAndImageGrid = new GridPane();
        labelAndImageGrid.add(splashImageView,2,0,3,2);
        labelAndImageGrid.setAlignment(Pos.TOP_RIGHT);
        labelAndImageGrid.setVgap(10);
        //GridPane for Labels
        labelAndImageGrid.add(loadingLabel,1,3,5,5);
        labelAndImageGrid.setAlignment(Pos.CENTER);
        labelAndImageGrid.setHgap(130);

        //StackPane
        StackPane splashLayout = new StackPane();
        splashLayout.getChildren().add(labelAndImageGrid);
        splashLayout.setStyle("-fx-background-image: url(img.png); -fx-background-radius: 6px; -fx-padding: 20px; ");

        //Scene
        Scene splashScene = new Scene(splashLayout, WIDTH,HEIGHT);
//        stage.setScene(UserProfile.userProfileScene());/******/ //----------------------------------------------------------------Scene
        stage.setScene(splashScene);/******/ //----------------------------------------------------------------Scene
//        stage.setScene();/******/ //----------------------------------------------------------------Scene
        stage.show();
        //Scene End and Shown

       responsiveness(labelAndImageGrid,splashScene);

        //Transition
        objectTransition(splashImageView);
        //Fade transition for background at start
        backgroundFade(splashLayout);
        //Task to start MyApp.JDBC and meet conditions if task succeed or fail
        Task<Void> task = getTask(labelAndImageGrid);
        //Counter to close if it exceeds 60 seconds && application if database is not connected
        cycleCount(stage);

        //closes application when pressed close button
        closeRequest(stage);


        //Thread Start for Task
        new Thread(task).start();
    }

    private static void closeRequest(Stage stage) {
        stage.setOnCloseRequest(event -> {
            // Perform any cleanup or save operations here

            // Display a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you want to exit?");
            alert.setContentText("Any unsaved data will be lost.");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                // User confirmed, close the application
                stage.close();
                System.exit(-1);
            } else {
                // Prevent the application from closing
                event.consume();
            }
        });
    }

    public static void responsiveness(GridPane root, Scene scene) {
        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());
    }


    private void imageMove(ImageView splashImageView) {
        splashImageView.setOnMousePressed(event -> {
            // Record the initial mouse press coordinates
            mouseX = event.getScreenX();
            mouseY = event.getScreenY();

            // Record the current image view position
            offsetX = splashImageView.getTranslateX();
            offsetY = splashImageView.getTranslateY();
        });

        splashImageView.setOnMouseDragged(event -> {
            // Calculate the delta change in mouse coordinates
            double deltaX = event.getScreenX() - mouseX;
            double deltaY = event.getScreenY() - mouseY;

            // Update the image view's position
            splashImageView.setTranslateX(offsetX + deltaX);
            splashImageView.setTranslateY(offsetY + deltaY);
        });

    }

    private Task<Void> getTask(GridPane gridPane2) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call()  {
                startJDBC();
                return null;
            }
        };

        taskOnSucceed(task);
        taskOnFail(gridPane2, task);
        return task;
    }

    private void taskOnSucceed(Task<Void> task) {
        task.setOnSucceeded(event -> {

            if (jdbcConnected) {
                SplashScreen.stage.setScene(Login.LoginPage());
                SplashScreen.stage.show();
            }

        });
    }

    private static void taskOnFail(GridPane gridPane2, Task<Void> task) {
        task.setOnFailed(event -> {
            promptlabel = new Label("\n\nConnecting is taking time, please check if your database status is online.");
            promptlabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: white;");
            gridPane2.add(promptlabel,0,10,6,2);

            //re-establish connection
            //MyApp.JDBC.SingeltonJDBC.getInstance().isConnected(); //error
            gridPane2.add(cycleCountRemaining(),2,9,2,1);

        });

    }
    public static Label cycleCountRemaining(){
        Label label = new Label();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

            int remainingTime = 60 - counter;
            label.setText("Closing in " + remainingTime);
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: white;");
            if(remainingTime <= 3){
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; ");
                label.setTextFill(Color.RED);
            }

        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        return label;
    }



    private static void backgroundFade(StackPane splashLayout) {
        //Fade Transition
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), splashLayout);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(2);
        fadeIn.setCycleCount(1);
        fadeIn.play();
        //Fade Transition End and Played
    }

    private void cycleCount(Stage stage) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            counter++;
            loadingLabel.setText("Please Wait While Database is Connecting"+ ".".repeat(counter % 4));
            if (counter >= 60 && !jdbcConnected) {
                stage.close();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private static void objectTransition(ImageView splashImageView) {
        // Text animation
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), splashImageView);
        translateTransition.setFromY(0);
        translateTransition.setToY(-5);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(SequentialTransition.INDEFINITE);

        // Sequential transition
        SequentialTransition sequentialTransition = new SequentialTransition(translateTransition);
        sequentialTransition.play();
    }

    public void startJDBC() {
        SingeltonJDBC jdbc = SingeltonJDBC.getInstance();
        if (jdbc.isConnected()) {
            jdbcConnected = true;
        }

    }

    public static void main(String[] args) {
        launch();
    }
}

