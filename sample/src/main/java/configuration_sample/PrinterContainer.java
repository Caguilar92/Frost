package configuration_sample;

public class PrinterContainer {

    private Printer printer;


    public PrinterContainer(Printer printer) {
       this.printer = printer;
    }

    public void printMesssage() {
        printer.print();
    }
}
