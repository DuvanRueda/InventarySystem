package co.edu.uptc.view.forms;

import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import co.edu.uptc.pojo.Person;
import co.edu.uptc.view.MainFrame;
import co.edu.uptc.view.base.BaseForm;
import co.edu.uptc.view.utilities.TextString;

public class PersonForm extends BaseForm {

    private TextString txtNames;
    private TextString txtLastNames;
    private JComboBox<String> cbGender;
    private JSpinner spBirthDate;
    private final MainFrame mediator;

    public PersonForm(MainFrame parent) {
        super(parent, "form.title.person");
        mediator = parent;
        setSize(380, 260);
        setLocationRelativeTo(parent);

        init();
    }

    @Override
    protected JPanel buildFormPanel() {
        txtNames     = new TextString();
        txtLastNames = new TextString();
        cbGender     = new JComboBox<>(new String[]{i18n.get("male"), i18n.get("female")});
        spBirthDate  = new JSpinner(new SpinnerDateModel());
        spBirthDate.setEditor(new JSpinner.DateEditor(spBirthDate, "yyyy-MM-dd"));

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 6));
        form.add(new JLabel(i18n.get("names")));      form.add(txtNames);
        form.add(new JLabel(i18n.get("lastnames")));  form.add(txtLastNames);
        form.add(new JLabel(i18n.get("gender")));     form.add(cbGender);
        form.add(new JLabel(i18n.get("birth.day")));  form.add(spBirthDate);
        return form;
    }

    @Override
    protected void setupActions() {
        btnSave.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> dispose());
    }

    private void onSave() {
        if (!txtNames.isValidField() || !txtLastNames.isValidField()) {
            JOptionPane.showMessageDialog(this, i18n.get("error.presave"));
            return;
        }
        List<String> errors = mediator.getPresenter().createEntity(buildPerson());
        if (errors == null) {
            dispose();
        } else {
            txtNames.setError(errors.get(0));
            txtLastNames.setError(errors.get(1));
        }
    }

    private Person buildPerson() {
        Date date = (Date) spBirthDate.getValue();
        LocalDate birthDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new Person((int)System.currentTimeMillis(), txtNames.getText(), txtLastNames.getText(),
                          (String) cbGender.getSelectedItem(), birthDate);
    }
}
