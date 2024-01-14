package esprit.tn.tunicamp.services;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import esprit.tn.tunicamp.entities.*;

public interface UserService {
        User signUp(String username, String password, String url, UserType userType);

        User login(String username, String password);


        void addStripe(Customer c, User s) throws StripeException;

        String getId(User s);

    String getCC(User s);
}
