package springpractice;
import org.springframework.context.annotation.*;

/**
 * Created by Alex Euzent on 8/26/2015.
 */
@Configuration
public class HelloWorldConfig {
    @Bean
    public HelloWorld helloWorld(){
        return new HelloWorld();
    }
}
