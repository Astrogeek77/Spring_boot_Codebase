# Design Patterns Handbook

This handbook covers 23 widely used object-oriented design patterns. Each section includes intent, structure, Java example code, UML-style diagram, benefits, drawbacks, usage guidance, real-world examples, and interview questions.

---

## 1. Builder Pattern

### Intent
Builder separates the construction of a complex object from its representation so the same construction process can create different representations.

### When to use
- When an object has many optional parameters.
- When constructors become telescoping and hard to read.
- When object creation should be step-by-step.

### UML Diagram
```text
+-------------------+        +-------------------+
|     Director      |------->|      Builder      |
+-------------------+        +-------------------+
| construct()       |        | buildPartA()      |
+-------------------+        | buildPartB()      |
                             | getResult()       |
                             +-------------------+
                                       ^
                                       |
                             +-------------------+
                             |  ConcreteBuilder  |
                             +-------------------+
                             | builds Product    |
                             +-------------------+
```

### Java Example
```java
class Computer {
    private String cpu;
    private String ram;
    private String storage;

    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
    }

    public static class Builder {
        private String cpu;
        private String ram;
        private String storage;

        public Builder cpu(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder ram(String ram) {
            this.ram = ram;
            return this;
        }

        public Builder storage(String storage) {
            this.storage = storage;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Computer computer = new Computer.Builder()
                .cpu("Intel i7")
                .ram("16GB")
                .storage("512GB SSD")
                .build();
    }
}
```

### Explanation
Builder moves object construction logic into a dedicated builder class. This improves readability and makes object creation safer when many fields are optional or need validation.

### Pros
- Improves readability.
- Avoids telescoping constructors.
- Supports immutable objects.
- Gives fine-grained control over construction.

### Cons
- Adds extra classes.
- Can be unnecessary for simple objects.

### Real-world use case
- `StringBuilder` in Java.
- Creating HTTP requests, SQL queries, or configuration-heavy objects.

### Interview Questions
1. How is Builder different from Factory?
2. Why is Builder useful for immutable classes?
3. What problem does Builder solve in constructor design?

---

## 2. Singleton Pattern

### Intent
Singleton ensures a class has only one instance and provides a global access point to it.

### When to use
- When exactly one shared instance is required.
- For logging, configuration, caching, or connection managers.

### UML Diagram
```text
+----------------------+
|      Singleton       |
+----------------------+
| - instance           |
+----------------------+
| + getInstance()      |
+----------------------+
```

### Java Example
```java
class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

### Explanation
The constructor is private so no outside class can create objects directly. Access is controlled through a static method that returns the same instance every time.

### Pros
- Controlled single instance.
- Saves memory for shared resources.
- Easy global access.

### Cons
- Hard to unit test.
- Can hide dependencies.
- Often violates single responsibility principle.
- Requires care in multithreading.

### Real-world use case
- Application configuration manager.
- Logger service.
- Print spooler.

### Interview Questions
1. How do you make Singleton thread-safe?
2. Why can Singleton be considered an anti-pattern in some systems?
3. How can reflection or serialization break Singleton?

---

## 3. Factory Pattern

### Intent
Factory creates objects without exposing the exact instantiation logic to the client.

### When to use
- When object creation logic is centralized.
- When client code should depend on abstractions instead of concrete classes.

### UML Diagram
```text
+-------------+       +----------------+
|   Client    |------>|    Factory     |
+-------------+       +----------------+
                               |
                 +-------------+-------------+
                 |                           |
         +---------------+           +---------------+
         | ConcreteProdA |           | ConcreteProdB |
         +---------------+           +---------------+
```

### Java Example
```java
interface Notification {
    void notifyUser();
}

class EmailNotification implements Notification {
    public void notifyUser() {
        System.out.println("Sending email");
    }
}

class SMSNotification implements Notification {
    public void notifyUser() {
        System.out.println("Sending SMS");
    }
}

