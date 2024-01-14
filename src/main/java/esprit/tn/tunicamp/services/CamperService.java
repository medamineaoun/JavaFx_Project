package esprit.tn.tunicamp.services;

import esprit.tn.tunicamp.entities.*;

import java.sql.SQLException;
import java.util.List;

public interface CamperService {

    Camper getCamperById(long camperId) throws SQLException;
    List<Post> getRecentPosts(Camper camper);

    List<CampingCenter> getAllCampingCenters();

    List<Activity> getAllActivities(int cid);
    List<Activity> getAllActivitiesEXP(int cid);

    List<Activity> getAllJoinedActivities(int cid);

    Activity addActivity( int creator, String name, String time, String description, int maxPeople);
    List<Product> getAvailableProducts(Store s);

    void applyForStoreOwner(User user);

    void applyForCampingCenterOwner(User user);
    void signUpForActivity(int userId, int activityId);
    void cancelSignUpForActivity(int userId, int activityId);
    int getSignupCountForActivity(int activityId);
    void deleteActivityById(int activityId);

    boolean reportActivity(int id, int ida);

    Activity getActivityById(int activityId);
}