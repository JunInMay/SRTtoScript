import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    public GUI() {
        setSize(500, 150);
        setTitle("Frame Test");

        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
    }

    public void engage () {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
