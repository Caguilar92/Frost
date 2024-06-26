package managedComponent_sample.no_injectAnnotation;

import frost.container.Annotations.ManagedComponent;

@ManagedComponent
public class HelloWorldPrinter implements Printer {

   public HelloWorldPrinter() {

   }

    @Override
    public void print() {
        System.out.println("Hello World");
    }
}
