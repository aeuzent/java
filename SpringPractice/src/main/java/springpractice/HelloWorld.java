package springpractice;

public class HelloWorld {
    private String message1, message2;

    public void setMessage(String message){
        this.message1  = message;
    }
    public void setMessage2(String message){
        this.message2  = message;
    }
    public void getMessage(){
        System.out.println("World Message1 : " + message1);
    }
    public void getMessage2(){
        System.out.println("World Message2 : " + message2);
    }

    /*
    public void init(){
        System.out.println("Bean going through init");
    }

    public void destroy(){
        System.out.println("Bean will now be destroyed");
    }*/
}