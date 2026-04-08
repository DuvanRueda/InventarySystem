package co.edu.uptc.view.base;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import co.edu.uptc.config.i18n.I18nConfig;

public abstract class BasePanel extends JPanel {

    protected I18nConfig i18n;

    protected BasePanel() {
        i18n = I18nConfig.getInstance();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
    }

    private JLabel buildHeader() {
        JLabel title = new JLabel(sectionTitle());
        title.setFont(title.getFont().deriveFont(Font.BOLD, 13f));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        return title;
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        addButtons(panel);
        return panel;
    }

    protected abstract String sectionTitle();

    protected abstract void addButtons(JPanel panel);

    protected void addButton(JPanel panel, javax.swing.JButton btn) {
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 36));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(3, 0, 3, 0),
                btn.getBorder()));
        panel.add(btn);
    }

    protected void init() {
        add(buildHeader(), BorderLayout.NORTH);
        add(buildButtonPanel(), BorderLayout.CENTER);
    }
}
