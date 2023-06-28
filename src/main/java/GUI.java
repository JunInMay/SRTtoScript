import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GUI extends JFrame {
    private Logic logic;
    private File selectedFile;
    private static String filePath = "testFileLocation";
    private static String fileName = "test";
    private static String extension = ".txt";

    public GUI(Logic l) {
        setSize(700, 700);
        setTitle("Frame Test");
        setLayout(new FlowLayout());
        logic = l;

        getContentPane().add(buttonFileChoose());
        getContentPane().add(buttonExecute());
        getContentPane().add(buttonFileGenerate());
    }

    public void engage () {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JButton buttonFileChoose () {
        JButton fileChooseButton = new JButton("Open");
        fileChooseButton.setPreferredSize(new Dimension(80, 30));
        fileChooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                }
            }
        });
        return fileChooseButton;
    }

    public JButton buttonExecute () {
        JButton executeButton = new JButton("Execute");
        executeButton.setPreferredSize(new Dimension(80, 30));
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(logic.discernSentence(selectedFile));
            }
        });

        return executeButton;
    }

    public JButton buttonFileGenerate () {
        JButton generateButton = new JButton("Generate");
        generateButton.setPreferredSize(new Dimension(80, 30));
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logic.makeFile(filePath, fileName, extension);
            }
        });
        generateButton.setMargin(new Insets(1,1,1,1));
        return generateButton;
    }

}
