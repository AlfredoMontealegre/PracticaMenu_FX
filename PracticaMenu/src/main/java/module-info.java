module ni.uam.edu.practicamenu {

    requires javafx.controls;
    requires javafx.fxml;

    exports ni.uam.edu.practicamenu.modelo;

    opens ni.uam.edu.practicamenu.controllers to javafx.fxml;
    opens ni.uam.edu.practicamenu.modelo to javafx.fxml, javafx.base;
}