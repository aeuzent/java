package springpractice;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
/**
 * Created by Alex Euzent on 8/26/2015.
 */
public class CStartEventHandler implements ApplicationListener<ContextStartedEvent>{
    public void onApplicationEvent(ContextStartedEvent event){
        System.out.println("ContextStartedEvent Received");
    }


}
