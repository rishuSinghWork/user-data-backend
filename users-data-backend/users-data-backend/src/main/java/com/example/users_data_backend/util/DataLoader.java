package com.example.users_data_backend.util;



import com.example.users_data_backend.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    private static final String USERS_API_URL = "https://dummyjson.com/users";

    public List<User> fetchUsers() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(USERS_API_URL))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch users data");
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());
        JsonNode usersNode = root.get("users");
        List<User> users = new ArrayList<>();
        if (usersNode != null && usersNode.isArray()) {
            for (JsonNode node : usersNode) {
                User user = new User();
                user.setId(node.get("id").asLong());
                user.setFirstName(node.get("firstName").asText());
                user.setLastName(node.get("lastName").asText());
                user.setSsn(node.has("ssn") ? node.get("ssn").asText() : "N/A");
                user.setEmail(node.get("email").asText());
                user.setImage(node.get("image").asText());
                users.add(user);
            }
        }
        return users;
    }
}
