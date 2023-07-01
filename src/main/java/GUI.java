import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.prefs.Preferences;

public class GUI extends JFrame {
    private Logic logic;
    private File selectedFile;
    private JTextField contentTextField;
    private static String filePath = "testFileLocation";
    private static String fileName = "test";
    private static String extension = ".txt";

    private String lastUsedFolder = ".";

    public GUI(Logic l) {
        setSize(700, 700);
        setTitle("Frame Test");
        setLayout(new FlowLayout());
        logic = l;

        JPanel mainPanel = new JPanel(new GridBagLayout());
        System.out.println(mainPanel.getInsets());
        GridBagConstraints constraints = new GridBagConstraints();
        getContentPane().add(mainPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 0, 5, 0);
        mainPanel.add(buttonPanel, constraints);

        buttonPanel.add(button("Open", actionFileChoose()));
        buttonPanel.add(button("Execute", actionExecute()));
        buttonPanel.add(button("Generate", actionFileGenerate()));

        JPanel textFieldPanel = new JPanel(new GridLayout(1, 2, 5, 20));
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(textFieldPanel, constraints);

        textFieldPanel.add(textArea(40, 30));
        textFieldPanel.add(textArea(40, 30));

//        mainPanel.add(textField(20));

    }

    public void engage () {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JButton button (String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(80, 30));
        button.addActionListener(actionListener);
        button.setMargin(new Insets(1, 1, 1, 1));

        return button;
    }

    private JTextField textField (int width) {
        return new JTextField(width);
    }

    private JTextArea textArea (int rows, int cols) {
        JTextArea jTextArea = new JTextArea();
        jTextArea.setPreferredSize(new Dimension(300, 300));
        return jTextArea;
    }

    private ActionListener actionFileChoose() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Preferences prefs = Preferences.userRoot().node(getClass().getName());
                JFileChooser fileChooser = new JFileChooser(prefs.get(lastUsedFolder,
                        new File(".").getAbsolutePath()));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    prefs.put(lastUsedFolder, fileChooser.getSelectedFile().getParent());
                }
            }
        };
    }

    private ActionListener actionExecute() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(logic.discernSentence(selectedFile));
            }
        };
    }

    private ActionListener actionFileGenerate() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logic.makeFile(filePath, fileName, extension);
            }
        };
    }

}
