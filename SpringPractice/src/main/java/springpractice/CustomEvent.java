package springpractice;

import org.springframework.context.ApplicationEvent;
/**
 * Created by Alex Euzent on 8/26/2015.
 */
public class CustomEvent  extends ApplicationEvent{
    public CustomEvent(Object source){
        super(source);
    }

    public String toString(){
        return "It's a custom event!";
    }
}

