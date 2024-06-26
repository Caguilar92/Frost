package application_test.packageScanner_test_classes.annotatedClasses.classHasOnlyConfigurationComponentAnnotation;

import frost.container.Application;

public class AnnotatedWithConfiguredComponent {
    public static void main(String[] args) {
        Application.start(AnnotatedWithConfiguredComponent.class);
    }
}
