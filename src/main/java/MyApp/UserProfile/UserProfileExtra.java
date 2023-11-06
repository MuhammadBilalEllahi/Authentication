package MyApp.UserProfile;



import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
public class UserProfileExtra {

public static Scene UserProfileExtraScene() {


            // Create the form grid pane
            GridPane gridPane = createFormPane();

            // Add form elements to the grid pane
            addFormElements(gridPane);

            return new Scene(gridPane, 400, 300);
        }

 public static GridPane createFormPane() {
            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(20));
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            return gridPane;
        }

public static void addFormElements(GridPane gridPane) {
            // Add labels and fields
            Label firstNameLabel = new Label("First Name:");
            gridPane.add(firstNameLabel, 0, 0);

            TextField firstNameField = new TextField();
            gridPane.add(firstNameField, 1, 0);

            Label lastNameLabel = new Label("Last Name:");
            gridPane.add(lastNameLabel, 0, 1);

            TextField lastNameField = new TextField();
            gridPane.add(lastNameField, 1, 1);

            Label ageLabel = new Label("Age:");
            gridPane.add(ageLabel, 0, 2);

            TextField ageField = new TextField();
            gridPane.add(ageField, 1, 2);

            Label emailLabel = new Label("Email:");
            gridPane.add(emailLabel, 0, 3);

            TextField emailField = new TextField();
            gridPane.add(emailField, 1, 3);

            Label phoneLabel = new Label("Phone:");
            gridPane.add(phoneLabel, 0, 4);

            TextField phoneField = new TextField();
            gridPane.add(phoneField, 1, 4);

            // Add a submit button
            Button submitButton = new Button("Submit");
            submitButton.setOnAction(event -> {
                // saveUserProfile();
            });
            gridPane.add(submitButton, 0, 5, 2, 1);
        }

//    public void saveUserProfile() {
//        String firstName = firstNameField.getText();
//        String lastName = lastNameField.getText();
//        String age = ageField.getText();
//        String email = emailField.getText();
//        String phone = phoneField.getText();
//
//        // Perform any necessary validation or saving logic here
//
//        System.out.println("User Profile Saved:");
//        System.out.println("First Name: " + firstName);
//        System.out.println("Last Name: " + lastName);
//        System.out.println("Age: " + age);
//        System.out.println("Email: " + email);
//        System.out.println("Phone: " + phone);
//    }
    }
