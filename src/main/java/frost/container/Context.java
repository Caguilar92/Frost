package frost.container;

import frost.container.component.Component;
import frost.container.component.ConfigurationComponent;
import frost.container.component.ManagedComponent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    private Map<Class<?>, List<Component>> context;
    public Context() {
        this.context = new HashMap<>();
    }

    protected void register(List<Component> components) {
        for(Component component : components) {
            context.computeIfAbsent(component.getClassz(), k -> new ArrayList<Component>());
            if(component instanceof ConfigurationComponent) {
                context.get(component.getClassz()).add(component);
            }else {
                instantiateManagedComponent((ManagedComponent) component);
                context.get(component.getClassz()).add(component);
            }

        }


    }

    private void instantiateManagedComponent(ManagedComponent component) {
        Object[] instantiatedDependencies = new Object[component.getDependencies().length];
        Class<?>[] classzDependencies = component.getDependencies();
        for(int i = 0; i < classzDependencies.length; i++) {
            instantiatedDependencies[i] = context.get(classzDependencies[i]).get(0).getObject();
        }

        try {
            Constructor<?> constructor = component.getConstructor();
            if(constructor.getParameterCount() == 0) {
                Object object = constructor.newInstance(null);
                component.setObject(object);
            }else {

                Object object = constructor.newInstance(instantiatedDependencies);
                component.setObject(object);
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }

    @SuppressWarnings("unchecked")
    public <T> T getComponent(Class<T> componentClass) {
        List<Component> components = context.get(componentClass);
        if (components == null || components.isEmpty()) {
            throw new RuntimeException("Component not found: " + componentClass.getName());
        }
        return (T) components.get(0).getObject();
    }




}
