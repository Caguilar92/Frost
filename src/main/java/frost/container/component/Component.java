package frost.container.component;

import java.util.Objects;

public abstract class Component {

    protected String name;
    protected Class<?> classz;
    protected Object object;

    public Component(String name, Class<?> classz) {
        this.name = name;
        this.classz = classz;
    }



    public Component(String name, Class<?> classz, Object object) {
        this.name = name;
        this.classz = classz;
        this.object = object;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getClassz() {
        return classz;
    }

    public void setClassz(Class<?> classz) {
        this.classz = classz;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Component component)) return false;
        return Objects.equals(name, component.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Component{" +
                "name='" + name + '\'' +
                '}';
    }
}
