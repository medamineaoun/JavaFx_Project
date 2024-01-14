package esprit.tn.tunicamp.services;

import esprit.tn.tunicamp.entities.Post;

import java.sql.SQLException;
import java.util.List;

public interface PostService {
    List<Post> getAllPosts() throws SQLException;
}