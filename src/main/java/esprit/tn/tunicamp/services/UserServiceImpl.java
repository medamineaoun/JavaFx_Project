package esprit.tn.tunicamp.services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import esprit.tn.tunicamp.entities.User;
import esprit.tn.tunicamp.entities.UserType;
import esprit.tn.tunicamp.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UserServiceImpl implements UserService {

    @Override
    public User signUp(String username, String password, String url, UserType userType) {
        if (!userExists(username)) {

                String insertQuery = "INSERT INTO user (username, password,url, userType) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    preparedStatement.setString(4, url);
                    preparedStatement.setString(3, userType.name());
                    preparedStatement.executeUpdate();
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int userId = generatedKeys.getInt(1);
                            return new User(userId, username, password, url, userType);
                        } else {
                            throw new SQLException("Failed");
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

        }
        return null;
    }


    @Override
    public User login(String username, String password) {

            String selectQuery = "SELECT * FROM user WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(selectQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int userId = resultSet.getInt("id");
                        String url = resultSet.getString("url");
                        UserType userType = UserType.valueOf(resultSet.getString("userType"));
                        return new User(userId, username, password, url,userType);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        return null;
    }


    private static String generateRandomCVV() {
        Random random = new Random();
        return String.format("%03d", random.nextInt(1000));
    }

    private static String generateRandomExpirationDate() {
        Random random = new Random();
        int currentYear = 2023;
        int randomYear = currentYear + random.nextInt(5);
        int randomMonth = random.nextInt(12) + 1;

        return String.format("%02d/%d", randomMonth, randomYear);
    }
    @Override
    public void addStripe(Customer c,User s) throws StripeException {


        String insertQuery = "INSERT INTO stripeusers (id, identifier, cc) VALUES (?, ?, ?)";

        String CARD_PREPEND = "400141";
        int CARD_LENGTH = 16;

        String full = "";

        String creditCard = "4242424242424242";

        String exp = generateRandomExpirationDate();
        String ccv = generateRandomCVV();

        String[] exp2 = exp.split("/");



        Stripe.apiKey = "sk_test_51OSy4OJrOPZ5OJwNhpq5v2E81ksiY1mSgr6CEqZJPZcPsSLZsfTvU30sJdnlvbeaRtB5dV89CtiiMPfMQa0SpMtj00rQBO2C0w";

        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number",creditCard);
        cardParams.put("exp_month",exp2[0]);
        cardParams.put("exp_year",exp2[1]);
        cardParams.put("ccv",ccv);

        Map<String, Object> tokenParam = new HashMap<>();
        tokenParam.put("card",cardParams);
        Token token = Token.create(tokenParam);

        full += creditCard +"|"+exp+"|"+ccv;

        try (PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, s.getId());
            preparedStatement.setString(2, c.getId());
            preparedStatement.setString(3, full);
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {

                } else {
                    throw new SQLException("Failed");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getId(User s) {
        String selectQuery = "SELECT * FROM stripeusers WHERE id = ?";
        try (PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, s.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String identifier = resultSet.getString("identifier");
                    return identifier;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String getCC(User s) {
        String selectQuery = "SELECT * FROM stripeusers WHERE id = ?";
        try (PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, s.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String cc = resultSet.getString("cc");
                    return cc;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    private boolean userExists(String username) {

            String selectQuery = "SELECT * FROM user WHERE username = ?";
            try (PreparedStatement preparedStatement = MyConnection.getInstance().getConnection().prepareStatement(selectQuery)) {
                preparedStatement.setString(1, username);
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
