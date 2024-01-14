package esprit.tn.tunicamp.services;

import esprit.tn.tunicamp.entities.ComplaintActivity;

import java.util.List;

public interface ReclamationService {
    String getStatus(int idAct);


    List<ComplaintActivity> getAllComplainsForUser(int userId);
}
