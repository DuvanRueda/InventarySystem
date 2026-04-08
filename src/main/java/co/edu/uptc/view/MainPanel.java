package co.edu.uptc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import co.edu.uptc.config.i18n.I18nConfig;

public class MainPanel extends JPanel {

    JButton btnPersons;
    JButton btnProducts;
    JButton btnAccounting;
    JButton btnExit;

    private final I18nConfig i18n;

    public MainPanel() {
        i18n = I18nConfig.getInstance();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        initButtons();
        buildLayout();
        setupActions();
    }

    private void initButtons() {
        btnPersons    = iconButton(i18n.get("btn.persons"),    "\u2022", new Color(0x1D9E75), new Color(0xE1F5EE));
        btnProducts   = iconButton(i18n.get("btn.products"),   "\u2022", new Color(0x185FA5), new Color(0xE6F1FB));
        btnAccounting = iconButton(i18n.get("btn.accounting"), "$",      new Color(0xBA7517), new Color(0xFAEEDA));
        btnExit       = iconButton(i18n.get("btn.exit"),       "\u00D7", new Color(0xA32D2D), new Color(0xFCEBEB));
    }

    private JButton iconButton(String text, String icon, Color iconFg, Color iconBg) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout(10, 0));
        btn.setBorderPainted(true);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 30), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        JLabel badge = new JLabel(icon, JLabel.CENTER);
        badge.setOpaque(true);
        badge.setBackground(iconBg);
        badge.setForeground(iconFg);
        badge.setFont(badge.getFont().deriveFont(Font.BOLD, 13f));
        badge.setPreferredSize(new Dimension(26, 26));
        badge.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(13f));

        btn.add(badge, BorderLayout.WEST);
        btn.add(label, BorderLayout.CENTER);
        return btn;
    }

    private void buildLayout() {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
        btnPanel.setOpaque(false);

        for (JButton btn : new JButton[]{btnPersons, btnProducts, btnAccounting, btnExit}) {
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
            btnPanel.add(btn);
            btnPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        }

        add(btnPanel, BorderLayout.CENTER);
    }

    private void setupActions() {
        btnExit.addActionListener(e -> System.exit(0));
    }
}
