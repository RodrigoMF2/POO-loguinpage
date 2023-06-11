module com.example.logineregistro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.logineregistro to javafx.fxml;
    exports com.example.logineregistro;
}