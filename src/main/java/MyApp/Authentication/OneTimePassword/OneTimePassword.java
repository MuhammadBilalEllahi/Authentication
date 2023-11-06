package MyApp.Authentication.OneTimePassword;

import MyApp.Authentication.Captcha.CaptchaApp;
import MyApp.Authentication.SMS_Sender.SMSSender;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
//import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignS;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;



import static MyApp.Authentication.EmailSender.EmailSenderWithAttachment.sendEmail;

public class OneTimePassword {

    public static double HEIGHT = 800;
    public static  TextField textField1 = new TextField();
    public static TextField textField2 = new TextField();
    public static TextField textField3 = new TextField();
    public static TextField textField4 = new TextField();

    public static Button verifyOtpbutton = new Button("Verify OTP");
    public static int a,b,c,d;
    public static double WIDTH = 800;
    public static FontIcon smsIcon = new FontIcon();//FontAwesome.COMMENTING);

    public static Scene OneTimePassword_Scene(){


        Label heading_label = new Label("Enter Your OTP");
        heading_label.setAlignment(Pos.CENTER);

        verifyOtpbutton.setAlignment(Pos.CENTER);



        double x = 37;
        textFieldListener(textField1, x, null, textField2);
        textFieldListener(textField2, x, textField1, textField3);
        textFieldListener(textField3, x, textField2, textField4);
        textFieldListener(textField4, x, textField3, null);


        HBox hBox = new HBox(textField1,textField2,textField3,textField4);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);


        Button reset_OTP = new Button();

        smsIcon.setIconSize(17);
        reset_OTP.setGraphic(smsIcon);



        reset_OTP.prefHeight(-150);
        reset_OTP.prefWidth(-150);



        HBox hBox1 = new HBox(verifyOtpbutton,reset_OTP);
        hBox1.setSpacing(4);
        hBox1.setAlignment(Pos.CENTER);

        VBox vb = new VBox(CaptchaApp.Captcha());
        vb.setAlignment(Pos.CENTER);
        VBox label_vBox = new VBox(heading_label,hBox, vb,hBox1,showDateTime());
        label_vBox.setSpacing(15);
        label_vBox.setAlignment(Pos.CENTER);


        reset_OTP.setOnAction(event -> OTP_generator());

