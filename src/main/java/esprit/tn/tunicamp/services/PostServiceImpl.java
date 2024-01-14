package esprit.tn.tunicamp.services;

import esprit.tn.tunicamp.entities.Camper;
import esprit.tn.tunicamp.entities.Post;
import esprit.tn.tunicamp.entities.UserType;
import esprit.tn.tunicamp.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;


public class PostServiceImpl implements PostService {

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        String requete = "SELECT id, camperId, content, createdAt FROM post";
        try{
            PreparedStatement st=MyConnection.getInstance().getConnection().prepareStatement(requete);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                long postId = rs.getLong("id");
                long camperId = rs.getLong("camperId");
                String content = rs.getString("content");
                Timestamp createdAt = rs.getTimestamp("createdAt");

                Camper camper = getCamperById(camperId);

                Post post = new Post(postId, camper, content, createdAt);
                posts.add(post);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return posts;
}

    private Camper getCamperById(long camperId) {
        Camper camper = null;

        try (Connection connection = MyConnection.getInstance().getConnection()) {
            String selectQuery = "SELECT id, username, password, userType FROM user WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setLong(1, camperId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String username = resultSet.getString("username");
                        String password = resultSet.getString("password");
                        String url = resultSet.getString("url");
                        UserType userType = UserType.valueOf(resultSet.getString("userType"));

                        camper = new Camper(id, username, password, url, userType);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return camper;
    }

}
