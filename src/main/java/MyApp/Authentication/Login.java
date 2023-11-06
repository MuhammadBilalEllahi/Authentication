package MyApp.Authentication;

import MyApp.Authentication.Captcha.CaptchaApp;
import MyApp.Authentication.OneTimePassword.OneTimePassword;
import MyApp.SplashScreen.SplashScreen;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static MyApp.JDBC.SingeltonJDBC.connection;
import static MyApp.SplashScreen.SplashScreen.responsiveness;
import static MyApp.WebEngine.WebEngine.HEIGHT;
import static MyApp.WebEngine.WebEngine.WIDTH;

public class Login {


    public static Scene LoginPage() {


        GridPane root = new GridPane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        root.setAlignment(Pos.CENTER);
        root.setHgap(85);
        root.setVgap(25);
        root.setStyle("-fx-background-image: url(bg_pastel.png); -fx-background-repeat: no-repeat; -fx-background-size: 1400 1500;   -fx-background-position: top center;");


        Text welcometext = new Text(1, 1, "Login to your Account");
        welcometext.setFont(Font.font(22));
        welcometext.setStyle("-fx-text-fill: white; font-size: 46px");
        welcometext.setFill(Color.WHITE);
        root.add(welcometext, 0, 0, 2, 1);

        VBox p = new VBox(welcometext);
        root.add(p, 0, 0, 2, 1);

        Label label_user = new Label("User Name: ");
        label_user.setStyle("-fx-text-fill:white; -fx-font-size:12");
        root.add(label_user, 0, 1);

        TextField login_text = new TextField();
        login_text.setPromptText("username ");
        root.add(login_text, 0, 2, 2, 1);

        Label pass_label = new Label("Password");
        pass_label.setStyle("-fx-text-fill:white; -fx-font-size:12");
        root.add(pass_label, 0, 3);


        PasswordField pass_text = new PasswordField();
        pass_text.setPromptText("password ");
        pass_text.setStyle(" -fx-border-color: #d0d0d0;");

        TextField pass_text_visible = new TextField();
        pass_text_visible.setPromptText("password ");
        pass_text_visible.setStyle(" -fx-border-color: #d0d0d0;");

        ToggleButton toggleButton = getToggleButton(pass_text,pass_text_visible,root);
        HBox hBox = new HBox(-34, pass_text, toggleButton);
        pass_text.setMinWidth(202);
        root.add(hBox, 0, 4, 2, 1); //column , row


        Button log_btn = new Button("Login");
        root.add(log_btn, 1, 6);
        log_btn.setStyle("-fx-background-color: rgb(201,251,251);");


        Text check = new Text();
        check.setStyle("-fx-text-fill:white; -fx-font-size: 14px;");
        root.add(check, 0, 5, 2, 1);

        root.add(CaptchaApp.Captcha(),0, 7, 2, 1);



        signup(root);
        login(log_btn,login_text,pass_text,check);
        //Event Handler Pressing Enter
        OnKeyPressedEnter(login_text, pass_text);
        OnKeyPressedEnter(pass_text, log_btn);
        OnKeyPressedEnter(pass_text_visible, log_btn);
        OnKeyPressedEnter(log_btn);


        responsiveness(root,scene);
        return scene;
    }

    private static void OnKeyPressedEnter(TextField textField, PasswordField passwordField) {
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
            });
    }
    private static void OnKeyPressedEnter(PasswordField passwordField, Button setOnAction) {
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                setOnAction.requestFocus();
            }
            });
    }
    private static void OnKeyPressedEnter(TextField textField, Button setOnAction) {
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                setOnAction.requestFocus();
            }
            });
    }
    private static void OnKeyPressedEnter(Button setOnAction) {
        setOnAction.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                setOnAction.fire();
            }
            });
    }


    private static void signup(GridPane root) {
        Button signup_btn = new Button("Sign up");
        root.add(signup_btn, 0, 6);
        signup_btn.setStyle("-fx-background-color: orange;");
        signup_btn.setOnAction(
                event -> SplashScreen.stage.setScene(Registration.RegistrationPage())
        );
        signup_btn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                signup_btn.fire();
            }
        });
    }

    private static ToggleButton getToggleButton(PasswordField passField,TextField password_visible_textField,GridPane root) {
        ToggleButton toggleButton = new ToggleButton();
      //  ImageView eyeIcon = new ImageView(new Image("hide.png"));
        FontIcon eyeIcon = new FontIcon(FontAwesome.EYE);
        eyeIcon.setIconSize(16);
        toggleButton.setGraphic(eyeIcon);
        toggleButton.setStyle("-fx-background-color: transparent;");



        toggleButton.setOnAction(event -> {
            if (toggleButton.isSelected()) {
                //shows text field
                password_visible_textField.setText(passField.getText());
                eyeIcon.setIconCode(FontAwesome.EYE_SLASH);
                HBox hBox = new HBox(-34, password_visible_textField, toggleButton);
                password_visible_textField.setMinWidth(202);
                root.add(hBox, 0, 4, 2, 1); //column , row




            } else {

                passField.setText(password_visible_textField.getText());
                eyeIcon.setIconCode(FontAwesome.EYE);
                HBox hBox = new HBox(-34, passField, toggleButton);
                passField.setMinWidth(202);
                root.add(hBox, 0, 4, 2, 1);
            }

        });
        password_visible_textField.textProperty().addListener((observable, oldValue, newValue) -> {
            passField.setText(password_visible_textField.getText());
            System.out.println(passField.getHeight());
        });

        return toggleButton;
    }

    static String username;
    static String password;


    public static void login(Button log_btn, TextField login_text, PasswordField pass_text,Text check) {



        username = login_text.getText();
        password = pass_text.getText();

        if (connection != null) {

            log_btn.setOnAction(actionEvent -> {
                pass_text.setPrefHeight(28);
                try {

                    loginSqlStatement(login_text, pass_text);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else {
            check.setFill(Color.ORANGERED);
            check.setFont(Font.font(15));
            check.setText("!!!Database not Connected!!!");
        }
    }

    private static void loginSqlStatement(TextField login_text, PasswordField pass_text) throws SQLException {
        String sql = "SELECT * FROM users WHERE Username = ? AND Password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login_text.getText());//parameter index 1 means 1st column
        statement.setString(2, pass_text.getText());//parameter index 2 means 2nd column

        System.out.println(login_text.getText());
        System.out.println(pass_text.getText());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            SplashScreen.stage.setScene(OneTimePassword.OneTimePassword_Scene());

        } else {
            login_text.setStyle("-fx-text-fill:red; ");
            pass_text.setStyle("-fx-text-fill:red; ");

        }
    }


}



