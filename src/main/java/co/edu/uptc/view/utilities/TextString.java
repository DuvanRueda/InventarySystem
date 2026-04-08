package co.edu.uptc.view.utilities;

import javax.swing.*;
import javax.swing.border.Border;

import co.edu.uptc.config.i18n.I18nConfig;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TextString extends JPanel {

    protected JTextField textField;
    protected JLabel errorLabel;
    protected Border defaultBorder;
    protected I18nConfig i18n;

    public TextString() {
        i18n = I18nConfig.getInstance();
        initComponents();
        setupLayout();
        setupValidation();
    }

    private void initComponents() {
        setLayout(new BorderLayout(5, 2));
        textField = new JTextField();
        defaultBorder = textField.getBorder();
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 10));
    }

    private void setupLayout() {
        add(textField, BorderLayout.CENTER);
        add(errorLabel, BorderLayout.SOUTH);
    }

    private void setupValidation() {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validateField();
            }
        });
    }

    protected void validateField() {
        String text = textField.getText().trim();
        if (text.isEmpty()) {
            showError(i18n.get("error.field.empty"));
        } else {
            clearError();
        }
    }

    protected void showError(String message) {
        errorLabel.setText(message);
        textField.setBorder(BorderFactory.createLineBorder(Color.RED));
    }

    protected void clearError() {
        errorLabel.setText("");
        textField.setBorder(defaultBorder);
    }

    public boolean isValidField() {
        validateField();
        return errorLabel.getText().isBlank();
    }

    public String getText() {
        return textField.getText().trim();
    }

    public void setError(String text) {
        if (text != null && !text.isBlank()) showError(text);
        else clearError();
    }
}
