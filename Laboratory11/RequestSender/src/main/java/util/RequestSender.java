package util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSender implements Runnable {
    private Integer threadNo = 0;

    @Override
    public void run() {
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
