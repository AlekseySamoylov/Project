package com.alekseysamoylov.carrepaircenter;

import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Created by Aleksey Samoylov on 29.12.2015.
 */
public class StageClose {
    StageClose(Label label){
        Stage stage = (Stage) label.getScene().getWindow();
        stage.close();
    }
}
