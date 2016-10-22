package sample;

import javafx.concurrent.Task;

/**
 * Created by AlekseiSamoilov on 10/12/15.
 */
public class ProgressClass extends Task<Void> {
    @Override
    public Void call() {
        final int max = 100000000;
        updateProgress(0, max);
        for (int i=1; i<=max; i++) {
            updateProgress(i, max);
        }

        return null;
    }

}
