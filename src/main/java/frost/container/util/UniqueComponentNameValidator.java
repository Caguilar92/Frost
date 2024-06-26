package frost.container.util;

import frost.container.component.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueComponentNameValidator {

    public static void validateComponents(List<Component> components) throws RuntimeException {
        Set<Component> uniqueSet = new HashSet<>();
        for(Component component : components) {
            if(uniqueSet.contains(component)) {
                throw new RuntimeException("Duplicate component name: '" + component.getName()+"'." + " Consider using @Name([unique_name]) annotation to resolve issue.");
            }
            uniqueSet.add(component);
        }
    }
}