class NotificationFactory {
    public static Notification createNotification(String type) {
        if (type.equalsIgnoreCase("EMAIL")) return new EmailNotification();
        if (type.equalsIgnoreCase("SMS")) return new SMSNotification();
        throw new IllegalArgumentException("Unknown type");
    }
}
```

### Explanation
The client asks the factory for an object and does not need to know the concrete class details. This reduces coupling and makes future changes easier.

### Pros
- Encapsulates creation logic.
- Reduces coupling.
- Easier extension.

### Cons
- Adds indirection.
- Simple cases may not need a factory.

### Real-world use case
- Parser creation based on file type.
- Payment processor creation based on payment method.

### Interview Questions
1. How is Factory different from Abstract Factory?
2. What problem does Factory solve in client code?
3. Can Factory violate open/closed principle if poorly implemented?

---

## 4. Abstract Factory Pattern

### Intent
Abstract Factory provides an interface for creating families of related objects without specifying their concrete classes.

### When to use
- When products belong to related families.
- When the system should switch between product families.

### UML Diagram
```text
+--------------------+
|  AbstractFactory   |
+--------------------+
| createButton()     |
| createCheckbox()   |
+--------------------+
          ^
          |
+--------------------+    +--------------------+
| WinFactory         |    | MacFactory         |
+--------------------+    +--------------------+
```

### Java Example
```java
interface Button { void paint(); }
interface Checkbox { void render(); }

class WindowsButton implements Button {
    public void paint() { System.out.println("Windows Button"); }
}

class MacButton implements Button {
    public void paint() { System.out.println("Mac Button"); }
}

class WindowsCheckbox implements Checkbox {
    public void render() { System.out.println("Windows Checkbox"); }
}

class MacCheckbox implements Checkbox {
    public void render() { System.out.println("Mac Checkbox"); }
}

interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

class WindowsFactory implements GUIFactory {
    public Button createButton() { return new WindowsButton(); }
    public Checkbox createCheckbox() { return new WindowsCheckbox(); }
}

class MacFactory implements GUIFactory {
    public Button createButton() { return new MacButton(); }
    public Checkbox createCheckbox() { return new MacCheckbox(); }
}
```

### Explanation
Abstract Factory groups factories that create consistent product families. The client uses one factory and gets compatible objects.

### Pros
- Ensures consistency among products.
- Hides concrete classes.
- Makes family switching easy.

### Cons
- More complex than simple factory.
- Hard to add new product types.

### Real-world use case
- UI toolkit for Windows, macOS, Linux.
- Database-specific drivers and related objects.

### Interview Questions
1. What is a product family?
2. How is Abstract Factory different from Builder?
3. Why is adding a new product harder in Abstract Factory?

---

## 5. Proxy Pattern

### Intent
Proxy provides a substitute or placeholder for another object to control access to it.

### When to use
- For lazy loading.
- For access control.
- For logging, caching, or remote access.

### UML Diagram
```text
+-----------+       +-------------+
|  Client   |------>|  Subject    |
+-----------+       +-------------+
                         ^     ^
                         |     |
                  +-----------+ +--------------+
                  | RealSubj. | |    Proxy     |
                  +-----------+ +--------------+
```

### Java Example
```java
interface Image {
    void display();
}

class RealImage implements Image {
    private String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        loadFromDisk();
    }

    private void loadFromDisk() {
        System.out.println("Loading " + fileName);
    }

    public void display() {
        System.out.println("Displaying " + fileName);
    }
}

