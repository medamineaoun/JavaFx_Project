package esprit.tn.tunicamp.services;

import esprit.tn.tunicamp.entities.Store;
import esprit.tn.tunicamp.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SettingsServiceImpl implements SettingsService{

    boolean test = true;
    boolean test2 = true;
    @Override
    public boolean submit(int id, String pass, String url){
        if (pass!=null){

            String updateQuery = "UPDATE user SET password = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(updateQuery)) {
                preparedStatement.setString(1, pass);
                preparedStatement.setInt(2, id);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Password updated successfully.");
                    test = true;
                } else {
                    System.out.println("No user found with the specified ID.");
                }
            } catch (SQLException e) {
                test = false;
                throw new RuntimeException(e);

            }



        }

        if (url!=null){
            if(checkURL(url)){

                String updateUrlQuery = "UPDATE user SET url = ? WHERE id = ?";
                try (PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(updateUrlQuery)) {
                    preparedStatement.setString(1, url);
                    preparedStatement.setInt(2, id);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("URL updated successfully.");
                        test2 = true;
                    } else {
                        System.out.println("No user found with the specified ID.");
                    }
                } catch (SQLException e) {
                    test = false;
                    throw new RuntimeException(e);

                }
            }else{
            test2 = false;}
        }
        return test && test2;
    }


    private boolean checkURL(String url) {
        String lowercaseUrl = url.toLowerCase();
        return lowercaseUrl.endsWith(".jpeg") || lowercaseUrl.endsWith(".jpg") || lowercaseUrl.endsWith(".png");

    }
}
