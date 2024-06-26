package frost.container.component_provider;

import frost.container.Annotations.ConfiguredComponents;
import frost.container.Annotations.ManagedComponent;
import frost.container.component.Component;

import java.util.List;

public class ComponentProviderManager {

    public ComponentProviderManager() {

    }

    public void createComponents(List<Component> components, List<Class<?>> classList, Class<?> annotation) {
        if(annotation == ConfiguredComponents.class) {
            new ConfigComponentProvider().initializeComponents(components, classList);

        }else if(annotation == ManagedComponent.class) {
            new ManagedComponentProvider().initializeComponents(components, classList);
        }
    }


}
