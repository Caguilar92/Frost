package configuration_sample;

import frost.container.Annotations.ConfiguredComponents;

@ConfiguredComponents
public class ConfigurationSample {
    public ConfigurationSample() {

    }

     public Printer goodByeWorld() {
        return new GoodByeWorldPrinter();
     }

}
