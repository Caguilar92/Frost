package frost.container;

import frost.container.Annotations.ConfiguredComponents;
import frost.container.Annotations.ManagedComponent;
import frost.container.component.Component;
import frost.container.component_provider.ComponentProviderManager;
import frost.container.util.PackageScanner;
import frost.container.util.UniqueComponentNameValidator;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private static final ComponentProviderManager componentManager = new ComponentProviderManager();
    private static PackageScanner packageScanner = new PackageScanner();
    private static Class<?> main;
    private static List<Component> components = new ArrayList<>();
    private static final Class<ConfiguredComponents> CONFIGURATION_ANNOTATION = ConfiguredComponents.class;
    private static final Class<ManagedComponent> COMPONENT_ANNOTATION = ManagedComponent.class;
    private static final Context context = new Context();

    public static void start(Class<?> mainClass) {
        main = mainClass;
        processApplication();

    }

    private static void processApplication() {
        processConfigurationFiles();
        processApplicationComponentFiles();
        UniqueComponentNameValidator.validateComponents(components);
        components = new TopologicalSorter(components).sort();
        context.register(components);

    }

    private static void processConfigurationFiles() {
        List<Class<?>> configurationClasses = packageScanner.scan(main, CONFIGURATION_ANNOTATION);
        componentManager.createComponents(components,configurationClasses,CONFIGURATION_ANNOTATION);



    }

    private static void processApplicationComponentFiles() {
        List<Class<?>> managedComponentClasses = packageScanner.scan(main, COMPONENT_ANNOTATION);
        componentManager.createComponents(components,managedComponentClasses,COMPONENT_ANNOTATION);

    }

    public static Context getContext() {
        return context;
    }





}
