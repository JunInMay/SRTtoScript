import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.prefs.Preferences;

public class GUI extends JFrame {
    private static String filePath = "testFileLocation";
    private static String fileName;
    private static String extension = ".txt";

    private Logic logic;
    private File selectedFile;
    private JTextArea asIsTextArea;
    private JTextArea toBeTextArea;
    private String lastUsedFolder = ".";
    private boolean fileOpened = false;

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
        buttonPanel.add(button("Generate", actionFileGenerate()));

        // Textarea Panel added
        JPanel textAreaPanel = new JPanel(new GridBagLayout());
        mainPanel.add(textAreaPanel, Constraints.TEXTAREA_PANEL_CONSTRAINTS);

        // two text areas added
        asIsTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(asIsTextArea);
        textAreaPanel.add(scrollPane, Constraints.ASIS_TEXTAREA_CONSTRAINTS);

        toBeTextArea = new JTextArea();
        scrollPane = new JScrollPane(toBeTextArea);
        textAreaPanel.add(scrollPane, Constraints.TOBE_TEXTAREA_CONSTRAINTS);

        // menu bar
        JMenuBar menuBar = new JMenuBar();
        getContentPane().add(menuBar);
        JMenu menu = new JMenu("Menu");

        JMenuItem openItem = new JMenuItem("test");
        menuBar.add(menu);
        menu.add(menuItem("Open", actionFileChoose()));
        menu.add(menuItem("Generate", actionFileGenerate()));

        setJMenuBar(menuBar);
    }

    public void engage () {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JButton button (String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(80, 20));
        button.addActionListener(actionListener);
        button.setMargin(new Insets(1, 1, 1, 1));

        return button;
    }

    private JMenuItem menuItem (String text, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(actionListener);

        return menuItem;
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

                    StringBuilder content = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))){
                        String line;
                        while ((line = reader.readLine()) != null) {
                            content.append(line).append(System.lineSeparator());
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    String fileNameWithExtension = selectedFile.getName();

                    // Separate file name and extension
                    int dotIndex = fileNameWithExtension.lastIndexOf('.');
                    if(dotIndex >= 0) {
                        // Get file name without extension
                        fileName = fileNameWithExtension.substring(0, dotIndex);
                    } else {
                        fileName = fileNameWithExtension;
                    }

                    asIsTextArea.setText(content.toString());
                    toBeTextArea.setText(logic.discernSentence(selectedFile));
                    fileOpened = true;
                }
            }
        };
    }

    private ActionListener actionFileGenerate() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileOpened) {
                    logic.makeFile(filePath, fileName, extension, asIsTextArea.getText());
                } else {
                    // Alert
                }
            }
        };
    }

}
