package co.edu.uptc.view.utilities;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TextDouble extends TextString {

    @Override
    protected void validateField() {
        String text = textField.getText().trim();
        if (text.isBlank()) {
            showError(i18n.get("error.field.empty"));
        } else if (!text.matches("^[0-9]+(\\.[0-9]+)?$")) {
            showError(i18n.get("error.field.decimal"));
        } else {
            clearError();
        }
    }
}
