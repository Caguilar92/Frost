package frost.container.component_provider;

import frost.container.Annotations.Name;
import frost.container.component.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ConfigComponentProvider extends ComponentProvider {


    @Override
    public void initializeComponents(List<Component> components, List<Class<?>> classList) {
        for(Class<?> classz : classList) {
            Object configObject = instantiateConfigClass(classz);
            createComponents(configObject,components);

        }


    }

    private Object instantiateConfigClass(Class<?> configClass) {
        try {
            return configClass.getConstructor().newInstance(null);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void createComponents(Object configObject, List<Component> components) {
        Method[] methods = configObject.getClass().getDeclaredMethods();
        Object object = null;
        for(Method method : methods) {
            try {
               object = method.invoke(configObject);

            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            Component component = componentFactory.createComponent(getName(method,object),object.getClass(),object);
            components.add(component);
        }

    }

    private String getName(Method method, Object object) {
        Name annotation = method.getAnnotation(Name.class);

        if(annotation == null) {
            return object.getClass().getName();
        }else {
            return annotation.name();
        }
    }
}
