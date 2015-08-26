package springpractice;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        AbstractApplicationContext context =
                new ClassPathXmlApplicationContext("Beans.xml");

        HelloWorld hw = (HelloWorld) context.getBean("helloWorld");
        hw.getMessage1();
        hw.getMessage2();

        HelloIndia hi = (HelloIndia) context.getBean("helloIndia");
        hi.getMessage1();
        hi.getMessage2();
        hi.getMessage3();

        /*
        HelloWorld objA = (HelloWorld) context.getBean("helloWorld");

        objA.setMessage("This is object A");
        objA.getMessage();

        HelloWorld objB = (HelloWorld) context.getBean("helloWorld");
        objB.getMessage();
        */
    }
}