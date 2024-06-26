package frost.container.util;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class PackageScanner {
    private List<Class<?>> classList;
    private ClassLoader classLoader;
    private Class<? extends Annotation> annotation;
    private final String CLASS_EXTENSION = ".class";

    public PackageScanner() {

    }

    public List<Class<?>> scan(Class<?> main, Class<? extends Annotation> annotation) {
        this.annotation = annotation;
        this.classList = new ArrayList<>();
        this.classLoader = Thread.currentThread().getContextClassLoader();

        String path = getPath(main);
        scanPackages(path);
        return classList;
    }

    private void scanPackages(String path) {
        InputStream inputStream = classLoader.getResourceAsStream(path.replaceAll("[.]", "/"));

        if (inputStream == null) {
            return;
        }

        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String resource = scanner.nextLine();
            processResource(resource,path);
        }
    }

    private void processResource(String resource, String path) {
        if (resourceIsAClass(resource)) {
            loadClassWithDeclaredAnnotation(resource, path);
        } else {
            String newPath = path + "." + resource;
            scanPackages(newPath);
        }
    }

    private boolean resourceIsAClass(String resource) {
        return resource.endsWith(CLASS_EXTENSION);
    }

    private void loadClassWithDeclaredAnnotation(String resource, String path) {
        Class<?> classz = getClassObject(path, resource);
        if (classz.isAnnotationPresent(annotation)) {
            classList.add(classz);
        }
    }

    private Class<?> getClassObject(String directory, String className) {
        String fullyQualifiedName = formatQualifiedName(directory,className);
        return resolveQualifiedNameToClassz(fullyQualifiedName);
    }

    private String getPath(Class<?> classz) {
        return classz.getPackageName().replaceAll("[.]", "/");
    }

    private boolean isTopLevelDirectory(String directory) {
        return directory.isEmpty();
    }

    private Class<?> resolveQualifiedNameToClassz(String fullyQualifiedName) {
        Class<?> classz = null;
        try {
            classz = Class.forName(fullyQualifiedName);

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Could not resolve " + fullyQualifiedName + " to Class type");
        }
        return classz;
    }

    private String formatQualifiedName(String directory, String className) {
        String fullyQualifiedName;
        if (isTopLevelDirectory(directory)) {
            fullyQualifiedName = className.substring(0, className.lastIndexOf("."));
        } else {
            fullyQualifiedName = directory + "." + className.substring(0, className.lastIndexOf("."));
        }
        fullyQualifiedName = fullyQualifiedName.replaceAll("/",".");
        return fullyQualifiedName;
    }

}
