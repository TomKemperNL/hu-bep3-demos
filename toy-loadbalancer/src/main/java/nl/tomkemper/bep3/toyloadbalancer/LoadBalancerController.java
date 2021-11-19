package nl.tomkemper.bep3.toyloadbalancer;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@RestController
public class LoadBalancerController {

    private HttpClient client = HttpClient.newHttpClient();

    @RequestMapping(path = "**")
    public ResponseEntity forward(HttpServletRequest req) throws IOException, InterruptedException {

        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(req.getInputStream());
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
        }

        HttpRequest request = HttpRequest.newBuilder()
                .method(req.getMethod(), HttpRequest.BodyPublishers.ofString(sb.toString()))
                .uri(URI.create("http://openjdk.java.net/"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return ResponseEntity.ok(response.body());
    }

    @GetMapping("test")
    public String testPriority() {
        return "Yep";
    }
}