        reset_OTP.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                reset_OTP.fire();
            }
        });
        verifyOtpbutton.setOnAction(event -> check_OTP());
        verifyOtpbutton.setOnAction(event -> CaptchaApp.validateCaptcha());
        reset_OTP.setOnMouseClicked(event -> textField1.requestFocus());
        verifyOtpbutton.setOnKeyPressed(event ->{
            if (!(event.getCode() == KeyCode.ENTER)) {
                    textField1.requestFocus();
            }
            if (event.getCode() == KeyCode.ENTER) {
                    verifyOtpbutton.fire();
            }
            });

        return new Scene(label_vBox,WIDTH,HEIGHT);
    }

    public static void textFieldListener(TextField textField, double x, TextField previousTextField, TextField nextTextField) {
        textField.setMaxWidth(x - 10);
        textField.setMaxHeight(x);
        textField.setPrefHeight(x);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            black();
            if (!newValue.matches("\\d*")) { // Regex for digits only
                textField.setText(newValue.replaceAll("\\D", "")); // Remove non-digits
            }
            if (textField.getText().length() > 1) { // Limit to one digit
                textField.setText(textField.getText(0, 1));
            }
            textField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.BACK_SPACE && textField.getText().isEmpty()) {
                    if(previousTextField != null)
                        previousTextField.requestFocus();
                }
                else if (textField.getText().length() == 1) {
                    if(nextTextField == null){
                        verifyOtpbutton.requestFocus();
                    }else
                        nextTextField.requestFocus();
                }

            });

        });


    }
    public  static  void check_OTP()  {
        black();

        if (!textField1.getText().isEmpty()
        &&  !textField2.getText().isEmpty()
        &&  !textField3.getText().isEmpty()
        &&  !textField4.getText().isEmpty() )
        {
        int w = Integer.parseInt(textField1.getText());
        int x = Integer.parseInt(textField2.getText());
        int y = Integer.parseInt(textField3.getText());
        int z = Integer.parseInt(textField4.getText());


        if( w == a && x == b && y == c && z == d  ){
                green();
        }
        else {
            red();// wouldn't work here at this moment
            setEmpty();
        }
        }
        else {
            red();
            System.out.println("Generate / Enter OTP PLease");
        }


    }
    public static void green(){
        textField1.setStyle("-fx-text-fill: green;");
        textField2.setStyle("-fx-text-fill: green;");
        textField3.setStyle("-fx-text-fill: green;");
        textField4.setStyle("-fx-text-fill: green;");

    }
    public static void red(){
        textField1.setStyle("-fx-text-fill: red;");
        textField2.setStyle("-fx-text-fill: red;");
        textField3.setStyle("-fx-text-fill: red;");
        textField4.setStyle("-fx-text-fill: red;");

    }
    public static void black(){
        textField1.setStyle("-fx-text-fill: black;");
        textField2.setStyle("-fx-text-fill: black;");
        textField3.setStyle("-fx-text-fill: black;");
        textField4.setStyle("-fx-text-fill: black;");

    }
    public static void setEmpty(){
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
    }

    public static void emailSending(String message, String subject, String emailTo)/*Thread to send Email Message*/ {
        Task<Void> emailTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                sendEmail(message, subject, emailTo);
                return null;
            }
        };

        emailTask.setOnRunning(e -> {
            // Show a loading indicator or disable the button while the email is being sent
            System.out.println("Sending email...");
        });

        emailTask.setOnSucceeded(e -> {
            // Enable the button or hide the loading indicator when the email is sent successfully
            System.out.println("Email sent successfully!");
            smsIcon.setIconColor(Color.GREEN);

        });

        emailTask.setOnFailed(e -> {
            // Handle any failure or error during the email sending operation
            System.out.println("Failed to send email: " + emailTask.getException().getMessage());
            smsIcon.setIconColor(Color.RED);
        });

        Thread emailThread = new Thread(emailTask);
        emailThread.start();
    }
    public static void messageSending(String numberTo,  String message)/*Thread to send Sms Message*/ {
        Task<Void> messageTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                smsMessage(numberTo, message);
                return null;
            }
        };

        messageTask.setOnRunning(e -> {
            // Show a loading indicator or disable the button while the email is being sent
            System.out.println("Sending message...");
        });

        messageTask.setOnSucceeded(e -> {
            // Enable the button or hide the loading indicator when the email is sent successfully
            System.out.println("Message sent successfully!");
            smsIcon.setIconColor(Color.GREEN);
        });

        messageTask.setOnFailed(e -> {
            // Handle any failure or error during the email sending operation
            System.out.println("Failed to send message: " + messageTask.getException().getMessage());
            smsIcon.setIconColor(Color.RED);
        });

        Thread messageThread = new Thread(messageTask);
        messageThread.start();
    }

    public static void OTP_generator()/*call to generate otp (also call message and call method) */{
        int i, j,k, l;
            Random random = new Random();
            i = random.nextInt(0,9);
            j = random.nextInt(0,9);
            k = random.nextInt(0,9);
            l = random.nextInt(0,9);
            a = i;  b= j;   c= k;   d=l;

            String message = "Otp generated from code is " + a + "" + b + "" + c + "" + d + " on " + showDateTime().getText()+"\n";


        System.out.println(message);
        emailSending(message ,"OTP","TOWHERE_EMAIL_IS_SENT@gmail.com");
        messageSending("+923111553820",message);


        //message("+92_TOWHERE_SMS_IS_SENT20","Otp generated is " + a + " " + b + " " + c + " " + d +"\n");

    }
    public static void smsMessage(String toNumber, String message)/*call to send otp via message */{
        SMSSender.sendSMS(toNumber, "Your App OTP is  "+ message);
    }
    public static void phoneCall(String toNumber,String message)/*call to send otp via phone call */{

        try {
            SMSSender.callPerson(toNumber,message);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    public static Label showDateTime() /*call this to show the date*/{
        Label dateTimeLabel = new Label();

        // Set the initial text for the Label
        updateDateTime(dateTimeLabel);

        // Create a Timeline to update the date and time every second
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            updateDateTime(dateTimeLabel);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        return dateTimeLabel;
    }
    public static void updateDateTime(Label label) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss EEEE");
        String formattedDateTime = now.format(formatter);
        label.setText(formattedDateTime);
    }
}
