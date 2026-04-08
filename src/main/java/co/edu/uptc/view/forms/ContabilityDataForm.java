package co.edu.uptc.view.forms;

import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import co.edu.uptc.pojo.ContabilityData;
import co.edu.uptc.pojo.TransactionType;
import co.edu.uptc.view.MainFrame;
import co.edu.uptc.view.base.BaseForm;
import co.edu.uptc.view.utilities.TextDouble;
import co.edu.uptc.view.utilities.TextString;

public class ContabilityDataForm extends BaseForm {

    private TextString txtDescription;
    private TextDouble txtValue;
    private JComboBox<String> cbType;
    private JSpinner spDateTime;
    private final MainFrame mediator;

    public ContabilityDataForm(MainFrame parent) {
        super(parent, "form.title.movement");
        mediator = parent;
        setSize(380, 240);
        setLocationRelativeTo(parent);

        init();
    }

    @Override
    protected JPanel buildFormPanel() {
        txtDescription = new TextString();
        cbType = new JComboBox<>(new String[]{i18n.get("income"), i18n.get("expenses")});
        txtValue   = new TextDouble();
        spDateTime = new JSpinner(new SpinnerDateModel());
        spDateTime.setEditor(new JSpinner.DateEditor(spDateTime, "yyyy-MM-dd HH:mm"));

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 6));
        form.add(new JLabel(i18n.get("description"))); form.add(txtDescription);
        form.add(new JLabel(i18n.get("type")));        form.add(cbType);
        form.add(new JLabel(i18n.get("value")));       form.add(txtValue);
        form.add(new JLabel(i18n.get("date")));        form.add(spDateTime);
        return form;
    }

    @Override
    protected void setupActions() {
        btnSave.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> dispose());
    }

    private void onSave() {
        if (!txtDescription.isValidField() || !txtValue.isValidField()) {
            JOptionPane.showMessageDialog(this, i18n.get("error.presave"));
            return;
        }
        mediator.getPresenter().createEntity(buildMovement());
        dispose();
    }

    private ContabilityData buildMovement() {
        Date date = (Date) spDateTime.getValue();
        LocalDateTime dateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return new ContabilityData(txtDescription.getText(),
                                   toTransactionType(cbType.getSelectedIndex()),
                                   Double.parseDouble(txtValue.getText()),
                                   dateTime);
    }

    private TransactionType toTransactionType(int index) {
        return switch (index) {
            case 0 -> TransactionType.INCOME;
            case 1 -> TransactionType.EXPENSES;
            default -> null;
        };
    }
}
