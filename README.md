## about Frost
### Frost is a dependency injection framework designed to simplify the implementation of the Inversion of Control (IoC) principle. By utilizing annotations, Frost enables the instantiation of user-defined classes as singleton objects, which are then managed by the applications Context.
#### <hr> 

### Sample
In the entry point of you application use the Application.start() method and pass in the class as the argument
```java

public class ManagedComponentSample {
    public static void main(String[] args) {
        Application.start(ManagedComponentSample.class);
        Context context = Application.getContext();
        
    }
}
```
The framework will scan all sibling and child packages searching for classes annotated with @ConfiguredComponent or @ManagedComponent annotations. These annotations are used in order to mark the classes that should be instantiated by the framework.
# <br>
Define and annotate the classes you wish the framework to manage with the @ManagedComponent annotation.

```java
@ManagedComponent
public class HelloWorldPrinter implements Printer {

   public HelloWorldPrinter() {

   }

    @Override
    public void print() {
        System.out.println("Hello World");
    }
}
```
```java
@ManagedComponent
public class GoodByeWorldPrinter implements Printer {

    @Override
    public void print() {
        System.out.println("Goodbye World");
    }
}
````
````java
@ManagedComponent
public class PrinterContainer {

    private Printer printer;


    public PrinterContainer(Printer printer) {
       this.printer = printer;
    }

    public void printMesssage() {
        printer.print();
    }
}
````
Note: Because no specific implementation was declared, the framework will search and inject any implementation it can find of the interface defined in the constructor. If it cannot find one, an exception will be thrown.
# <br>
If you wish to use a specific implementation, simply add the @Inject annotation and pass in the implementations your wish the framework to inject. The framework will attempt to inject the correct implementation if it can find one else it will throw an exception.
````java
@ManagedComponent
public class PrinterContainer {

    private Printer helloWorldPrinter;


    @Inject(implementations = {HelloWorldPrinter.class})
    public PrinterContainer(Printer helloWorld) {
        this.helloWorldPrinter = helloWorld;

    }

    public void printMesssage() {
        helloWorldPrinter.print();

    }
}
````
# <br>
If there are multiple parameters you need resolve, simply declare the classes in the @Inject annotation.
````java
@ManagedComponent
public class PrinterContainer {

    private Printer helloWorldPrinter;
    private Printer goodbyeWorldPrinter;

   @Inject(implementations = {HelloWorldPrinter.class,GoodByeWorldPrinter.class})
    public PrinterContainer(Printer helloWorld, Printer goodbyeWorldPrinter) {
       this.helloWorldPrinter = helloWorld;
       this.goodbyeWorldPrinter = goodbyeWorldPrinter;

   }

    public void printMesssage() {
       helloWorldPrinter.print();
       goodbyeWorldPrinter.print();

    }
    
}
````
Note: The order the implementations are declared must be the in the same order as the parameters defined.
# <br>
If you need to inject an object from third-party code, use the @ConfiguredComponent annotation on the classes where you will configure your objects. Create and return them in a method, and the framework will pick them up and inject them into the appropriate constructors. Or you can choose to wire up all of your applications objects through the configuration files.
```java
@ConfiguredComponents
public class ConfigurationSample {
    public ConfigurationSample() {

    }

     public Printer goodByeWorld() {
        return new GoodByeWorldPrinter();
     }

}

```
```java
@ManagedComponent
public class PrinterContainer {

    private Printer printer;


    public PrinterContainer(Printer printer) {
       this.printer = printer;
    }

    public void printMesssage() {
        printer.print();
    }
    
}
```
# <br>
If you wish to get a reference to objects maintained by the framework, simply get a reference of the Context from the Application, and use the context's getComponent() method.
```java
public class ManagedComponentSample {
    public static void main(String[] args) {
        Application.start(ManagedComponentSample.class);
        Context context = Application.getContext();
        PrinterContainer printerContainer = context.getComponent(PrinterContainer.class);
        printerContainer.printMesssage();
    }





