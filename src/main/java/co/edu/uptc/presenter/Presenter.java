package co.edu.uptc.presenter;

import java.util.Date;
import java.util.List;

import co.edu.uptc.config.LoadConfig;
import co.edu.uptc.config.i18n.I18nConfig;
import co.edu.uptc.interfaces.IPresenter;
import co.edu.uptc.model.AccountingCalculator;
import co.edu.uptc.model.ManagerCollection;
import co.edu.uptc.model.validator.PersonValidator;
import co.edu.uptc.model.validator.ProductValidator;
import co.edu.uptc.pojo.ContabilityData;
import co.edu.uptc.pojo.Person;
import co.edu.uptc.pojo.Product;
import co.edu.uptc.util.CSVUtil;
import co.edu.uptc.util.DateFormatter;
import co.edu.uptc.util.MessageResolver;

public class Presenter implements IPresenter {

    private static final String PEOPLE_EXPORT_PATH   = "data/people/people_export{0}.csv";
    private static final String PRODUCTS_EXPORT_PATH = "data/products/products_export{0}.csv";
    private static final String ACCOUNTING_PATH      = "data/accounting/accounting.csv";

    private final ManagerCollection<Person> people;
    private final ManagerCollection<Product> products;
    private final ManagerCollection<ContabilityData> accounting;
    private final AccountingCalculator accountingCalculator;
    private final MessageResolver messageResolver;

    public Presenter() {
        LoadConfig config = LoadConfig.getInstance();
        I18nConfig i18n   = I18nConfig.getInstance();
        messageResolver      = new MessageResolver(i18n);
        accountingCalculator = new AccountingCalculator();

        people     = new ManagerCollection<>((list, p) -> list.addLast(p),  new PersonValidator(config));
        products   = new ManagerCollection<>((list, p) -> list.addFirst(p), new ProductValidator(config));
        accounting = new ManagerCollection<>((list, c) -> list.addLast(c),  c -> null);

        loadAccountingFromFile();
    }

    private void loadAccountingFromFile() {
        CSVUtil.importFromCsv(ACCOUNTING_PATH, ContabilityData.class)
               .forEach(record -> accounting.add(record));
    }

    @Override
    public List<String> createEntity(Object entity) {
        if (entity instanceof Person person) {
            List<String> errors = people.add(person);
            return errors != null ? messageResolver.resolve(errors) : null;
        }
        if (entity instanceof Product product) {
            List<String> errors = products.add(product);
            return errors != null ? messageResolver.resolve(errors) : null;
        }
        List<String> errors = accounting.add((ContabilityData) entity);
        if (errors == null) CSVUtil.exportToCsv(ACCOUNTING_PATH, accounting.listObjects());
        return errors;
    }

    @Override public List<Person>          getListPeople()   { return people.listObjects(); }
    @Override public List<Product>         getListProduct()  { return products.listObjects(); }
    @Override public List<ContabilityData> getListCData()    { return accounting.listObjects(); }
    @Override public Person                removePerson()    { return people.remove(); }
    @Override public Product               removeProduct()   { return products.remove(); }

    @Override
    public double getNetTotal() {
        return accountingCalculator.calculateNetTotal(accounting.listObjects());
    }

    @Override
    public String exportPeople() {
        String path = PEOPLE_EXPORT_PATH.replace("{0}", DateFormatter.getDateNow());
        CSVUtil.exportToCsv(path, people.listObjects());
        return path;
    }

    @Override
    public String exportProducts() {
        String path = PRODUCTS_EXPORT_PATH.replace("{0}", DateFormatter.getDateNow());
        CSVUtil.exportToCsv(path, products.listObjects());
        return  path;
    }

    @Override
        public String exportAccounting() {
        String path = ACCOUNTING_PATH;
        CSVUtil.exportToCsv(ACCOUNTING_PATH, accounting.listObjects());
        return path;
    }
}
