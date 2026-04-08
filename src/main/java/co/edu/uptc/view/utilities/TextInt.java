package co.edu.uptc.view.utilities;

import javax.swing.BorderFactory;
import lombok.NoArgsConstructor;
import java.awt.Color;

@NoArgsConstructor
public class TextInt extends TextString {

    @Override
    protected void validateField() {
        String text = textField.getText().trim();
        if (text.isBlank()) {
            showError(i18n.get("error.field.empty"));
        } else if (!text.matches("^[0-9]+$")) {
            showError(i18n.get("error.field.integer"));
        } else {
            clearError();
        }
    }
}