class ProxyImage implements Image {
    private RealImage realImage;
    private String fileName;

    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }

    public void display() {
        if (realImage == null) {
            realImage = new RealImage(fileName);
        }
        realImage.display();
    }
}
```

### Explanation
The proxy implements the same interface as the real object. It adds control logic before delegating to the actual object.

### Pros
- Adds control without changing real object.
- Supports lazy initialization.
- Useful for security and caching.

### Cons
- Adds extra layer of abstraction.
- Can increase response time slightly.

### Real-world use case
- Virtual proxy for large image loading.
- Spring AOP proxies.
- Remote service stubs.

### Interview Questions
1. What is the difference between Proxy and Decorator?
2. What types of proxies exist?
3. Why is Proxy useful in distributed systems?

---

## 6. Flyweight Pattern

### Intent
Flyweight reduces memory usage by sharing common state between many fine-grained objects.

### When to use
- When the system creates a huge number of similar objects.
- When memory optimization matters.

### UML Diagram
```text
+--------------+      +------------------+
| FlyweightFac |----->|    Flyweight     |
+--------------+      +------------------+
| get(key)     |      | operation(ext)   |
+--------------+      +------------------+
```

### Java Example
```java
interface Shape {
    void draw(String color);
}

class Circle implements Shape {
    private String radius;

    public Circle(String radius) {
        this.radius = radius;
    }

    public void draw(String color) {
        System.out.println("Circle radius=" + radius + ", color=" + color);
    }
}

class ShapeFactory {
    private static final java.util.Map<String, Shape> circles = new java.util.HashMap<>();

    public static Shape getCircle(String radius) {
        circles.putIfAbsent(radius, new Circle(radius));
        return circles.get(radius);
    }
}
```

### Explanation
Intrinsic state is shared and stored in the flyweight object. Extrinsic state is passed from the client when needed.

### Pros
- Saves memory.
- Improves performance in large object graphs.

### Cons
- Increases complexity.
- Requires separating intrinsic and extrinsic state carefully.

### Real-world use case
- Text editors sharing font glyphs.
- Game engines sharing trees, bullets, or particles.

### Interview Questions
1. What is intrinsic vs extrinsic state?
2. When is Flyweight not worth using?
3. How does Flyweight improve performance?

---

## 7. Decorator Pattern

### Intent
Decorator adds new behavior to an object dynamically without altering its class.

### When to use
- When behavior should be added at runtime.
- When subclass explosion should be avoided.

### UML Diagram
```text
+------------+       +----------------+
| Component  |<------|   Decorator    |
+------------+       +----------------+
      ^                        ^
      |                        |
+------------+         +------------------+
| Concrete   |         | ConcreteDecorator|
+------------+         +------------------+
```

### Java Example
```java
interface Coffee {
    String getDescription();
    double cost();
}

class SimpleCoffee implements Coffee {
    public String getDescription() { return "Simple Coffee"; }
    public double cost() { return 5.0; }
}

abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;
    public CoffeeDecorator(Coffee coffee) { this.coffee = coffee; }
}

class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) { super(coffee); }
    public String getDescription() { return coffee.getDescription() + ", Milk"; }
    public double cost() { return coffee.cost() + 1.5; }
}
```

### Explanation
A decorator wraps another object that follows the same interface. The wrapper adds behavior before or after delegating calls.

### Pros
- Flexible runtime behavior addition.
- Follows open/closed principle.
- Avoids too many subclasses.

### Cons
- Many small classes may be created.
- Deep decorator chains can be harder to debug.

### Real-world use case
- Java I/O streams.
- UI component enhancements.
- Middleware pipelines.

### Interview Questions
1. How is Decorator different from inheritance?
2. How is Decorator different from Proxy?
3. Why is composition preferred here?

---

## 8. Iterator Pattern

### Intent
Iterator provides a way to access elements of a collection sequentially without exposing its internal structure.

### When to use
- When traversal logic should be separated from collection logic.
- When multiple traversal strategies are needed.

### UML Diagram
```text
+-----------+       +-------------+
| Aggregate |------>|  Iterator   |
+-----------+       +-------------+
      ^                    ^
      |                    |
+-----------+       +-------------+
| Concrete  |       | ConcreteIter|
+-----------+       +-------------+
```

### Java Example
```java
import java.util.*;

class NameRepository implements Iterable<String> {
    private String[] names = {"A", "B", "C"};

