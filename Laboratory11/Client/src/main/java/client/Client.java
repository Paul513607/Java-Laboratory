package client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/** A simple client class that makes requests to the Server. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private String name;
    private String JWT_TOKEN;

    public void register(String username) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", username);

        String URL = "http://localhost:8080/api/v1/users/register";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
        System.out.println(response.getStatusCode());
    }

    public void login(String username) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("username", username);

        String URL = "http://localhost:8080/login";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());

        HttpEntity request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
        System.out.println(response.getStatusCode());
        this.name = username;

        // get the JWT_TOKEN
        int startingIndex = response.getHeaders().toString().indexOf("access_token");
        String substringToken = response.getHeaders().toString().substring(startingIndex + "access_token".length() + 2);
        int nextIndex = substringToken.indexOf("\"");
        substringToken = substringToken.substring(0, nextIndex);
        JWT_TOKEN = substringToken;
    }

    public void getUsers() {
        String URL = "http://localhost:8080/api/v1/users";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", JWT_TOKEN);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
    }

    public void updateUser(Long id, String username) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", username);

        String URL = "http://localhost:8080/api/v1/users";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", JWT_TOKEN);

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);

        ResponseEntity<Void> response = restTemplate.exchange(URL, HttpMethod.PUT, request, Void.class);
        System.out.println(response.getStatusCode());
    }

    public void deleteUser(Long id, String username) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", username);

        String URL = "http://localhost:8080/api/v1/users";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", JWT_TOKEN);

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.DELETE, request, String.class);
        System.out.println(response.getStatusCode());
    }

    public void createFriendship(String username1, String username2) {
        String URL = "http://localhost:8080/api/v1/friends?username1=" + username1 + "&username2=" + username2;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", JWT_TOKEN);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, request, String.class);
        System.out.println(response.getStatusCode());
    }

    public void deleteFriendship(String username1, String username2) {
        String URL = "http://localhost:8080/api/v1/friends?username1=" + username1 + "&username2=" + username2;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", JWT_TOKEN);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.DELETE, request, String.class);
        System.out.println(response.getStatusCode());
    }

    public void getFriendsOf(String username) {
        String URL = "http://localhost:8080/api/v1/friends?username=" + username;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", JWT_TOKEN);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
    }

    public void getMostPopularUsers(Integer howMany) {
        String URL = "http://localhost:8080/api/v1/friends/popular?howMany=" + howMany;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", JWT_TOKEN);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
    }

    public void getApiDocs() {
        String URL = "http://localhost:8080/v2/api-docs";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
    }

    public void getFriendsCutpoints() {
        String URL = "http://localhost:8080/api/v1/users/alg";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", JWT_TOKEN);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
    }

    public void getHello() {
        String URL = "http://localhost:8080/";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
    }
}
