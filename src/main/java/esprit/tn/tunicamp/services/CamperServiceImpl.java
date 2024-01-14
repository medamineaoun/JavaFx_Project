package esprit.tn.tunicamp.services;

import esprit.tn.tunicamp.entities.*;
import esprit.tn.tunicamp.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CamperServiceImpl implements CamperService {

    @Override
    public Camper getCamperById(long camperId) throws SQLException {
        Camper camper = null;


            String selectQuery = "SELECT id, username, password, userType FROM users WHERE id = ?";
            try (PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(selectQuery)) {
                preparedStatement.setLong(1, camperId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String username = resultSet.getString("username");
                        String password = resultSet.getString("password");
                        String url = resultSet.getString("url");
                        UserType userType = UserType.valueOf(resultSet.getString("userType"));

                        camper = new Camper(id, username, password,url, userType);
                    }
                }
            }


        return camper;
    }


    @Override
    public List<Post> getRecentPosts(Camper camper) {
        return null;
    }

    @Override
    public List<CampingCenter> getAllCampingCenters() {
        return null;
    }

    @Override
    public List<Activity> getAllActivities(int cid) {
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
    public List<Activity> getAllActivitiesEXP(int cid) {
        List<Activity> activities = new ArrayList<>();
        String query = "SELECT * FROM activity a " +
                "WHERE a.creator != ? " +
                "AND NOT EXISTS (" +
                "    SELECT 1 FROM activity_signups s " +
                "    WHERE s.activity_id = a.id " +
                "    AND s.user_id = ?" +
                ")";

        try (PreparedStatement statement = MyConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, cid);
            statement.setInt(2, cid);

            try (ResultSet rs = statement.executeQuery()) {
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
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving activities: " + e.getMessage());
        }

        return activities;
    }


    @Override
    public List<Activity> getAllJoinedActivities(int cid) {
        List<Activity> joinedActivities = new ArrayList<>();
        String query = "SELECT a.* FROM activity a " +
                "JOIN activity_signups s ON a.id = s.activity_id " +
                "WHERE s.user_id = ?";

        try (PreparedStatement statement = MyConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, cid);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int actId = resultSet.getInt("id");
                    int camperId = resultSet.getInt("creator");
                    String name = resultSet.getString("name");
                    String time = resultSet.getString("dateTime");
                    String desc = resultSet.getString("description");
                    int max = resultSet.getInt("maxPeople");

                    Activity activity = new Activity(actId, camperId, name, time, desc, max);
                    joinedActivities.add(activity);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving joined activities: " + e.getMessage());
        }

        return joinedActivities;
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

    @Override
    public List<Product> getAvailableProducts(Store s) {
        return null;
    }

    @Override
    public void applyForStoreOwner(User user) {

    }

    @Override
    public void applyForCampingCenterOwner(User user) {

    }

    public void signUpForActivity(int userId, int activityId) {
        String query = "INSERT INTO activity_signups (user_id, activity_id) VALUES (?, ?)";

        try (PreparedStatement statement = MyConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, activityId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error signing up for activity: " + e.getMessage());
        }
    }

    public void cancelSignUpForActivity(int userId, int activityId) {
        String query = "DELETE FROM activity_signups WHERE user_id = ? AND activity_id = ?";

        try (PreparedStatement statement = MyConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, activityId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error canceling signup for activity: " + e.getMessage());
        }
    }

    public int getSignupCountForActivity(int activityId) {
        String query = "SELECT COUNT(*) FROM activity_signups WHERE activity_id = ?";

        try (PreparedStatement statement = MyConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, activityId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving signup count for activity: " + e.getMessage());
        }

        return 0;
    }

    @Override
    public void deleteActivityById(int activityId) {
        String query = "DELETE FROM activity WHERE id = ?";

        try (PreparedStatement statement = MyConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, activityId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting activity by ID: " + e.getMessage());
        }
    }

    @Override
    public boolean reportActivity(int id, int ida) {
        if (!appExists(ida)) {

            String insertQuery = "INSERT INTO reclamation (idUser, idAct) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, ida);
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

        String selectQuery = "SELECT * FROM reclamation WHERE idAct = ?";
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

    @Override
    public Activity getActivityById(int activityId) {
        Activity activity = null;
        String query = "SELECT * FROM activity WHERE id = ?";

        try (PreparedStatement statement = MyConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, activityId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int actId = resultSet.getInt("id");
                    int camperId = resultSet.getInt("creator");
                    String name = resultSet.getString("name");
                    String time = resultSet.getString("dateTime");
                    String desc = resultSet.getString("description");
                    int max = resultSet.getInt("maxPeople");

                    activity = new Activity(actId, camperId, name, time, desc, max);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving activity by ID: " + e.getMessage());
        }

        return activity;
    }

}