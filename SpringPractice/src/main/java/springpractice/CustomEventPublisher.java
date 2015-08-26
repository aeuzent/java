package springpractice;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
/**
 * Created by Alex Euzent on 8/26/2015.
 */
public class CustomEventPublisher  implements ApplicationEventPublisherAware{
    private ApplicationEventPublisher pub;

    public void setApplicationEventPublisher(ApplicationEventPublisher pub){
        this.pub = pub;
    }

    public void publish(){
        CustomEvent ce = new CustomEvent(this);
        pub.publishEvent(ce);
    }
}
