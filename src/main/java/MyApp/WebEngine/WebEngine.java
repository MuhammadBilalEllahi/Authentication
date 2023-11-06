package MyApp.WebEngine;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static MyApp.JDBC.SingeltonJDBC.connection;

public class WebEngine {

    public static int WIDTH = 800;
    public static int HEIGHT = 800;
    public static  Scene scene;



    public static Scene startWebScene() {
        StackPane root = new StackPane();
        scene = new Scene(root,WIDTH,HEIGHT);


        Button button = new Button("Click");
        button.setOnAction(event -> {
        });
        root.getChildren().add(button);

/*

        try {
            read();
        }
        catch (Exception e){
            e.printStackTrace();
        }
*/
        responsiveness(root,scene);

        root.getChildren().add(webEngine());
        return  scene ;
    }

    private static void responsiveness(StackPane root, Scene scene) {
        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());
    }

    public static WebView webEngine(){

        WebView webView = new WebView();
        javafx.scene.web.WebEngine webEngine = webView.getEngine();
        webEngine.load("https://www.google.com");
        return webView;

    }
    public static void read() throws SQLException, DocumentException {

    String sql = "SELECT * FROM users";
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