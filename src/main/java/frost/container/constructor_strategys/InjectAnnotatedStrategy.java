package frost.container.constructor_strategys;

import frost.container.Annotations.Inject;
import frost.container.component.Component;
import frost.container.component.ManagedComponent;

import java.lang.reflect.Constructor;
import java.util.List;

public class InjectAnnotatedStrategy implements AnnotatedConstructorStrategy {

    private ManagedComponent component;
    private List<Component> currentComponents;
    private List<Class<?>> managedComponents;
    public InjectAnnotatedStrategy(ManagedComponent component,List<Class<?>> managedComponents, List<Component> currentComponents)  {
        this.component = component;
        this.managedComponents = managedComponents;
        this.currentComponents = currentComponents;
    }
    @Override
    public void resolveConstructor() {
        Constructor<?> constructor = null;
        try {
            constructor = component.getClassz().getDeclaredConstructor(component.getDependencies());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        Class<?>[] implementations = constructor.getDeclaredAnnotation(Inject.class).implementations();
        Class<?>[] constructorDependencies = component.getDependencies();
        if(implementations.length == 0) {
            throw new RuntimeException("@Inject has no implementations");
        }

        int currenIndex = 0;
        for(int i = currenIndex; i < implementations.length; i++) {
            if(implementations[i].isInterface()) {
                throw new RuntimeException("@Inject has an interface");
            }
           constructorDependencies[i] = getImplementation(implementations[i]);
            currenIndex = i;
        }

        if(currenIndex < constructorDependencies.length) {
            for(int i = 0; i < constructorDependencies.length; i++) {
                if(constructorDependencies[i].isInterface()) {
                    constructorDependencies[i] = swapInterfaceWithImplementation(constructorDependencies[i]);
                }
                checkIfImplementationExists(constructorDependencies[i]);

            }
        }



    }

    private Class<?> getImplementation(Class<?> implementation) {
        for(Class<?> classz : managedComponents) {
            if(classz.isAssignableFrom(implementation)) {
                return implementation;
            }
        }

        for(Component component : currentComponents) {
            if(component.getClassz() == implementation) {
                return component.getClassz();
            }
        }

        throw new RuntimeException("@Inject implementation " + implementation.getName() + " does not exist in this context");
    }


    private Class<?> swapInterfaceWithImplementation(Class<?> _interface) {
        for(Class<?> classz : managedComponents) {
            if(_interface.isAssignableFrom(classz)) {
                return classz;
            }
        }

        for(Component component : currentComponents) {
            if(_interface.isAssignableFrom(component.getClassz())) {
                return component.getClassz();
            }
        }

        throw new RuntimeException("no implementation found for " + _interface.getName());
    }

    private void checkIfImplementationExists(Class<?> classz) {
        for(Class<?> concreteClass : managedComponents) {
            if(concreteClass == classz) {
                return;
            }
        }

        for(Component component : currentComponents) {
            if(component.getClassz() == classz) {
                return;
            }
        }

        throw new RuntimeException(classz.getName() + " not found in context");
    }

}
