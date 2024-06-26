package managedComponent_sample.withInjectAnnotation;

import frost.container.Annotations.Inject;
import frost.container.Annotations.ManagedComponent;
@ManagedComponent
public class PrinterContainer {

    private Printer helloWorldPrinter;
    private Printer goodByeWorldPrinter;

   @Inject(implementations = {HelloWorldPrinter.class,GoodByeWorldPrinter.class})
    public PrinterContainer(Printer helloWorld, Printer goodbyeWorldPrinter ) {
       this.helloWorldPrinter = helloWorld;
       this.goodByeWorldPrinter = goodbyeWorldPrinter;
   }

    public void printMesssage() {
       helloWorldPrinter.print();
       goodByeWorldPrinter.print();
    }
}
