package frost.container.component_provider;

import frost.container.Annotations.Inject;
import frost.container.component.Component;
import frost.container.component.ManagedComponent;
import frost.container.constructor_strategys.InjectAnnotatedStrategy;
import frost.container.constructor_strategys.NoInjectAnnotatedStrategy;

import java.lang.reflect.Constructor;
import java.util.List;

public class ManagedComponentProvider extends ComponentProvider {

    @Override
    public void initializeComponents(List<Component> components, List<Class<?>> classList) {
        for(Class<?> classz : classList) {
            String name = classz.getName();
            Constructor<?> constructor = getLargestConstructor(classz);
            ManagedComponent component = new ManagedComponent(name,classz,constructor);
            Inject injectAnnotation = constructor.getAnnotation(Inject.class);
            if(injectAnnotation == null) {
                new NoInjectAnnotatedStrategy(component, classList, components).resolveConstructor();
            }else {
                new InjectAnnotatedStrategy(component, classList, components).resolveConstructor();
            }
            components.add(component);
        }
    }

    private Constructor<?> getLargestConstructor(Class<?> classz) {
        Constructor<?>[] constructors = classz.getDeclaredConstructors();
        Constructor<?> result = constructors[0];
        for(Constructor<?> constructor : constructors) {
            int resultLength = result.getParameterTypes().length;
            int currentConstructorLength = constructor.getParameterTypes().length;

            if(currentConstructorLength > resultLength) {
                result = constructor;
            }
        }
        return result;
    }
}
