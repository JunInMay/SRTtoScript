import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.prefs.Preferences;

public class GUI extends JFrame {
    private static String filePath = "testFileLocation";
    private static String fileName = "test";
    private static String extension = ".txt";

    private Logic logic;
    private File selectedFile;
    private JTextArea asISTextArea;
    private JTextArea toBeTextArea;
    private GridBagConstraints constraints;
    private String lastUsedFolder = ".";

    public GUI(Logic l) {
        setSize(700, 700);
        setTitle("Frame Test");
        setLayout(new GridBagLayout());
        logic = l;

        // Main Panel added
        constraints = new GridBagConstraints();
        JPanel mainPanel = new JPanel(new GridBagLayout());
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
        constraints.weightx = 1;
        getContentPane().add(mainPanel, constraints);

        // Button Panel added
        constraints = new GridBagConstraints();
        JPanel buttonPanel = new JPanel(new FlowLayout());
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 0, 5, 0);
        mainPanel.add(buttonPanel, constraints);

        buttonPanel.add(button("Open", actionFileChoose()));
        buttonPanel.add(button("Execute", actionExecute()));
        buttonPanel.add(button("Generate", actionFileGenerate()));

        // Textarea Panel added
        JPanel textAreaPanel = new JPanel(new GridBagLayout());
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.weightx = 1;
        constraints.weighty = 1;
        mainPanel.add(textAreaPanel , constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.weighty = 1;
        constraints.weightx = 1;
        constraints.insets = new Insets(10, 10, 10, 5);
        constraints.fill = GridBagConstraints.BOTH;
        JTextArea textArea = new JTextArea();
        textArea.setMinimumSize(new Dimension(400, 400));
        textAreaPanel.add(textArea, constraints);

        JTextArea textArea2 = new JTextArea();
        constraints.insets = new Insets(10, 5, 10, 10);
        constraints.gridx = 1;
        textAreaPanel.add(textArea2, constraints);

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

    private JTextArea textArea (int width, int height) {
        JTextArea jTextArea = new JTextArea();
        jTextArea.setPreferredSize(new Dimension(width, height));
        return jTextArea;
    }

    private JTextArea textAreaColRow (int col, int row) {
        return new JTextArea(col, row);
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
