package co.edu.uptc.view;

import javax.swing.*;

import co.edu.uptc.config.LoadConfig;
import co.edu.uptc.config.i18n.I18nConfig;
import co.edu.uptc.interfaces.IPresenter;
import co.edu.uptc.interfaces.IView;
import co.edu.uptc.pojo.TransactionType;
import co.edu.uptc.pojo.UnitOfMeasure;
import co.edu.uptc.view.forms.ContabilityDataForm;
import co.edu.uptc.view.forms.PersonForm;
import co.edu.uptc.view.forms.ProductForm;
import lombok.Getter;

import java.awt.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.function.Supplier;

@Getter
public class MainFrame extends JFrame implements IView {

    private IPresenter presenter;
    private CardLayout layout;
    private JPanel container;
    private MainPanel mainMenu;
    private PersonsPanel persons;
    private ProductsPanel products;
    private ContabilityPanel contability;
    private I18nConfig i18n;

    public MainFrame() {
        LoadConfig config = LoadConfig.getInstance();
        i18n = I18nConfig.getInstance(config.get("lan"));
        initComponents();
        addPanels();
        addActionsMain();
        addActionsPersons();
        addActionsProducts();
        addActionsCont();
    }

    private void initComponents() {
        setTitle("Main Menu");
        setSize(340, 280);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        layout      = new CardLayout();
        container   = new JPanel(layout);
        mainMenu    = new MainPanel();
        persons     = new PersonsPanel();
        products    = new ProductsPanel();
        contability = new ContabilityPanel();
    }

    private void addPanels() {
        container.add(mainMenu,    "main");
        container.add(persons,     "persons");
        container.add(products,    "products");
        container.add(contability, "contability");
        add(container);
    }

    private void addActionsMain() {
        mainMenu.btnPersons.addActionListener(e    -> layout.show(container, "persons"));
        mainMenu.btnProducts.addActionListener(e   -> layout.show(container, "products"));
        mainMenu.btnAccounting.addActionListener(e -> layout.show(container, "contability"));
    }

    private void addActionsPersons() {
        persons.addButton.addActionListener(e    -> new PersonForm(this));

        persons.listButton.addActionListener(e   -> openTable(
                presenter.getListPeople(),
                new String[]{i18n.get("col.names"), i18n.get("col.lastnames"),
                             i18n.get("col.gender"), i18n.get("col.age")},
                p -> new Object[]{p.getName(), p.getLastName(),
                                  p.getGender(), (Period.between(p.getBirthDate(), LocalDate.now()).getYears())}));

        persons.deleteButton.addActionListener(e -> showRemoved(presenter.removePerson()));
        persons.exportButton.addActionListener(e -> handleExport(
                presenter::exportPeople, presenter.getListPeople().isEmpty()));
        persons.btnExit.addActionListener(e      -> layout.show(container, "main"));
    }

    private void addActionsProducts() {
        products.addButton.addActionListener(e   -> new ProductForm(this));
        products.listButton.addActionListener(e  -> openTable(
                presenter.getListProduct(),
                new String[]{i18n.get("col.id"), i18n.get("col.description"),
                             i18n.get("col.unit.type"), i18n.get("col.price")},
                p -> new Object[]{p.getId(), p.getDescription(),
                                  toLocalUnit(p.getUnitOfMeasure()), p.getPrice()}));
        products.deleteButton.addActionListener(e -> showRemoved(presenter.removeProduct()));
        products.exportButton.addActionListener(e -> handleExport(
                presenter::exportProducts, presenter.getListProduct().isEmpty()));
        products.btnExit.addActionListener(e     -> layout.show(container, "main"));
    }

    private void addActionsCont() {
        contability.addButton.addActionListener(e   -> new ContabilityDataForm(this));
        contability.listButton.addActionListener(e  -> openAccountingTable());
        contability.exportButton.addActionListener(e -> handleExport(
                presenter::exportAccounting, presenter.getListCData().isEmpty()));
        contability.btnExit.addActionListener(e     -> layout.show(container, "main"));
    }

    private void openAccountingTable() {
        String totalLabel = String.format("%s  $ %.2f",
                i18n.get("col.net.total"), presenter.getNetTotal());
        new TableFrame<>(this,
                presenter.getListCData(),
                new String[]{i18n.get("col.description"), i18n.get("col.ctType"),
                             i18n.get("col.value"), i18n.get("col.date")},
                c -> new Object[]{c.getDescription(), toLocalType(c.getTransactionType()),
                                  c.getTransactionValue(), c.getDate().toString()},
                totalLabel);
    }

    private <T> void openTable(java.util.List<T> list, String[] cols,
                               co.edu.uptc.view.interfaces.Mapper<T> mapper) {
        new TableFrame<>(this, list, cols, mapper);
    }

    private void handleExport(Supplier<String> action, boolean isEmpty) {
        if (isEmpty) {
            JOptionPane.showMessageDialog(this, i18n.get("msg.export.empty"));
            return;
        }
        String folder = action.get();
        JOptionPane.showMessageDialog(this,i18n.get("msg.export.success").replace("{0}", folder));
    }

    private void showRemoved(Object removed) {
        if (removed == null)
            JOptionPane.showMessageDialog(this, i18n.get("msg.list.empty"));
        else
            JOptionPane.showMessageDialog(this,
                    i18n.get("msg.removed").replace("{0}", removed.toString()));
    }

    private String toLocalUnit(UnitOfMeasure unit) {
        return switch (unit) {
            case POUND -> i18n.get("pound");
            case SACK  -> i18n.get("sack");
            case KILO  -> i18n.get("kilo");
            case TON   -> i18n.get("ton");
        };
    }

    private String toLocalType(TransactionType type) {
        return switch (type) {
            case INCOME   -> i18n.get("income");
            case EXPENSES -> i18n.get("expenses");
        };
    }

    @Override public void setPresenter(IPresenter presenter) { this.presenter = presenter; }
    @Override public void start() { setVisible(true); }
}
