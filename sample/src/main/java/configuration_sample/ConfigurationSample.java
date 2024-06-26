package configuration_sample;

import frost.container.Annotations.ConfiguredComponents;

@ConfiguredComponents
public class ConfigurationSample {
    public ConfigurationSample() {

    }

    public PrinterContainer printerContainer() {
        HelloWorldPrinter helloWorldPrinter = new HelloWorldPrinter();
        PrinterContainer printerContainer = new PrinterContainer(helloWorldPrinter);
        return printerContainer;
    }





}
