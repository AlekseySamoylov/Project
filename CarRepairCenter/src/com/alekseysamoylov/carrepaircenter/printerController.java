package com.alekseysamoylov.carrepaircenter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Aleksey Samoylov on 29.12.2015.
 */
public class printerController implements Initializable{

    @FXML private TextArea printerArea;
    @FXML private Label label;

    @FXML
    private void clickPrint() throws IOException {
        String printerText = printerArea.getText();



        FileWriter writer = new FileWriter("text.txt");
        BufferedWriter writer2 = new BufferedWriter(writer);

        writer2.newLine();
        writer2.write(printerText);

        writer2.close();
        writer.close();

        new PrinterGo().printPage();
    }

    @FXML
    private void clickBack() throws IOException {
        StaticValues.setPrintOrder("");
        new StageClose(label);
        new NewStage("mainStage.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        printerArea.setText(StaticValues.getPrintOrder());
    }


}
