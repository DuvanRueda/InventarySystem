package co.edu.uptc.view.base;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import co.edu.uptc.config.i18n.I18nConfig;

public abstract class BaseForm extends JDialog {

    protected I18nConfig i18n;
    protected JButton btnSave;
    protected JButton btnCancel;

    protected BaseForm(JFrame parent, String titleKey) {
        super(parent, "", true);
        i18n = I18nConfig.getInstance();
        setTitle(i18n.get(titleKey));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void buildLayout() {
        setLayout(new BorderLayout(10, 10));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));

        btnSave   = new JButton(i18n.get("btn.save"));
        btnCancel = new JButton(i18n.get("btn.cancel"));

        add(buildFormPanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        panel.add(btnCancel);
        panel.add(btnSave);
        return panel;
    }

    protected void init() {
        setLayout(new BorderLayout(10, 10));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));

        btnSave   = new JButton(i18n.get("btn.save"));
        btnCancel = new JButton(i18n.get("btn.cancel"));

        add(buildFormPanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);

        setupActions();

        setVisible(true);
    }

    protected abstract JPanel buildFormPanel();

    protected abstract void setupActions();
}
