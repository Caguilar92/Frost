package managedComponent_sample.no_injectAnnotation;


import frost.container.Application;
import frost.container.Context;

public class ManagedComponentSample {
    public static void main(String[] args) {
        Application.start(ManagedComponentSample.class);
        Context context = Application.getContext();
        PrinterContainer printerContainer = context.getComponent(PrinterContainer.class);
        printerContainer.printMesssage();
    }
}
