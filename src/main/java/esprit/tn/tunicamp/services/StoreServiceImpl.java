package esprit.tn.tunicamp.services;

import esprit.tn.tunicamp.entities.Camper;
import esprit.tn.tunicamp.entities.Post;
import esprit.tn.tunicamp.entities.Product;
import esprit.tn.tunicamp.entities.Store;
import esprit.tn.tunicamp.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class StoreServiceImpl implements StoreService{
    @Override
    public List<Product> getStoreProducts(int cid) {
        return null;
    }

    @Override
    public List<Store> getStores() {
        List<Store> storeList = new ArrayList<>();
        String requete = "SELECT * FROM store";
        try{
            PreparedStatement st= MyConnection.getInstance().getConnection().prepareStatement(requete);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int storeId = rs.getInt("id");
                String name = rs.getString("name");
                int ownerId = rs.getInt("ownerId");

                Store store = new Store(storeId,name,ownerId,getAll(storeId));
                storeList.add(store);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return storeList;
    }

    private List<Product> getAll(int cid){
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
}
