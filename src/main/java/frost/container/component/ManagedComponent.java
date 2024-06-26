package frost.container.component;

import java.lang.reflect.Constructor;

public class ManagedComponent extends Component {

    private Class<?>[] dependencies;
    private Constructor<?> constructor;

    public ManagedComponent(String name, Class<?> classz, Constructor<?> constructor) {
        super(name,classz);
        this.dependencies = constructor.getParameterTypes();
        this.constructor = constructor;
    }

    public Class<?>[] getDependencies() {
        return dependencies;
    }
    public Constructor<?> getConstructor() {return constructor;}

    public void setDependencies(Class<?>[] dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public String toString() {
        return "ManagedComponent{" +

                " name='" + name + '\'';
    }
}
