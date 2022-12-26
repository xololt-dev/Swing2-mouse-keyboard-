package tb.soft;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Kanwa extends JFrame implements MouseMotionListener, MouseListener{
    private Canvas canvas;
    private int mouseX = 0;
    private int mouseY = 0;
    private boolean shape = false;
    Kanwa(){
        // Set the frame to exit when it is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Kanwa");
        setVisible(true);
        setSize(400,400);
        // Center the frame
        setLocationRelativeTo(null);

        // Create a new canvas
        canvas = new Canvas();
        // Add the canvas to the frame
        add(canvas);
        // Set the size of the canvas
        canvas.setPreferredSize(new Dimension(400, 400));
        // Pack the frame to fit the canvas
        pack();

        // Add a listeners to the frame
        canvas.addMouseMotionListener(this);
        canvas.addMouseListener(this);

        // Keybinds to toggle shapes, draw and exit the program
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F"), "toggleShape");
        getRootPane().getActionMap().put("toggleShape", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                // Toggle the shape that will be drawn
                shape = !shape;
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "drawShape");
        getRootPane().getActionMap().put("drawShape", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Graphics2D g = (Graphics2D) canvas.getGraphics();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(shape){
                    // Set the color to draw with
                    g.setColor(Color.RED);

                    // Draw a circle
                    g.fillOval(mouseX - 25, mouseY - 25, 50, 50);
                }
                else{
                    // Set the color to draw with
                    g.setColor(Color.BLUE);

                    // Draw a square
                    g.fillRect(mouseX - 25, mouseY - 25, 50, 50);
                }
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "exitProgram");
        getRootPane().getActionMap().put("exitProgram", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                // Hide the panel
                setVisible(false);
                // Delete the instance
                dispose();
                // Exit the program
                System.exit(0);
            }
        });
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        // Get mouse coordinates
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mouseClicked(MouseEvent e) {
        // If left button pressed, get canvas and clear it
        if (e.getButton() == MouseEvent.BUTTON1){
            Graphics2D g = (Graphics2D) canvas.getGraphics();
            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Kanwa();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        });
    }
}