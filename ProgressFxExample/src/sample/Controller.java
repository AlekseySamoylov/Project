package sample;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {
    @FXML
    private ProgressBar progressB;

    @FXML
    private void onClickB(ActionEvent event){

        progressB.setProgress(1.0D);
//        Task task = new Task() {
//            @Override
//            protected Object call() throws Exception {
//for (int i = 1; i<=100000000; i++){
//    updateProgress(i, 100000000);
//}
//                return null;
//            }
//        };
//
//        ExecutorService es = Executors.newSingleThreadExecutor();
//        progressB.progressProperty().bind(task.progressProperty());
//        es.execute(task);


    }
}
