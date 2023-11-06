package MyApp.Animation;

import MyApp.Authentication.Registration;
import MyApp.SplashScreen.SplashScreen;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Random;

import static MyApp.Authentication.Login.login;

public  class AnimatedBackground {

    private static final int WIDTH = 1800;
    private static final int HEIGHT = 1200;
    private static final int NUM_CIRCLES = 100;
    private static final  Circle[] circles = new Circle[NUM_CIRCLES];
    private static final Random random = new Random();



    public static Scene start2() {
        BorderPane root = new BorderPane();
        // Generate NUM_CIRCLES random circles
        generateCircles(root);
        // Create an AnimationTimer to update the animation every frame
        circleTimer();
        root.setCenter(getGridPane());

        return new Scene(root, 800,1000);

    }

    private static void generateCircles(BorderPane root) {
        for (int i = 0; i < NUM_CIRCLES; i++) {
            // Generate a random pastel color
            Color color = Color.hsb(random.nextInt(360), 0.5 + random.nextDouble() * 0.5, 0.5 + random.nextDouble() * 0.5, 0.8);
            // Generate a random circle with radius between 10 and 50
            Circle circle = new Circle(10 + random.nextInt(40), color);
            // Set the initial position of the circle
            circle.setLayoutX(random.nextInt(WIDTH));
            circle.setLayoutY(random.nextInt(HEIGHT));
            circles[i] = circle;
            root.getChildren().add(circle);

        }
    }

    private static BorderPane getGridPane() {
        GridPane rootForAllNodes = new GridPane();
        BorderPane root = new BorderPane();
        HBox hBox = new HBox(rootForAllNodes);
        VBox vBox = new VBox(hBox);
        root.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);

        //Changes Background for Login Section
        rootForAllNodes.setPrefHeight(330);
        rootForAllNodes.setPrefWidth(280);


        rootForAllNodes.setStyle("-fx-background-color: rgba(255, 16, 5, 0.4);" +
                "-fx-background-radius: 10px;");

        rootForAllNodes.setAlignment(Pos.CENTER);
        rootForAllNodes.setHgap(85);
        rootForAllNodes.setVgap(25);


        Text welcometext = new Text(1, 1, "Login to your Account");
        welcometext.setFont(Font.font(22));
        welcometext.setStyle("-fx-text-fill: white; font-size: 46px");
        welcometext.setFill(Color.WHITE);
        rootForAllNodes.add(welcometext, 0, 0, 2, 1);

        VBox p = new VBox(welcometext);
        rootForAllNodes.add(p, 0, 0, 2, 1);

        Label label_user = new Label("User Name: ");
        label_user.setStyle("-fx-text-fill:white; -fx-font-size:12");
        rootForAllNodes.add(label_user, 0, 1);

        TextField login_text = new TextField();
        login_text.setPromptText("username ");
        rootForAllNodes.add(login_text, 0, 2, 2, 1);

        Label pass_label = new Label("Password");
        pass_label.setStyle("-fx-text-fill:white; -fx-font-size:12");
        rootForAllNodes.add(pass_label, 0, 3);


        PasswordField pass_text = new PasswordField();
        pass_text.setPromptText("password ");
        pass_text.setStyle(" -fx-border-color: #d0d0d0;");
        pass_text.setMinWidth(202);
        rootForAllNodes.add(pass_text, 0, 4, 2, 1); //column , row


        Button log_btn = new Button("Login");
        rootForAllNodes.add(log_btn, 1, 6);
        log_btn.setStyle("-fx-background-color: rgb(201,251,251);");


        rootForAllNodes.add(signup(), 0, 6);


        Text check = new Text();
        check.setStyle("-fx-text-fill:white; -fx-font-size: 14px;");
        rootForAllNodes.add(check, 0, 5, 2, 1);

        login(log_btn,login_text,pass_text,check); //From MyApp.Authentication

        return root;
    }

    private static void circleTimer() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Update the position of each circle
                for (Circle circle : circles) {
                    // Move the circle in a random direction
                    circle.setLayoutX(circle.getLayoutX() + (random.nextDouble() - 0.5) * 0.5);
                    circle.setLayoutY(circle.getLayoutY() + (random.nextDouble() - 0.5) * 0.5);
                    // Wrap the circle around when it goes off the screen
                    if (circle.getLayoutX() < -circle.getRadius()) {
                        circle.setLayoutX(WIDTH + circle.getRadius());
                    } else if (circle.getLayoutX() > WIDTH + circle.getRadius()) {
                        circle.setLayoutX(-circle.getRadius());
                    }
                    if (circle.getLayoutY() < -circle.getRadius()) {
                        circle.setLayoutY(HEIGHT + circle.getRadius());
                    } else if (circle.getLayoutY() > HEIGHT + circle.getRadius()) {
                        circle.setLayoutY(-circle.getRadius());
                    }
                }
            }
        };
        timer.start();
    }
    private static Node signup() {
        Button signup_btn = new Button("Sign up");
        signup_btn.setStyle("-fx-background-color: orange;");
        signup_btn.setOnAction(
                event -> SplashScreen.stage.setScene(Registration.RegistrationPage())
        );
        return signup_btn;
    }

}
