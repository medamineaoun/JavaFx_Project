package esprit.tn.tunicamp.services;

import esprit.tn.tunicamp.entities.Activity;
import esprit.tn.tunicamp.entities.CampingCenter;

import java.util.List;

public interface CCService {
    List<Activity> getCCActivities(int cid);
    List<CampingCenter> getCCs();

    Activity addActivity(int creator, String name, String time, String description, int maxPeople);
}
