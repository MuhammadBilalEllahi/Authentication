package MyApp.Authentication.Captcha;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

import java.security.SecureRandom;

import static MyApp.Authentication.OneTimePassword.OneTimePassword.*;

public class CaptchaApp {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CAPTCHA_LENGTH = 6;

    private static Label captchaLabel;
    private static TextField inputTextField;
    private static Button refreshButton;

    public static final Duration COOLDOWN_DURATION = Duration.seconds(10);


    public static boolean isGeneratingCaptcha = false;

    private static Label cooldownLabel = new Label("");

    private static long cooldownEndTime = 0;








    public static VBox Captcha(){

        VBox root = new VBox();


        captchaLabel = new Label("Captcha ");
        //captchaLabel.setStyle("-fx-text-fill: black;");
        captchaLabel.setStyle("-fx-background-color: yellow; -fx-pref-width: 60px; -fx-pref-height: 10px;");

        inputTextField = new TextField("");
        inputTextField.setOnMouseClicked(mouseEvent -> captchaLabel.setStyle("-fx-background-color: yellow; -fx-pref-width: 60px; -fx-pref-height: 10px;"));

        refreshButton = new Button("Refresh");

        FontIcon icon = new FontIcon(FontAwesome.REFRESH);
        refreshButton.setGraphic(icon);
        icon.setIconSize(15);



        refreshButton.setOnAction(event -> {
            if (!isGeneratingCaptcha) {
                generateCaptcha();
                startCooldownTimer();
            }
        });

        root.setPadding(new Insets(10));
        root.getChildren().addAll(captchaLabel, inputTextField, refreshButton,cooldownLabel);
        root.setMaxWidth(150);
        root.setSpacing(5);
        root.setAlignment(Pos.CENTER);


        return root;
    }

    public static void generateCaptcha() {
        if (!isGeneratingCaptcha) {
            String captchaText = generateRandomCaptcha();
            captchaLabel.setText(captchaText);

            isGeneratingCaptcha = false;


            String message = "Otp generated from code is " + captchaText + " on " + showDateTime().getText() + "\n";


            System.out.println(message);


            //  emailSending(message ,"OTP","bilalillahi25@gmail.com");
            //messageSending("+923111553820",message);
        }

    }

    public static String generateRandomCaptcha() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }


    public static void validateCaptcha() {
        String userInput = inputTextField.getText();
        String captchaText = captchaLabel.getText();

        if (userInput.equals(captchaText)) {
            System.out.println("Captcha is valid.");
            captchaLabel.setStyle("-fx-background-color: lightgreen; -fx-pref-width: 60px; -fx-pref-height: 10px;");
        } else {
            System.out.println("Captcha is invalid.");
            captchaLabel.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4); -fx-pref-width: 60px; -fx-pref-height: 10px;");
            generateCaptcha();
        }
    }
//    public static void startCooldownTimer() {
//        isGeneratingCaptcha = true;
//        refreshButton.setDisable(true); // Disable the refresh button
//
//        Timeline cooldownTimer = new Timeline(new KeyFrame(COOLDOWN_DURATION));
//        cooldownTimer.setOnFinished(event -> {
//            isGeneratingCaptcha = false;
//            refreshButton.setDisable(false); // Enable the refresh button when cooldown is over
//            cooldownLabel.setText(""); // Clear the cooldown label
//        });
//
//        // Update the cooldown label text during the cooldown period
//        cooldownTimer.setOnUpdate(event -> {
//            Duration timeLeft = COOLDOWN_DURATION.subtract(cooldownTimer.getCurrentTime());
//            long secondsLeft = (long) Math.ceil(timeLeft.toSeconds());
//            cooldownLabel.setText("Cooldown: " + secondsLeft + "s");
//        });
//
//        cooldownTimer.play();
//    }

    public static void startCooldownTimer() {
        isGeneratingCaptcha = true;
        refreshButton.setDisable(true); // Disable the refresh button

        long currentTime = System.currentTimeMillis();
        cooldownEndTime = (long) (currentTime + COOLDOWN_DURATION.toMillis());

        AnimationTimer cooldownTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long timeLeft = (cooldownEndTime - System.currentTimeMillis()) / 1000;
                if (timeLeft <= 0) {
                    isGeneratingCaptcha = false;
                    refreshButton.setDisable(false); // Enable the refresh button when cooldown is over
                    cooldownLabel.setText(""); // Clear the cooldown label
                    stop();
                } else {
                    cooldownLabel.setText("Please Wait: " + timeLeft + "s"+"\nto resend OTP");
                }
            }
        };
        cooldownTimer.start();


    }



}
