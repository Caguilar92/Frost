package frost.container.component_factory;

import frost.container.component.Component;
import frost.container.component.ConfigurationComponent;
import frost.container.component.ManagedComponent;

import java.lang.reflect.Constructor;

public class ManagedConfigComponentFactory implements ComponentFactory {


    @Override
    public Component createComponent(String name, Class<?> classz, Constructor<?> constructor) {
        return new ManagedComponent(name,classz,constructor);
    }

    @Override
    public Component createComponent(String name, Class<?> classz, Object object) {
        return new ConfigurationComponent(name,classz,object);
    }

}
