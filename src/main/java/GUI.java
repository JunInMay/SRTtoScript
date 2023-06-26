import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GUI extends JFrame {
    private Logic logic;

    public GUI(Logic l) {
        setSize(700, 700);
        setTitle("Frame Test");
        setLayout(new FlowLayout());
        logic = l;

        getContentPane().add(buttonFileChoose());
        getContentPane().add(buttonExecute());
    }

    public void engage () {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JButton buttonFileChoose () {
        JButton fileChooseButton = new JButton("Open");
        fileChooseButton.setPreferredSize(new Dimension(50, 50));
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

    public JButton buttonExecute () {
        JButton executeButton = new JButton("Execute");
        executeButton.setPreferredSize(new Dimension(50, 50));
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(logic.discernSentence());
            }
        });

        return executeButton;
    }

}
