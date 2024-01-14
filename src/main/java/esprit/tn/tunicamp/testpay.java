package esprit.tn.tunicamp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.*;
import com.stripe.exception.StripeException;
import com.stripe.model.*;

import java.util.HashMap;
import java.util.Map;

public class testpay {
    public static void main(String[] args) throws StripeException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Stripe.apiKey = "sk_test_51OSy4OJrOPZ5OJwNhpq5v2E81ksiY1mSgr6CEqZJPZcPsSLZsfTvU30sJdnlvbeaRtB5dV89CtiiMPfMQa0SpMtj00rQBO2C0w";

        Map<String, Object> params = new HashMap<>();
        //params.put("limit",3);
        params.put("email","t@gmail.com");
        Customer c = Customer.create(params);
        System.out.println(c.getId());
    }
}
