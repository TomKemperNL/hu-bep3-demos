package nl.tomkemper.bep3.hellospringweb;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class KanyeAdviceGenerator implements SLBAdviceGenerator {

    private RestTemplate template;

    public KanyeAdviceGenerator() {
        this.template = new RestTemplate();
    }

    @Override
    public String giveAdvice(SLBer slBer, Student student) {

        Quote result = this.template.getForObject(URI.create("https://api.kanye.rest"), Quote.class);
        return result.quote;
    }

    private static class Quote {
        public String quote;
    }

    public static void main(String[] args) {
        KanyeAdviceGenerator generator = new KanyeAdviceGenerator();
        String advice = generator.giveAdvice(new SLBer("Test"), new Student("Junky XL"));
        System.out.println(advice);
    }
}
