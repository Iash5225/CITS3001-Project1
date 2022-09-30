import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;

public class CanvasController extends JFrame implements MouseInputListener {
    private Canvas canvas;
    private int x, y;

    public CanvasController(String title) {
        super(title);
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Define 5 buttons
        JButton b1 = new JButton("Red");
        JButton b2 = new JButton("Green");
        JButton b3 = new JButton("Blue");
        JButton b4 = new JButton("Yellow");
        JButton b5 = new JButton("Black");

        // Define a panel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(b4);
        panel.add(b5);

        getContentPane().add(panel, BorderLayout.SOUTH);
        setVisible(true);

        canvas = new Canvas();
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        getContentPane().add(canvas);
        JButton button = new JButton("Clear");
        getContentPane().add(button, BorderLayout.SOUTH);
        setVisible(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Graphics g = canvas.getGraphics();
                g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            }
        });
    }

    // create a color change jbutton
    public void colorChange(JButton button, Color color) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Graphics g = canvas.getGraphics();
                g.setColor(color);
            }
        });
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