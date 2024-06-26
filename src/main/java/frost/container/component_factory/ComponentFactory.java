package frost.container.component_factory;
import frost.container.component.Component;

import java.lang.reflect.Constructor;

public interface ComponentFactory {


    Component createComponent(String name, Class<?> classz, Constructor<?> constructor);

    Component createComponent(String name, Class<?> classz, Object object);
}
