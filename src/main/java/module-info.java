module betaversion.cp2406assignment_beta {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.csv;
    requires java.desktop;


    opens betaversion.cp2406assignment_beta to javafx.fxml;
    exports betaversion.cp2406assignment_beta;
}