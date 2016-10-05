package com.project.graphs;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import java.awt.Dimension;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class MainWindow extends JFrame {

    // 0 -> Undirected  1 -> Directed
    public static int GRAPH_KIND = 1;

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
        JMenu matrixMenu = new JMenu("Matrices");
        JRadioButtonMenuItem directedMenuItem = new JRadioButtonMenuItem("Dirigido", true);
        JRadioButtonMenuItem undirectedMenuItem = new JRadioButtonMenuItem("No Dirigido");
        JMenuItem exitMenuItem = new JMenuItem("Salir");
        JMenuItem aboutMenuItem = new JMenuItem("Acerca");
        JMenuItem dijkstraMenuItem = new JMenuItem("Dijkstra");
        JMenuItem floydMenuItem = new JMenuItem("Floyd");
        JMenuItem weightMatrixMenuItem = new JMenuItem("Matriz de Costos");
        JMenuItem adjacencyMatrixMenuItem = new JMenuItem("Matriz de Adyacencia");

        directedMenuItem.setMnemonic(KeyEvent.VK_1);
        directedMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_MASK));
        undirectedMenuItem.setMnemonic(KeyEvent.VK_2);
        undirectedMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_MASK));
        dijkstraMenuItem.setMnemonic(KeyEvent.VK_D);
        dijkstraMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
        floydMenuItem.setMnemonic(KeyEvent.VK_F);
        floydMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
        aboutMenuItem.setMnemonic(KeyEvent.VK_F1);
        aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        exitMenuItem.setMnemonic(KeyEvent.VK_Q);
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.ALT_MASK));

        GraphPanel graphPanel = new GraphPanel();

        ButtonGroup menuGroup = new ButtonGroup();
        menuGroup.add(directedMenuItem);
        menuGroup.add(undirectedMenuItem);

        algorithmsMenu.add(dijkstraMenuItem);
        algorithmsMenu.add(floydMenuItem);

        helpMenu.add(aboutMenuItem);
        graphMenu.add(directedMenuItem);
        graphMenu.add(undirectedMenuItem);
        matrixMenu.add(weightMatrixMenuItem);
        matrixMenu.add(adjacencyMatrixMenuItem);
        fileMenu.add(graphMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        mainMenu.add(fileMenu);
        mainMenu.add(algorithmsMenu);
        mainMenu.add(matrixMenu);
        mainMenu.add(helpMenu);

        frame.setJMenuBar(mainMenu);

        undirectedMenuItem.addActionListener(e -> GRAPH_KIND = 0);
        directedMenuItem.addActionListener(e -> GRAPH_KIND = 1);
        aboutMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Grafos\n\tVersion: 1.0", "Acerca",
                                        JOptionPane.INFORMATION_MESSAGE));
        exitMenuItem.addActionListener(e -> System.exit(0));

        container.add(graphPanel, BorderLayout.CENTER);
    }
}
