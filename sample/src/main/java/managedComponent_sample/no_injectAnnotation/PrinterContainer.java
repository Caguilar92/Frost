package managedComponent_sample.no_injectAnnotation;

import frost.container.Annotations.ManagedComponent;

@ManagedComponent
public class PrinterContainer {

    private Printer printer;


    public PrinterContainer(Printer printer) {
       this.printer = printer;
    }

    public void printMesssage() {
        printer.print();
    }
}
