package frost.container.component_provider;

import frost.container.component.Component;
import frost.container.component_factory.ComponentFactory;
import frost.container.component_factory.ManagedConfigComponentFactory;

import java.util.List;

public abstract class ComponentProvider {
    protected ComponentFactory componentFactory = new ManagedConfigComponentFactory();

    public abstract void initializeComponents(List<Component> components, List<Class<?>> classList);


}
