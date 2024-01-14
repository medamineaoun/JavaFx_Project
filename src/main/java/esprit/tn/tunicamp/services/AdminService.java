package esprit.tn.tunicamp.services;

import esprit.tn.tunicamp.entities.*;

import java.util.List;

public interface AdminService {
    List<ApplicationRequest> getPendingRequests();

    void approveRequest(ApplicationRequest request);

    void denyRequest(ApplicationRequest request);


}