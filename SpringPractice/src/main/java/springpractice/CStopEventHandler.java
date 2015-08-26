package springpractice;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * Created by Alex Euzent on 8/26/2015.
 */
public class CStopEventHandler implements ApplicationListener<ContextStoppedEvent>{
    public void onApplicationEvent(ContextStoppedEvent event){
        System.out.println("ContextStoppedEvent Received");
    }
}
