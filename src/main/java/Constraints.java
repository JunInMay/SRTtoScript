import java.awt.*;

public class Constraints {
    static final GridBagConstraints MAIN_PANEL_CONSTRAINTS = new GridBagConstraints();
    static final GridBagConstraints BUTTON_PANEL_CONSTRAINTS = new GridBagConstraints();
    static final GridBagConstraints TEXTAREA_PANEL_CONSTRAINTS = new GridBagConstraints();
    static final GridBagConstraints ASIS_TEXTAREA_CONSTRAINTS = new GridBagConstraints();
    static final GridBagConstraints TOBE_TEXTAREA_CONSTRAINTS = new GridBagConstraints();

    static {
        // MAIN PANEL
        MAIN_PANEL_CONSTRAINTS.fill = GridBagConstraints.BOTH;
        MAIN_PANEL_CONSTRAINTS.weightx = 1;
        MAIN_PANEL_CONSTRAINTS.weighty = 1;

        // BUTTON PANEL
        BUTTON_PANEL_CONSTRAINTS.fill = GridBagConstraints.BOTH;
        BUTTON_PANEL_CONSTRAINTS.gridy = 0;
        BUTTON_PANEL_CONSTRAINTS.insets = new Insets(5, 0, 5, 0);

        // TEXTAREA PANEL
        TEXTAREA_PANEL_CONSTRAINTS.fill = GridBagConstraints.BOTH;
        TEXTAREA_PANEL_CONSTRAINTS.gridy = 1;
        TEXTAREA_PANEL_CONSTRAINTS.insets = new Insets(0, 0, 0, 0);
        TEXTAREA_PANEL_CONSTRAINTS.weightx = 1;
        TEXTAREA_PANEL_CONSTRAINTS.weighty = 1;

        // ASIS TEXTAREA
        ASIS_TEXTAREA_CONSTRAINTS.gridx = 0;
        ASIS_TEXTAREA_CONSTRAINTS.weighty = 1;
        ASIS_TEXTAREA_CONSTRAINTS.weightx = 1;
        ASIS_TEXTAREA_CONSTRAINTS.insets = new Insets(10, 10, 10, 5);
        ASIS_TEXTAREA_CONSTRAINTS.fill = GridBagConstraints.BOTH;

        // TOBE TEXTAREA
        TOBE_TEXTAREA_CONSTRAINTS.gridx = 1;
        TOBE_TEXTAREA_CONSTRAINTS.weighty = 1;
        TOBE_TEXTAREA_CONSTRAINTS.weightx = 1;
        TOBE_TEXTAREA_CONSTRAINTS.insets = new Insets(10, 5, 10, 10);
        TOBE_TEXTAREA_CONSTRAINTS.fill = GridBagConstraints.BOTH;
    }

}
