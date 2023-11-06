package MyApp.JavaCodes;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static MyApp.JDBC.SingeltonJDBC.connection;

public class JavaCodes {

    /**
     * Responsive method adjusts the scene width and height. When the width and height of stage is changed by user.
     * */
    public static void responsiveness(GridPane root, Scene scene) {
        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());
    }
    public static void responsiveness(BorderPane root, Scene scene) {
        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());
    }
    public static void responsiveness(StackPane root, Scene scene) {
        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());
    }
    public static void responsiveness(TilePane root, Scene scene) {
        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());
    }
    public static void responsiveness(HBox root, Scene scene) {
        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());
    }
    public static void responsiveness(VBox root, Scene scene) {
        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());
    }
    public static void responsiveness(AnchorPane root, Scene scene) {
        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());
    }
    public static void responsiveness(Pane root, Scene scene) {
        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());
    }


    /**
     * OnKeyPressedEnter is used to set focus on same/other object.
     * */
    private static void OnKeyPressedEnter(TextField textField, PasswordField passwordField) {
        //change focus to passwordField from text-field
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });
    }
    private static void OnKeyPressedEnter( PasswordField passwordField,TextField textField) {
        //change focus to text-field from passwordField
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                textField.requestFocus();
            }
        });
    }
    private static void OnKeyPressedEnter(PasswordField passwordField, Button setOnAction) {
        //change focus to button from passwordField
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                setOnAction.requestFocus();
            }
        });
    }
    private static void OnKeyPressedEnter(Button setOnAction,PasswordField passwordField) {
        //change focus to passwordField from button
        setOnAction.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });
    }
    private static void OnKeyPressedEnterFire(Button setOnAction) {
        //change focus to itself and fires itself
        setOnAction.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                setOnAction.fire();
            }
        });
    }

    /**
     * Open WebPage to your App
     * */
    public static WebView webEngine(String url){
        // Provide url to Start a webpage.
        WebView webView = new WebView();
        javafx.scene.web.WebEngine webEngine = webView.getEngine();
        webEngine.load(url);
        return webView;

    }
    /**
     * Read Table from sql Database
     * Incomplete Method
     * */
    public static void read(String table) throws SQLException, DocumentException {

        String sql = "SELECT * FROM "+table;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        //DBTablePrinter.printResultSet(resultSet);

        com.itextpdf.text.Document document;
        document =   new com.itextpdf.text.Document(PageSize.A4,50,50,50,50);
        document.open();
        System.out.println(document.isOpen());

        int count=0;
        while (resultSet.next()) {
            count++;
            System.out.println("Inside while > "+count);
            String data = resultSet.getString("ID") + " " + resultSet.getString("Item")
                    + " " + resultSet.getString("Quantity")+ " " + resultSet.getString("Price");
            Paragraph paragraph = new Paragraph(data);
            document.add(paragraph);
            System.out.println(paragraph);


        }
        // Close the PDF document and the database connection
        document.close();
        System.out.println(document);

    }


}
