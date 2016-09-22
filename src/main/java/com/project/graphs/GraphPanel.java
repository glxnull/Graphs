package com.project.graphs;

import com.sun.istack.internal.Nullable;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Cursor;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import java.util.List;
import java.util.ArrayList;

public class GraphPanel extends JPanel {

    private List<Ellipse2D> ellipses;
    private List<Line2D> lines;
    private List<String> labels;
    private Ellipse2D current;
    private Line2D line;
    private Point2D auxPoint;
    private int clicks, count;
    private String ellipseLabel;

    private static final int DIMENSION = 30;

    public GraphPanel() {
        super();
        this.ellipses = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.current = null;
        this.line = null;
        this.auxPoint = null;
        this.count = 1;
        this.clicks = 0;
        initPanel(this);
    }

    private void initPanel(JPanel panel) {
        panel.setBackground(Color.DARK_GRAY);

        panel.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                current = findEllipse(e.getPoint());
                if (current == null){
                    current = newEllipse(e.getPoint());
                    ellipses.add(current);
                    ellipseLabel = String.valueOf(count++);
                    labels.add(ellipseLabel);
                }

                panel.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                current = findEllipse(e.getPoint());
                if (current != null && e.getClickCount() >= 2){
                    clicks += e.getClickCount();
                    System.out.println("Numero de clicks: " + clicks);

                    if (clicks == 2) {
                        auxPoint = e.getPoint();
                        System.out.println("Posicion actual X: " + e.getX() + " Y: " + e.getY());
                    }
                    else if (clicks == 4) {
                        line = newLine(auxPoint, e.getPoint());
                        lines.add(line);
                        clicks = 0;
                    }
                }

                panel.repaint();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e){
                if(findEllipse(e.getPoint()) == null)
                    setCursor(Cursor.getDefaultCursor());
                else
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseDragged(MouseEvent e){
                if(current != null) {
                    int x = e.getX(), y = e.getY();
                    current.setFrame(x - DIMENSION / 2, y - DIMENSION / 2, DIMENSION, DIMENSION);
                }

                panel.repaint();
            }
        });
    }

    @Nullable
    private Ellipse2D findEllipse(Point2D p) {
        for (Ellipse2D ellipse : ellipses){
            if(ellipse.contains(p))
                return ellipse;
        }

        return null;
    }

    private Ellipse2D newEllipse(Point2D p){
        return new Ellipse2D.Double(p.getX() - DIMENSION / 2, p.getY() - DIMENSION / 2, DIMENSION, DIMENSION);
    }

    private Line2D newLine(Point2D first,Point2D second){
        return new Line2D.Double(first.getX(), first.getY(), second.getX(), second.getY());
    }


    private void drawArrowHead(Graphics2D g2, Point start, Point end, Color color) {
        double phi = Math.toRadians(30);
        int size = 15;
        double dy = start.y - end.y;
        double dx = start.x - end.x;
        double theta = Math.atan2(dy, dx);
        double x, y, rho = theta + phi;

        g2.setPaint(color);

        for(int j = 0; j < 2; j++) {
            x = start.x - size * Math.cos(rho);
            y = start.y - size * Math.sin(rho);
            g2.draw(new Line2D.Double(start.x, start.y, x, y));
            rho = theta - phi;
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Ellipse
        for (Ellipse2D ellipse : ellipses) {
            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(4));
            g2.fill(ellipse);
            g2.setColor(Color.BLACK);
            g2.drawOval(ellipse.getBounds().x, ellipse.getBounds().y, DIMENSION, DIMENSION);
        }

        // Lines
        for (Line2D line : lines) {
            double lineX = (line.getX2() - line.getX1()) / 2;
            double lineY = (line.getY2() - line.getY1()) / 2;
            g2.setColor(Color.BLACK);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(2));
            g2.draw(line);
            g2.drawString(String.valueOf(line.getBounds().getWidth()), (int) lineX, (int) lineY);
        }
    }
}