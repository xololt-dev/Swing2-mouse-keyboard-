package tb.soft;

import javax.swing.*;
import java.awt.event.*;
import java.awt.event.MouseMotionListener;

public class CatchGame extends JFrame implements MouseMotionListener {
    private JButton rabbit;
    private int rabbitX = 150;
    private int rabbitY = 150;
    private int mouseX = 0;
    private int mouseY = 0;
    private static final int SCREEN_EDGE_BUFFER = 20;
    private static final int RABBIT_MOVEMENT_DISTANCE = 100;

    CatchGame(){
        // Set the frame to exit when it is closed
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400,400);
        // Center the frame
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);
        setTitle("Przycisk nie królik, nie ucieknie...");

        // Create a button
        rabbit = new JButton();
        rabbit.setBounds(rabbitX, rabbitY, 20, 20);

        // If the button is pressed, go to the next part of the program
        rabbit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // Make it not visible
                setVisible(false);
                // Start the second part of the program
                new Kanwa();
                // Delete the current instance
                dispose();
            }
        });
        add(rabbit);

        // Add a listener to the frame
        addMouseMotionListener(this);

        // Keybinds to toggle shapes, draw and exit the program
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "exitProgram");
        getRootPane().getActionMap().put("exitProgram", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                System.exit(0);
            }
        });
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        // Get mouse coordinates
        int newMouseX = e.getX();
        int newMouseY = e.getY();

        // Calculate if we need to update button position
        double distanceToRabbit = Math.sqrt((rabbitX - newMouseX) * (rabbitX - newMouseX) + (rabbitY - newMouseY) * (rabbitY - newMouseY));

        if(distanceToRabbit < RABBIT_MOVEMENT_DISTANCE){
            double dx = newMouseX - mouseX;
            double dy = newMouseY - mouseY;

            // How much distance has the mouse covered
            double distance = Math.sqrt(dx * dx + dy * dy);
            if(distance > 5){
                mouseX = newMouseX;
                mouseY = newMouseY;
                return;
            }

            double cos = 0;
            double sin = 0;
            if(distance != 0){
                cos = dx/distance;
                sin = dy/distance;
            }

            double newRabbitX = rabbitX;
            double newRabbitY = rabbitY;

            // If mouse is coming towards the button, make the button run away from the cursor
            if(!(dx < 0 && rabbitX > newMouseX) && !(dx > 0 && rabbitX < newMouseX)){
                newRabbitX += cos * distance * 200 / distanceToRabbit;
                // Button (rabbit) runs away faster the closer you are to it
            }
            if(!(dy < 0 && rabbitY > newMouseY) && !(dy > 0 && rabbitY < newMouseY - SCREEN_EDGE_BUFFER)) {
                newRabbitY += sin * distance * 200 / distanceToRabbit;
                // Button (rabbit) runs away faster the closer you are to it
            }

            // If the button was to go off-screen, correct it
            if (newRabbitX < SCREEN_EDGE_BUFFER) { newRabbitX = SCREEN_EDGE_BUFFER; }
            else if (newRabbitX + 2 * rabbit.getWidth() > getWidth() - SCREEN_EDGE_BUFFER) {
                newRabbitX = getWidth() - 2 * rabbit.getWidth() - SCREEN_EDGE_BUFFER;
            }
            if (newRabbitY < SCREEN_EDGE_BUFFER){ newRabbitY = SCREEN_EDGE_BUFFER;}
            else if (newRabbitY > getHeight() - 3 * rabbit.getHeight() - SCREEN_EDGE_BUFFER) {
                newRabbitY = getHeight() - 3 * rabbit.getHeight() - SCREEN_EDGE_BUFFER;
            }

            rabbit.setBounds((int)newRabbitX, (int)newRabbitY,20, 20);

            rabbitX = (int)newRabbitX;
            rabbitY = (int)newRabbitY;
        }

        mouseX = newMouseX;
        mouseY = newMouseY;
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new CatchGame();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        });
    }
}