package esprit.tn.tunicamp.services;

import esprit.tn.tunicamp.entities.CampingCenter;
import esprit.tn.tunicamp.entities.User;
import esprit.tn.tunicamp.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CCAppServiceImpl implements CCAppService{

    @Override
    public boolean submit(CampingCenter cc){
        if (!appExists(cc.getOwnerId())) {

            String insertQuery = "INSERT INTO applicationrequest (userId, requestedUserType) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, cc.getOwnerId());
                preparedStatement.setString(2, "CampingCenter");
                preparedStatement.executeUpdate();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return true;
                    } else {
                        throw new SQLException("Failed");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return false;
    }


    private boolean appExists(int id) {

        String selectQuery = "SELECT * FROM applicationrequest WHERE userId = ?";
        try (PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
