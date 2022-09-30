import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;

public class CanvasController extends JFrame implements MouseInputListener {
    private Canvas canvas;
    private int x, y;

    public CanvasController(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        canvas = new Canvas();
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        getContentPane().add(canvas);
        JButton button = new JButton("Clear");
        getContentPane().add(button, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    public void mouseDragged(MouseEvent e) {
        int x1 = e.getX();
        int y1 = e.getY();
        Graphics g = canvas.getGraphics();
        g.drawLine(x, y, x1, y1);
        x = x1;
        y = y1;
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public static void main(String[] args) {
        CanvasController c = new CanvasController("CanvasController");
    }
}