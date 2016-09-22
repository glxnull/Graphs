package com.project.graphs;

import javax.swing.UIManager;

public class Main {

    public static void main(String args[]) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.err.println("Error: Failed to load Look and Feel\n");
            e.printStackTrace();
        }

        MainWindow window = new MainWindow();
        window.setVisible(true);
    }
}
