package nl.tomkemper.bep3.hellospringweb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@Primary
public class RandomAdviceGenerator implements SLBAdviceGenerator {

    private RestTemplate template;

    public RandomAdviceGenerator() {
        this.template = new RestTemplate();
    }

    @Override
    public String giveAdvice(SLBer slBer, Student student) {
        //Deze random advice API returnt JSON met een Content-Type van text/html... dus dat gaat niet zomaar werken
        String rawResult = this.template.getForObject(URI.create("https://api.adviceslip.com/advice"), String.class);
        ObjectMapper mapper = new ObjectMapper();
        Advice result = null;
        try {
            result = mapper.readValue(rawResult, Advice.class);
            return result.slip.advice;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Slip {
        public String advice;
        public int id;
    }

    private static class Advice {
        public Slip slip;
    }

    public static void main(String[] args) {
        RandomAdviceGenerator generator = new RandomAdviceGenerator();
        String advice = generator.giveAdvice(new SLBer("Test"), new Student("Junky XL"));
        System.out.println(advice);
    }
}
