package frost.container.component;

public class ConfigurationComponent extends Component {

    @Override
    public String toString() {
        return "ConfigurationComponent{" +
                "name='" + name + '\'' +
                '}';
    }

    public ConfigurationComponent(String name, Class<?> classz, Object object) {
        super(name, classz,object);

    }
}