    public Iterator<String> iterator() {
        return Arrays.asList(names).iterator();
    }
}
```

### Explanation
Iterator hides how the collection stores elements. The client only uses methods like `hasNext()` and `next()`.

### Pros
- Simplifies collection traversal.
- Supports multiple traversal styles.
- Encapsulates internal structure.

### Cons
- Extra objects may be created.
- Overkill for simple data structures.

### Real-world use case
- Java Collections Framework iterators.
- Database cursor traversal.

### Interview Questions
1. What problem does Iterator solve?
2. How does fail-fast iterator behavior work in Java?
3. Why should internal collection details remain hidden?

---

## 9. Command Pattern

### Intent
Command turns a request into an object, allowing parameterization, queuing, logging, and undo support.

### When to use
- When actions need to be decoupled from invokers.
- For undo/redo, task scheduling, or queues.

### UML Diagram
```text
+---------+       +-----------+       +----------+
| Invoker |------>| Command   |<------| Receiver |
+---------+       +-----------+       +----------+
                        ^
                        |
                 +---------------+
                 | ConcreteCmd   |
                 +---------------+
```

### Java Example
```java
interface Command {
    void execute();
}

class Light {
    public void on() { System.out.println("Light ON"); }
}

class LightOnCommand implements Command {
    private Light light;
    public LightOnCommand(Light light) { this.light = light; }
    public void execute() { light.on(); }
}

class RemoteControl {
    private Command command;
    public void setCommand(Command command) { this.command = command; }
    public void pressButton() { command.execute(); }
}
```

### Explanation
The invoker does not know how the action is performed. It only triggers the command object.

### Pros
- Decouples sender and receiver.
- Supports undo/redo.
- Easy to queue or log commands.

### Cons
- Increases number of classes.
- May feel verbose for simple operations.

### Real-world use case
- GUI buttons and menu actions.
- Job queues.
- Transaction rollback systems.

### Interview Questions
1. How does Command support undo?
2. Why is Command useful in event-driven systems?
3. What is the difference between Command and Strategy?

---

## 10. State Pattern

### Intent
State lets an object alter its behavior when its internal state changes.

### When to use
- When behavior changes based on object state.
- When conditional logic becomes large and repetitive.

### UML Diagram
```text
+---------+       +-----------+
| Context |------>|   State   |
+---------+       +-----------+
                        ^
                        |
            +-----------+-----------+
            |                       |
      +-----------+           +-----------+
      | StateA    |           | StateB    |
      +-----------+           +-----------+
```

### Java Example
```java
interface State {
    void handle(Context context);
}

class Context {
    private State state;
    public void setState(State state) { this.state = state; }
    public void request() { state.handle(this); }
}

class StartState implements State {
    public void handle(Context context) {
        System.out.println("Starting");
        context.setState(new StopState());
    }
}

class StopState implements State {
    public void handle(Context context) {
        System.out.println("Stopping");
    }
}
```

### Explanation
State-specific behavior is placed into separate classes. The context delegates work to the current state object.

### Pros
- Removes complex conditionals.
- Makes state transitions explicit.
- Improves maintainability.

### Cons
- Many state classes may be needed.
- Transition logic can become distributed.

### Real-world use case
- ATM machine states.
- Order processing lifecycle.
- Media player play/pause/stop states.

### Interview Questions
1. How is State different from Strategy?
2. Why is State better than many if-else blocks?
3. Where should transition logic live?

---

## 11. Bridge Pattern

### Intent
Bridge separates an abstraction from its implementation so both can vary independently.

### When to use
- When abstraction and implementation both need extension.
- When inheritance would create a class explosion.

### UML Diagram
```text
+--------------+      +----------------+
| Abstraction  |----->| Implementor    |
+--------------+      +----------------+
      ^                        ^
      |                        |
+--------------+      +----------------+
| RefinedAbs   |      | ConcreteImpl   |
+--------------+      +----------------+
```

### Java Example
```java
interface Device {
    void on();
    void off();
}

class TV implements Device {
    public void on() { System.out.println("TV ON"); }
    public void off() { System.out.println("TV OFF"); }
}

abstract 