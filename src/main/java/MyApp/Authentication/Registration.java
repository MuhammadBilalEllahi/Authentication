package MyApp.Authentication;

import MyApp.SplashScreen.SplashScreen;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static MyApp.JDBC.SingeltonJDBC.connection;
import static MyApp.WebEngine.WebEngine.HEIGHT;
import static MyApp.WebEngine.WebEngine.WIDTH;


public class Registration {

    //public static Scene Registration;


    static RadioButton manager_radio = new RadioButton("Manager");
    static RadioButton cashier_radio = new RadioButton("Cashier");
    static RadioButton admin_radio = new RadioButton("Admin");
    static String manager = manager_radio.getText();
    static String cashier = cashier_radio.getText();

    public static Scene RegistrationPage() {


        GridPane root = new GridPane();

        root.setAlignment(Pos.CENTER);
        root.setHgap(20);
        root.setVgap(20);
        root.setPadding(new Insets(20));


        Label name_label = new Label("Username");
        TextField name_text = new TextField();

        Label pass_label = new Label("Password");
        TextField pass_text = new TextField();

        Label admin_pass_label = new Label("Admin Access Password");
        PasswordField pass_text_admin = new PasswordField();

        Text check = new Text();

        Button backbutton = new Button("Back");
        Back_to_Main_Page(backbutton);
        backbutton.setStyle("-fx-background-color: orange;");

        SplashScreen.stage.setTitle("Sign Up!");


        Button register = new Button("Register");
        Text welcometext = new Text(1, 1, "Sign up your Account");
        welcometext.setFont(Font.font(22));
        welcometext.setFill(Color.WHITE);
        root.setStyle("-fx-background-image: url(bg_reg.jpg); -fx-background-repeat: no-repeat; -fx-background-size: 1800 1500;   -fx-background-position: center center;");

        pass_label.setStyle("-fx-text-fill:white; -fx-font-size:12");
        name_label.setStyle("-fx-text-fill:white; -fx-font-size:12");
        admin_pass_label.setStyle("-fx-text-fill:white; -fx-font-size:12");
        register.setStyle("-fx-background-color: rgb(201,251,251);");


        Label Admin_or_Employee = new Label("Employee Type");
        ToggleGroup Admin_or_Employee_grp = new ToggleGroup();

        manager_radio.setToggleGroup(Admin_or_Employee_grp);
        cashier_radio.setToggleGroup(Admin_or_Employee_grp);

        Admin_or_Employee.setStyle("-fx-text-fill:white; -fx-font-size:12");
        manager_radio.setStyle("-fx-text-fill:white; -fx-font-size:12");
        cashier_radio.setStyle("-fx-text-fill:white; -fx-font-size:12");


        root.add(welcometext, 0, 0, 2, 1);
        root.add(name_label, 0, 1);
        root.add(name_text, 0, 2, 2, 1);
        root.add(pass_label, 0, 3);
        root.add(pass_text, 0, 4, 2, 1);
        root.add(Admin_or_Employee, 0, 5);
        root.add(manager_radio, 0, 6);
        root.add(cashier_radio, 1, 6);
        root.add(admin_pass_label, 0, 7);
        root.add(pass_text_admin, 0, 8, 2, 1);
        root.add(check, 0, 9, 2, 1);
        root.add(backbutton, 0, 10);
        root.add(register, 1, 10);

        name_text.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                pass_text.requestFocus();
            }
        });
        pass_text.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                manager_radio.requestFocus();
            }
        });
        manager_radio.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                manager_radio.fire();
                pass_text_admin.requestFocus();
            }
            if (event.getCode() == KeyCode.TAB) {
                cashier_radio.requestFocus();
            }
        });
        cashier_radio.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                cashier_radio.fire();
                pass_text_admin.requestFocus();
            }
        });
        pass_text_admin.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                register.requestFocus();
            }
        });
        register.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                register.fire();
            }
        });


        add_Register_EventHandler(register, manager_radio, cashier_radio, pass_text_admin, name_text, pass_text, check,Admin_or_Employee_grp);

        return new Scene(root, WIDTH, HEIGHT);
    }


    public static void add_Register_EventHandler(Button register, RadioButton manager_radio, RadioButton cashier_radio,
                                                 TextField pass_text_admin, TextField name_text, TextField pass_text, Text check,ToggleGroup Admin_or_Employee_grp) {

        //re-establish connection MyApp.JDBC.SingeltonJDBC.getInstance().isConnected()
        if(connection != null){
        register.setOnAction(actionEvent -> {


                manager = manager_radio.getText();
                cashier = cashier_radio.getText();
                check.setFill(Color.WHITE);

                String username = name_text.getText();
                String password = pass_text.getText();

                String admin_pass = pass_text_admin.getText();


            if (username == null || password == null || username.equals("") || password.equals("")) {

                check.setText("Username or Password is empty");

            }
            else {
                try {
                    // Check if username already exists
                    String selectQuery = "SELECT * FROM users WHERE Username=?";
                    PreparedStatement selectStmt = connection.prepareStatement(selectQuery);

                    selectStmt.setString(1, username);

                ResultSet result = selectStmt.executeQuery();
                    if (result.next()) {
                        check.setText("Username already exists");
                        return;
                    }
                if (admin_pass.equals("444") ){
                    admin_radio.setToggleGroup(Admin_or_Employee_grp);
                    admin_radio.setStyle("-fx-text-fill:white; -fx-font-size:12");

                    try {
                    String sql = "INSERT INTO users(Username,Password,AccountType)" +
                            "VALUES (?,?,?)";
                    PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setString(1,username);
                    statement.setString(2,password);
                    statement.setString(3,admin_radio.getText());
                    Dialog(admin_radio.getText());

                    statement.execute();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                }

                if (admin_pass.equals("1234") && (cashier_radio.isSelected() || manager_radio.isSelected())) {
                    radioSelected(cashier_radio, username, password, cashier);
                    radioSelected(manager_radio, username, password, manager);
                } else {
                    // check.setText("Invalid Admin Password");
                    check.setText("Invalid Admin Password or Option\n is Empty");
                }
                } catch (SQLException e) {
                    check.setText("Username Already Exists");
                }


            }

        });
        }
        else {
            check.setFill(Color.BLACK);
            check.setFont(Font.font(20));
            check.setText("!!!Database not Connected!!!");
        }

    }

    private static void radioSelected(RadioButton cashier_radio, String username, String password, String cashier) {
        if (cashier_radio.isSelected()) {

            try {
                String sql = "INSERT INTO users(Username,Password,AccountType)" +
                        "VALUES (?,?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1,username);
                statement.setString(2,password);
                statement.setString(3, cashier);
                statement.execute();
                Dialog(cashier);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
    }

    public static void Back_to_Main_Page(Button back_button) {
        back_button.setOnAction(actionEvent -> SplashScreen.stage.setScene(Login.LoginPage()));
    }


    static public void Dialog(String s) {

        Stage Message_stage = new Stage();
        DialogPane dialogPane = new DialogPane();
        Scene Message_scene = new Scene(dialogPane, 220, 70);
        Message_stage.setResizable(false);
        dialogPane.setContentText(s + "  Registered Successfully");
        Message_stage.setTitle("Dialog");
        Message_stage.setScene(Message_scene);
        Message_stage.show();
        dialogPane.setMinWidth(220);
        dialogPane.setMinHeight(70);

        EventHandler<WindowEvent> windowEvent = windowEvent1 -> SplashScreen.stage.setScene(Login.LoginPage());
        Message_stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, windowEvent);

    }



}
