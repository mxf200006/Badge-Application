This application is designed to manage and track badges within an organization or system. The main functionalities of the application include:

Displaying Badge Information: The application loads a graphical user interface (GUI) using JavaFX, where users can view a list of badges along with their details such as badge number, type, note, and status.

Data Management: The application allows users to read badge data from a file, perform search operations to find specific badges based on their number or type, and display the search results in the UI. Users can also add new badges, edit existing badge information, delete badges, and update badge statuses (e.g., mark a badge as active, deactive, missing, etc.).

User Interaction: The GUI includes various interactive elements such as buttons, text fields, and combo boxes that enable users to perform actions like saving badge data to a file, issuing new badges, returning badges, activating or deactivating badges, and handling missing or extra badges.

Enums for Badge Types and Statuses: The application utilizes enums (BadgeType and BadgeStatus) to categorize badges into different types (e.g., employee, student, visitor, etc.) and statuses (e.g., active, deactive, missing, etc.). This categorization helps in organizing and managing badges effectively.

User Notifications: The application provides user-friendly notifications using JavaFX's alert dialogs (Alert) to inform users about important events, confirmations for critical actions (e.g., deleting a badge), warnings, errors, and information messages.

The Main class is the entry point for the JavaFX application, extending Application and providing the necessary start method for initializing the application. Within this class, the start method loads the UI layout from an FXML file using FXMLLoader and sets up the primary stage with the loaded scene. Additionally, the main method serves as the application's entry point, launching the JavaFX application with the provided arguments.

The Controller class implements Initializable and manages the functionality of UI elements defined in the corresponding FXML file. This class handles user interactions such as button clicks, manages data retrieval from files, performs search operations, updates badge statuses, saves data, deletes badges, adds new badges, and edits existing badge information. It also initializes UI components, sets up event listeners, and updates the UI based on user actions.

The BadgeType and BadgeStatus enums define the different types and statuses of badges within the application, respectively. These enums provide methods to retrieve the string representation of each badge type or status and facilitate conversion between enum types and corresponding strings.

Lastly, the Badge class represents individual badge objects with properties like badge number, type, note, and status. It utilizes JavaFX's SimpleStringProperty for property binding and offers getter and setter methods for accessing and modifying badge properties. The class also overrides the toString method to provide a string representation of the badge object, facilitating its display and manipulation within the application.
