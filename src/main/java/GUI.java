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
    private String lastUsedFolder = ".";

    public GUI(Logic l) {
        setSize(700, 700);
        setTitle("Frame Test");
        setLayout(new GridBagLayout());
        logic = l;

        // Main Panel added
        JPanel mainPanel = new JPanel(new GridBagLayout());
        getContentPane().add(mainPanel, Constraints.MAIN_PANEL_CONSTRAINTS);

        // Button Panel added
        JPanel buttonPanel = new JPanel(new FlowLayout());
        mainPanel.add(buttonPanel, Constraints.BUTTON_PANEL_CONSTRAINTS);

        buttonPanel.add(button("Open", actionFileChoose()));
        buttonPanel.add(button("Execute", actionExecute()));
        buttonPanel.add(button("Generate", actionFileGenerate()));

        // Textarea Panel added
        JPanel textAreaPanel = new JPanel(new GridBagLayout());
        mainPanel.add(textAreaPanel, Constraints.TEXTAREA_PANEL_CONSTRAINTS);

        // two text areas added
        JTextArea textArea = new JTextArea();
        textArea.setMinimumSize(new Dimension(400, 400));
        textAreaPanel.add(textArea, Constraints.ASIS_TEXTAREA_CONSTRAINTS);

        JTextArea textArea2 = new JTextArea();
        textAreaPanel.add(textArea2, Constraints.TOBE_TEXTAREA_CONSTRAINTS);

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
