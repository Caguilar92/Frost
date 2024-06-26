package application_test.packageScanner_test_classes.annotatedClasses.classHasOnlyManagedComponentAnnotation;

import frost.container.Application;

public class AnnotatedWithManagedComponent {
    public static void main(String[] args) {
        Application.start(AnnotatedWithManagedComponent.class);
    }
}
