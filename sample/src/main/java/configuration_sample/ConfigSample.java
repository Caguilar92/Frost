package configuration_sample;

import frost.container.Application;
import frost.container.Context;

public class ConfigSample {
    public static void main(String[] args) {
        Application.start(ConfigSample.class);
        Context context = Application.getContext();
        PrinterContainer printerContainer = context.getComponent(PrinterContainer.class);
        printerContainer.printMesssage();
    }
}
