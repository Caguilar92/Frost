package configuration_sample;

public class HelloWorldPrinter implements Printer {

   public HelloWorldPrinter() {

   }

    @Override
    public void print() {
        System.out.println("Hello World");
    }
}
