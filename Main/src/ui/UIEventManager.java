package ui;

import javax.swing.*;

public interface UIEventManager {
    void onStart() throws InterruptedException;
    void reset();
    void stop();
}
