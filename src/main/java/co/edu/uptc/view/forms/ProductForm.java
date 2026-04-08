package co.edu.uptc.view.forms;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import co.edu.uptc.pojo.Product;
import co.edu.uptc.pojo.UnitOfMeasure;
import co.edu.uptc.view.MainFrame;
import co.edu.uptc.view.base.BaseForm;
import co.edu.uptc.view.utilities.TextDouble;
import co.edu.uptc.view.utilities.TextString;

public class ProductForm extends BaseForm {

    private TextString txtDescription;
    private TextDouble txtPrice;
    private JComboBox<String> cbUnit;
    private final MainFrame mediator;

    public ProductForm(MainFrame parent) {
        super(parent, "form.title.product");
        mediator = parent;
        setSize(360, 210);
        setLocationRelativeTo(parent);

        init();
    }

    @Override
    protected JPanel buildFormPanel() {
        txtDescription = new TextString();
        cbUnit = new JComboBox<>(new String[]{
            i18n.get("pound"), i18n.get("kilo"), i18n.get("sack"), i18n.get("ton")});
        txtPrice = new TextDouble();

        JPanel form = new JPanel(new GridLayout(3, 2, 8, 6));
        form.add(new JLabel(i18n.get("description"))); form.add(txtDescription);
        form.add(new JLabel(i18n.get("unit")));        form.add(cbUnit);
        form.add(new JLabel(i18n.get("price")));       form.add(txtPrice);
        return form;
    }

    @Override
    protected void setupActions() {
        btnSave.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> dispose());
    }

    private void onSave() {
        if (!txtDescription.isValidField() || !txtPrice.isValidField()) {
            JOptionPane.showMessageDialog(this, i18n.get("error.presave"));
            return;
        }
        List<String> errors = mediator.getPresenter().createEntity(buildProduct());
        if (errors == null) {
            dispose();
        } else {
            txtPrice.setError(errors.get(0));
        }
    }

    private Product buildProduct() {
        return new Product((int)System.currentTimeMillis(), txtDescription.getText(),
                           toUnitOfMeasure(cbUnit.getSelectedIndex()),
                           Double.parseDouble(txtPrice.getText()));
    }

    private UnitOfMeasure toUnitOfMeasure(int index) {
        return switch (index) {
            case 0 -> UnitOfMeasure.POUND;
            case 1 -> UnitOfMeasure.KILO;
            case 2 -> UnitOfMeasure.SACK;
            case 3 -> UnitOfMeasure.TON;
            default -> null;
        };
    }
}
