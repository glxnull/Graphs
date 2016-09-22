package com.project.graphs;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Container;
import java.awt.BorderLayout;

public class MainWindow extends JFrame {

    // 0 -> Undirected  1 -> Directed
    public static int GRAPH_KIND = 0;

    public MainWindow() {
        super("Grafos");
        initComponents(this);
    }

    private void initComponents(JFrame frame) {
        frame.setSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());

        JMenuBar mainMenu = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        JMenu helpMenu = new JMenu("Ayuda");
        JMenu graphMenu = new JMenu("Tipo de Grafo");
        JMenu algorithmsMenu = new JMenu("Algoritmos");
        JRadioButtonMenuItem directedMenuItem = new JRadioButtonMenuItem("Dirigido", true);
        JRadioButtonMenuItem undirectedMenuItem = new JRadioButtonMenuItem("No Dirigido");
        JMenuItem exitMenuItem = new JMenuItem("Salir");
        JMenuItem aboutMenuItem = new JMenuItem("Acerca");
        JMenuItem dijkstraMenuItem = new JMenuItem("Dijkstra");
        JMenuItem primMenuItem = new JMenuItem("Prim");

        GraphPanel graphPanel = new GraphPanel();

        ButtonGroup menuGroup = new ButtonGroup();
        menuGroup.add(directedMenuItem);
        menuGroup.add(undirectedMenuItem);

        algorithmsMenu.add(dijkstraMenuItem);
        algorithmsMenu.add(primMenuItem);

        helpMenu.add(aboutMenuItem);

        graphMenu.add(directedMenuItem);
        graphMenu.add(undirectedMenuItem);
        fileMenu.add(graphMenu);
        fileMenu.add(algorithmsMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        mainMenu.add(fileMenu);
        mainMenu.add(helpMenu);

        frame.setJMenuBar(mainMenu);

        aboutMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Grafos\n\tVersion: 1.0", "Acerca",
                                        JOptionPane.INFORMATION_MESSAGE));
        exitMenuItem.addActionListener(e -> System.exit(0));

        container.add(graphPanel, BorderLayout.CENTER);
    }
}
