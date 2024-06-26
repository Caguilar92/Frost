package frost.container.constructor_strategys;

import frost.container.component.Component;
import frost.container.component.ManagedComponent;

import java.lang.reflect.Constructor;
import java.util.List;

public class NoInjectAnnotatedStrategy implements AnnotatedConstructorStrategy {

    private ManagedComponent component;
    private List<Class<?>> currentMangedComponentClasses;
    private List<Component> currentComponents;

    public NoInjectAnnotatedStrategy(ManagedComponent component,List<Class<?>> managedComponentClasses, List<Component> currentComponents) {
        this.component = component;
        this.currentMangedComponentClasses = managedComponentClasses;
        this.currentComponents= currentComponents;

    }

    @Override
    public void resolveConstructor() {
        Constructor<?> constructor;
        try {
            constructor = component.getClassz().getDeclaredConstructor(component.getDependencies())   ;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        Class<?>[] dependencies = component.getDependencies();
        for(int i = 0; i < dependencies.length; i++){
            if(dependencies[i].isInterface()) {
               dependencies[i] = replaceInterfaceWithImplementationClassz(dependencies[i]);
            }

            checkIfImplementationExists(dependencies[i]);
        }




    }

    private Class<?> replaceInterfaceWithImplementationClassz(Class<?> _interface) {
        Class<?> implementation = checkIfImplementationExistsAsManagedComponent(_interface);
        if(implementation != null) {
            return implementation;
        }

        implementation = checkIfImplementationExistsAsCurrentComponent(_interface);
        if(implementation != null) {
            return implementation;
        }

        throw new RuntimeException("No implementation found for " + _interface);


    }

    private Class<?> checkIfImplementationExistsAsManagedComponent(Class<?> _interface) {
        for(Class<?> implementation : currentMangedComponentClasses) {
            if(_interface.isAssignableFrom(implementation)) {
                return implementation;
            }
        }
        return null;
    }

    private Class<?> checkIfImplementationExistsAsCurrentComponent(Class<?> _interface) {
        for(Component implementation : currentComponents) {
            if(_interface.isAssignableFrom(implementation.getClassz())) {
                return implementation.getClassz();
            }
        }
        return null;
    }

    private void checkIfImplementationExists(Class<?> classz) {
        for(Class<?> concreteClass : currentMangedComponentClasses) {
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
