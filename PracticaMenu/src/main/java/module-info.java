module ni.uam.edu.practicamenu {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.uam.edu.practicamenu to javafx.fxml;
    exports ni.uam.edu.practicamenu;
    exports ni.uam.edu.practicamenu.modelo;
    opens ni.uam.edu.practicamenu.modelo to javafx.fxml;
}