package co.edu.uptc.view;

import javax.swing.JButton;
import javax.swing.JPanel;

import co.edu.uptc.view.base.BasePanel;

public class ContabilityPanel extends BasePanel {

    JButton addButton;
    JButton listButton;
    JButton exportButton;
    JButton btnExit;

    public ContabilityPanel() {
        addButton    = new JButton(i18n.get("btn.add"));
        listButton   = new JButton(i18n.get("btn.list"));
        exportButton = new JButton(i18n.get("btn.export"));
        btnExit      = new JButton(i18n.get("btn.back"));
        init();
    }

    @Override
    protected String sectionTitle() {
        return i18n.get("btn.accounting");
    }

    @Override
    protected void addButtons(JPanel panel) {
        addButton(panel, addButton);
        addButton(panel, listButton);
        addButton(panel, exportButton);
        addButton(panel, btnExit);
    }
}
