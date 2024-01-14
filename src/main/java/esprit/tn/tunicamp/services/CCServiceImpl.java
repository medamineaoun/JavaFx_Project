package esprit.tn.tunicamp.services;

import esprit.tn.tunicamp.entities.Activity;
import esprit.tn.tunicamp.entities.CampingCenter;
import esprit.tn.tunicamp.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CCServiceImpl implements CCService{
    @Override
    public List<Activity> getCCActivities(int cid) {
        List<Activity> activities = new ArrayList<>();
        String requete = "SELECT * FROM activity WHERE creator ="+ cid;
        try{
            PreparedStatement st=MyConnection.getInstance().getConnection().prepareStatement(requete);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                int actId = rs.getInt("id");
                int creator = rs.getInt("creator");
                String name = rs.getString("name");
                String time = rs.getString("dateTime");
                String desc = rs.getString("description");
                int max = rs.getInt("maxPeople");

                Activity activity = new Activity(actId, creator, name, time, desc, max);
                activities.add(activity);
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return activities;
    }

    @Override
    public List<CampingCenter> getCCs() {
        List<CampingCenter> campingCenters = new ArrayList<>();
        String requete = "SELECT * FROM campingcenter";
        try{
            PreparedStatement st= MyConnection.getInstance().getConnection().prepareStatement(requete);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String loc = rs.getString("location");
                int ownerId = rs.getInt("ownerId");

                CampingCenter center = new CampingCenter(id,name,loc,ownerId);
                campingCenters.add(center);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return campingCenters;
    }

    @Override
    public Activity addActivity(int creator, String name, String time, String description, int maxPeople) {
        String insertQuery = "INSERT INTO activity (creator, name,dateTime, description,maxPeople) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, creator);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, time);
            preparedStatement.setString(4, description);
            preparedStatement.setInt(5, maxPeople);
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return new Activity(id, creator, name, time, description,maxPeople);
                } else {
                    throw new SQLException("Failed");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
