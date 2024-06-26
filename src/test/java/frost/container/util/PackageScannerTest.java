package frost.container.util;

import application_test.packageScanner_test_classes.annotatedClasses.classHasOnlyConfigurationComponentAnnotation.AnnotatedWithConfiguredComponent;
import application_test.packageScanner_test_classes.annotatedClasses.classHasOnlyConfigurationComponentAnnotation.packageA.ConfigClassA;
import application_test.packageScanner_test_classes.annotatedClasses.classHasOnlyConfigurationComponentAnnotation.packageA.packagB.ConfigClassB;
import application_test.packageScanner_test_classes.annotatedClasses.classHasOnlyManagedComponentAnnotation.AnnotatedWithManagedComponent;
import application_test.packageScanner_test_classes.annotatedClasses.classHasOnlyManagedComponentAnnotation.packageA.ClassA;
import application_test.packageScanner_test_classes.annotatedClasses.classHasOnlyManagedComponentAnnotation.packageA.packageB.ClassB;
import application_test.packageScanner_test_classes.annotatedClasses.classHasOnlyManagedComponentAnnotation.packageA.packageB.PackageC.ClassC;
import application_test.packageScanner_test_classes.emptyPackages.emptyPackageA.EmptyPackage;
import application_test.packageScanner_test_classes.no_annotatedClasses.NoAnnotatedClasses;
import frost.container.Annotations.ConfiguredComponents;
import frost.container.Annotations.ManagedComponent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class PackageScannerTest {
    PackageScanner packageScanner = new PackageScanner();
    private static final Class<ConfiguredComponents> CONFIGURATION_ANNOTATION = ConfiguredComponents.class;
    private static final Class<frost.container.Annotations.ManagedComponent> MANAGED_COMPONENT_ANNOTATION = ManagedComponent.class;
    public PackageScannerTest() {

    }

    @Test
    public void scansEmptyPackages() {
       List<Class<?>> configurationList = packageScanner.scan(EmptyPackage.class,CONFIGURATION_ANNOTATION);
       List<Class<?>> managedComponentList = packageScanner.scan(EmptyPackage.class, MANAGED_COMPONENT_ANNOTATION);
       assertTrue(configurationList.isEmpty());
       assertTrue(managedComponentList.isEmpty());

    }

    @Test
    public void scansPackages_classesHaveNoAnnotationsPresent() {
        List<Class<?>> configurationList = packageScanner.scan(NoAnnotatedClasses.class,CONFIGURATION_ANNOTATION);
        List<Class<?>> managedComponentList = packageScanner.scan(NoAnnotatedClasses.class, MANAGED_COMPONENT_ANNOTATION);
        assertTrue(configurationList.isEmpty());
        assertTrue(managedComponentList.isEmpty());
    }

    @Test
    public void scansPackagesOnlyAnnotatedWithManagedComponent() {
        List<Class<?>> managedComponentList = packageScanner.scan(AnnotatedWithManagedComponent.class,MANAGED_COMPONENT_ANNOTATION);
        assertTrue(managedComponentList.contains(ClassA.class));
        assertTrue(managedComponentList.contains(ClassB.class));
        assertTrue(managedComponentList.contains(ClassC.class));

    }

    @Test
    public void scanPackagesOnlyUsingConfiguredComponents() {
        List<Class<?>> configuredComponentClasses = packageScanner.scan(AnnotatedWithConfiguredComponent.class,CONFIGURATION_ANNOTATION);
        assertTrue(configuredComponentClasses.contains(ConfigClassA.class));
        assertTrue(configuredComponentClasses.contains(ConfigClassB.class));
    }




}