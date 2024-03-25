The Main class is the entry point for the JavaFX application, extending Application and providing the necessary start method for initializing the application. Within this class, the start method loads the UI layout from an FXML file using FXMLLoader and sets up the primary stage with the loaded scene. Additionally, the main method serves as the application's entry point, launching the JavaFX application with the provided arguments.

The Controller class implements Initializable and manages the functionality of UI elements defined in the corresponding FXML file. This class handles user interactions such as button clicks, manages data retrieval from files, performs search operations, updates badge statuses, saves data, deletes badges, adds new badges, and edits existing badge information. It also initializes UI components, sets up event listeners, and updates the UI based on user actions.

The BadgeType and BadgeStatus enums define the different types and statuses of badges within the application, respectively. These enums provide methods to retrieve the string representation of each badge type or status and facilitate conversion between enum types and corresponding strings.

Lastly, the Badge class represents individual badge objects with properties like badge number, type, note, and status. It utilizes JavaFX's SimpleStringProperty for property binding and offers getter and setter methods for accessing and modifying badge properties. The class also overrides the toString method to provide a string representation of the badge object, facilitating its display and manipulation within the application.