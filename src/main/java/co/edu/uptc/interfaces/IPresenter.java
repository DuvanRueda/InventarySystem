package co.edu.uptc.interfaces;

import java.util.List;

import co.edu.uptc.pojo.ContabilityData;
import co.edu.uptc.pojo.Person;
import co.edu.uptc.pojo.Product;

public interface IPresenter {
    List<String> createEntity(Object entity);
    List<Person> getListPeople();
    List<Product> getListProduct();
    List<ContabilityData> getListCData();
    Person removePerson();
    Product removeProduct();
    String exportPeople();
    String exportProducts();
    String exportAccounting();
    double getNetTotal();
}
