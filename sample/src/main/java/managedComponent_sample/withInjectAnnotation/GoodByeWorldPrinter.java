package managedComponent_sample.withInjectAnnotation;

import frost.container.Annotations.ManagedComponent;

@ManagedComponent
public class GoodByeWorldPrinter implements Printer {

    @Override
    public void print() {
        System.out.println("Goodbye World");
    }
}
