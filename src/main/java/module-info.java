module org.example.prototype {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.prototype to javafx.fxml;
    exports org.example.prototype;
}