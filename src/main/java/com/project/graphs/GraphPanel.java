package com.project.graphs;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import java.util.List;
import java.util.ArrayList;

public class GraphPanel extends JPanel {

    private List<Ellipse2D> ellipses;
    private List<Integer> xLinesCoordinates;
    private List<Integer> yLinesCoordinates;
    private Ellipse2D current;
    private int count, auxCount;
    private int xBefore;
    private int yBefore;
    private boolean dragged;
    private int firstArrayPosition, secondArrayPosition;
    private int xAux, yAux;

    private static final int DIMENSION = 30;

    public GraphPanel() {
        super();
        this.ellipses = new ArrayList<>();
        this.xLinesCoordinates = new ArrayList<>();
        this.yLinesCoordinates =new ArrayList<>();
        this.count = 0;
        this.auxCount = 0;
        this.xBefore = 0;
        this.yBefore = 0;
        this.firstArrayPosition = 0;
        this.secondArrayPosition = 0;
        this.xAux = 100;
        this.yAux = 100;
        this.dragged = false;
        initPanel(this);
    }

    private void initPanel(JPanel panel) {
        panel.setBackground(Color.DARK_GRAY);

        panel.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(isPositionCorrect(e)) {
                    if(arrayPosition(e) != -1) {
                        if(auxCount == 1) {
                            secondArrayPosition = arrayPosition(e);
                            auxCount = 0;
                            xLinesCoordinates.add(firstArrayPosition);
                            yLinesCoordinates.add(secondArrayPosition);
                            repaint();
                        }
                        else {
                            firstArrayPosition= arrayPosition(e);
                            auxCount++;
                        }
                    }
                    else {
                        auxCount = 0;
                    }

                }
                else {
                    xAux = e.getX();
                    yAux = e.getY();
                    ellipses.add(new Ellipse2D.Double(xAux, yAux, DIMENSION, DIMENSION));
                    auxCount = 0;
                }

                panel.repaint();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!dragged) {
                    if (isPositionCorrect(e)) {
                        xBefore = e.getX();
                        yBefore = e.getY();
                        dragged = true;
                    }
                }
                else {
                    current = new Ellipse2D.Double((ellipses.get(count).getX() + e.getX()) - xBefore,(ellipses.get(count).getY()
                            +e.getY()) - yBefore, DIMENSION, DIMENSION);
                    ellipses.set(count, current);
                    xBefore = e.getX();
                    yBefore = e.getY();
                }
                panel.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e){
                dragged = false;
                if(isPositionCorrect(e))
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                else
                    setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    private boolean isPositionCorrect(MouseEvent e) {
        int x1 = e.getX();
        int y1 = e.getY();

        for (int i= 0; i < ellipses.size(); i++) {
            if((x1 > ellipses.get(i).getX()) && (x1 < ellipses.get(i).getX() + DIMENSION) && (y1 > ellipses.get(i).getY()) &&
                    (y1 < ellipses.get(i).getY() + DIMENSION)) {
                count = i;
                return true;
            }
        }

        return false;
    }

    private int arrayPosition(MouseEvent e) {
        int x1 = e.getX();
        int y1 = e.getY();

        for (int i= 0; i < ellipses.size(); i++) {
            if((x1 > ellipses.get(i).getX()) && (x1 < ellipses.get(i).getX() + 30) && (y1 > ellipses.get(i).getY()) &&
                    (y1 < ellipses.get(i).getY() + 30)) {
                count = i;
                return i;
            }
        }

        return -1;
    }

    private void drawArrowHead(Graphics2D g2, Point start, Point finish, Color color) {
        double phi = Math.toRadians(30);
        final int size = 10;
        double dx = start.x - finish.x;
        double dy = start.y - finish.y;
        double theta = Math.atan2(dy, dx);
        double x1, y1, rho = theta + phi;

        g2.setPaint(color);

        for (int j = 0; j < 2; j++) {
            x1 = start.x - size * Math.cos(rho);
            y1 = start.y - size * Math.sin(rho);
            g2.draw(new Line2D.Double(start.x, start.y, x1, y1));
            rho = theta - phi;
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Ellipses
        for (int i = 0; i < ellipses.size(); i++) {
            g2.setColor(new Color(117, 35, 37));
            g2.setStroke(new BasicStroke(4));
            g2.fill(ellipses.get(i));
            g2.setColor(Color.BLACK);
            g2.drawOval((int) ellipses.get(i).getX(), (int) ellipses.get(i).getY(), DIMENSION, DIMENSION);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Monospaced", Font.BOLD, 12));
            g2.drawString(String.valueOf(i + 1), (int) ellipses.get(i).getX(), (int) ellipses.get(i).getY());
        }

        // Lines
        for (int i = 0; i < xLinesCoordinates.size(); i++) {
            g2.setColor(Color.BLACK);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(2));

            int lineX1 = (int) ellipses.get(xLinesCoordinates.get(i)).getCenterX();
            int lineY1 = (int) ellipses.get(xLinesCoordinates.get(i)).getCenterY();
            int lineX2 = (int) ellipses.get(yLinesCoordinates.get(i)).getCenterX();
            int lineY2 = (int) ellipses.get(yLinesCoordinates.get(i)).getCenterY();

            if (MainWindow.GRAPH_KIND == 1) { // If Directed
                g2.drawLine(lineX1, lineY1, lineX2, lineY2);
                Point startLine = new Point(lineX1, lineY1);
                Point finishLine = new Point(lineX2, lineY2);
                drawArrowHead(g2, finishLine, startLine, Color.BLACK);
            }
            else // Undirected
                g2.drawLine(lineX1, lineY1, lineX2, lineY2);

            // g2.setColor(Color.WHITE);
            // g2.setFont(new Font("Monospaced", Font.BOLD, 12));
            // g2.drawString(String.valueOf());
        }
    }
}
