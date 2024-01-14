package esprit.tn.tunicamp.services;

import esprit.tn.tunicamp.entities.ComplaintActivity;
import esprit.tn.tunicamp.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReclamationServiceImpl implements ReclamationService {
    @Override
    public String getStatus(int idAct) {
        String status = null;
        String query = "SELECT status FROM reclamation WHERE idAct = ?";

        try (PreparedStatement statement = MyConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, idAct);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    status = resultSet.getString("status");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving status for activity: " + e.getMessage());
        }

        return status;
    }

    @Override
    public List<ComplaintActivity> getAllComplainsForUser(int userId) {
        List<ComplaintActivity> complaintActivities = new ArrayList<>();
        String query = "SELECT a.*, c.status FROM activity a " +
                "LEFT JOIN reclamation c ON a.id = c.idAct " +
                "WHERE c.idUser = ?";

        try (PreparedStatement statement = MyConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int actId = resultSet.getInt("id");
                    int camperId = resultSet.getInt("creator");
                    String name = resultSet.getString("name");
                    String time = resultSet.getString("dateTime");
                    String desc = resultSet.getString("description");
                    int max = resultSet.getInt("maxPeople");
                    String status = resultSet.getString("status");

                    ComplaintActivity complaintActivity = new ComplaintActivity(actId, camperId, name, time, desc, max, status);
                    complaintActivities.add(complaintActivity);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving complaints for user: " + e.getMessage());
        }

        return complaintActivities;
    }



}
