package MyApp.UserProfile;


import MyApp.JDBC.DatabaseController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

public class UserProfile {
    public static Scene userProfileScene(){
        BorderPane borderPane = new BorderPane();


        Button editProfileButton = new Button("Edit Profile");
        Button saveChangesButton = new Button("Save Changes");
        getButton(editProfileButton);
        getButton(saveChangesButton);

        Insets insets = new Insets(30, 50, 60, 40);
        Insets insetsForDetailLabel = new Insets(0, 0, 30, 40);


        Label nameLabel = new Label("Your Name Here");
        nameLabel.setTextFill(Color.WHITE);

        Label nameLabel2 = new Label("Name");
        getLabel(nameLabel2);
        TextField nameField = new TextField();
        getTextField(nameField);
        HBox nameHBox = new HBox(nameLabel2,nameField);

        Label usernameLabel2 = new Label("Username");
        getLabel(usernameLabel2);
        TextField usernameField = new TextField();
        getTextField(usernameField);
        HBox usernameBox = new HBox(usernameLabel2,usernameField);

        Label dobLabel2 = new Label("Date of Birth");
        getLabel(dobLabel2);
        TextField dobField = new TextField();
        getTextField(dobField);
        HBox dobHBox = new HBox(dobLabel2,dobField);

        Label phoneLabel2 = new Label("Phone");
        getLabel(phoneLabel2);
        TextField phoneField = new TextField();
        getTextField(phoneField);
        HBox phoneHBox = new HBox(phoneLabel2,phoneField);

        Label emailLabel2 = new Label("Email");
        getLabel(emailLabel2);
        TextField emailField = new TextField();
        getTextField(emailField);
        HBox emailHBox = new HBox(emailLabel2,emailField);


        Label mainHeadingLabel = new Label("Profile Settings"); //Main First Heading
        mainHeadingLabel.setFont(Font.font(25));
        mainHeadingLabel.setTextFill(Color.WHITE);
        VBox mainHeadingBox = new VBox(mainHeadingLabel);



        VBox profileDetailedBox = new VBox(nameHBox,usernameBox,dobHBox);//pack
        VBox profileDetailsBox = new VBox(getHeadingLabel("Profile details"),profileDetailedBox);
        VBox thirdHeadingBox = new VBox(getHeadingLabel("Contact info"));
        VBox profileContact = new VBox(phoneHBox,emailHBox);//pack
        VBox profileInfo = new VBox(thirdHeadingBox,profileContact);


        VBox saveChangesVBox = new VBox(saveChangesButton);
        saveChangesVBox.setAlignment(Pos.CENTER);

        VBox full = new VBox(mainHeadingBox,profileDetailsBox,profileInfo,saveChangesVBox);
        borderPane.setCenter(full);
        full.getStyleClass().add("secondary-color");

        //insets
        mainHeadingBox.setPadding(insets);
        profileDetailsBox.setPadding(insetsForDetailLabel);
        profileInfo.setPadding(insetsForDetailLabel);


        //lineVbox.setAlignment(Pos.CENTER);

        editProfileButton.setOnAction(event -> {
            DatabaseController.resumeDatabase();

            nameField.setEditable(true);
            usernameField.setEditable(true);
            dobField.setEditable(true);
            phoneField.setEditable(true);
            emailField.setEditable(true);
        });
        saveChangesButton.setOnAction(event -> {

            DatabaseController.pauseDatabase();

            nameField.setEditable(false);
            usernameField.setEditable(false);
            dobField.setEditable(false);
            phoneField.setEditable(false);
            emailField.setEditable(false);
        });

        //left portion

        VBox lineVbox = new VBox(gethbox(FontAwesome.USER,"Profile"),
                gethbox(FontAwesome.BELL,"Notifications"),
                gethbox(FontAwesome.KEY,"Password"),
                gethbox(FontAwesome.COG,"Settings"),
                gethbox(FontAwesome.LOCK,"Privacy Policy"));
        lineVbox.setSpacing(15);

        VBox leftTopBox = new VBox(getImageView(),nameLabel,editProfileButton);
        leftTopBox.setSpacing(15);
        leftTopBox.setAlignment(Pos.CENTER);
        leftTopBox.setPrefSize(200,250);


        VBox leftHeightedBox = new VBox(leftTopBox,lineVbox);//Main VBox
        leftHeightedBox.getStyleClass().add("primary-color");

        leftHeightedBox.setSpacing(25);
        leftHeightedBox.setPrefWidth(250);
        borderPane.setLeft(leftHeightedBox);

        Scene scene = new Scene(borderPane,900,700);
        scene.getStylesheets().add("/styles.css");
        return  scene;
    }

    public static void getButton(Button button) {
        button.setTextFill(Color.WHITE);
        button.setFont(Font.font(12));
        button.setAlignment(Pos.CENTER);
        button.getStyleClass().add("button");

    }


    public static void getLabel(Label  label) {
        Insets insetsForDetailLabel2 = new Insets(10, 0, 30, 0);
        label.setTextFill(Color.WHITE);
        label.setMinWidth(160);
        label.setPadding(insetsForDetailLabel2);


    }
    public static Label getHeadingLabel(String name) {
        Insets insetsForHeadings = new Insets(0, 0, 20, 0);
        Label label = new Label(name);

        label.setFont(Font.font(20));
        label.setTextFill(Color.WHITE);
        label.setPadding(insetsForHeadings);

        return label;
    }

    public static void getTextField(TextField textField){
        textField.setMinWidth(300);
        textField.setEditable(false);
        textField.getStyleClass().add("text-field");


    }



    public static HBox gethbox(FontAwesome font,String linedata) {
        HBox lineBox = getLineBox(font,linedata);
        lineBox.getStyleClass().add("selected");
        return  lineBox;
    }


    private static HBox getLineBox(FontAwesome font,String linedata) {
        Label profileLabel = new Label(linedata);

        FontIcon icon = new FontIcon();

        icon.setIconCode(font);
        icon.setIconSize(22);
        icon.setIconColor(Color.WHITE);

        profileLabel.setTextFill(Color.WHITE);
        profileLabel.setFont(Font.font(18));

        HBox box = new HBox(icon);
        HBox box2 = new HBox(profileLabel);


        box2.setPrefWidth(140);
        HBox result = new HBox(box, box2);

        result.setSpacing(20);
        result.setAlignment(Pos.CENTER);


        return result;
    }

    private static ImageView getImageView() {
        ImageView imageView = new ImageView();
        imageView.setStyle("-fx-image: url(image.jpg);");
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        imageView.setClip(new Circle(40,40, 40));
        return imageView;
    }

}
