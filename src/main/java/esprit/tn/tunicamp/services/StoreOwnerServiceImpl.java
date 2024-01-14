package esprit.tn.tunicamp.services;

import esprit.tn.tunicamp.entities.Activity;
import esprit.tn.tunicamp.entities.Product;
import esprit.tn.tunicamp.entities.Store;
import esprit.tn.tunicamp.entities.StoreOwner;
import esprit.tn.tunicamp.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreOwnerServiceImpl implements StoreOwnerService{
    @Override
    public List<Product> getOwnedProducts(int cid) {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT a.* FROM product a " +
                "JOIN storeproduct s ON a.id = s.productId " +
                "WHERE s.storeId = ?";

        try (PreparedStatement statement = MyConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, cid);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String img = resultSet.getString("img");
                    double price = resultSet.getDouble("price");

                    Product product = new Product(id , name , img , price);
                    productList.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving Products: " + e.getMessage());
        }

        return productList;
    }

    @Override
    public void addProductToStore(Store store, Product product) {

    }
}
