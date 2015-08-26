package springpractice;
import org.springframework.context.ApplicationListener;
/**
 * Created by Alex Euzent on 8/26/2015.
 */
public class CustomEventHandler implements ApplicationListener<CustomEvent>{
    public void onApplicationEvent(CustomEvent event){
        System.out.println(event.toString());
    }
}
