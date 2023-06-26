import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GUI extends JFrame {
    private Logic logic;

    public GUI(Logic l) {
        setSize(500, 150);
        setTitle("Frame Test");
        logic = l;

        getContentPane().add(buttonFileChoose());
    }

    public void engage () {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JButton buttonFileChoose() {
        JButton fileChooseButton = new JButton("Open");
        fileChooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    logic.readFile(selectedFile);
                }
            }
        });
        return fileChooseButton;
    }


}
