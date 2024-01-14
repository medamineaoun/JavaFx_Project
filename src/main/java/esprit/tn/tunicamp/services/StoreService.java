package esprit.tn.tunicamp.services;

import esprit.tn.tunicamp.entities.Product;
import esprit.tn.tunicamp.entities.Store;

import java.util.List;

public interface StoreService {
    List<Product> getStoreProducts(int cid);
    List<Store> getStores();
}
