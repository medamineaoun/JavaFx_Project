package esprit.tn.tunicamp.services;

import esprit.tn.tunicamp.entities.*;

import java.util.List;

public interface StoreOwnerService {
    List<Product> getOwnedProducts(int cid);

    void addProductToStore(Store store, Product product);
}