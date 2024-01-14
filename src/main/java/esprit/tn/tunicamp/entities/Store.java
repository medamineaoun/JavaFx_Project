package esprit.tn.tunicamp.entities;

import java.util.List;

public class Store {
    private int id;
    private String name;
    private int ownerId;
    private List<Product> products;

    public Store(int id, String name, int ownerId, List<Product> products) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}