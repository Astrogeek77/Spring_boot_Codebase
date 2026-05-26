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

abstract class Remote {
    protected Device device;
    public Remote(Device device) { this.device = device; }
    abstract void togglePower();
}

class BasicRemote extends Remote {
    public BasicRemote(Device device) { super(device); }
    void togglePower() { device.on(); }
}
```

### Explanation
The abstraction holds a reference to the implementation interface. Both sides can evolve separately.

### Pros
- Reduces subclass explosion.
- Promotes composition.
- Independent extensibility.

### Cons
- Adds abstraction layers.
- Can be harder to understand initially.

### Real-world use case
- Remote and device hierarchy.
- Shape abstraction with different rendering engines.

### Interview Questions
1. When should Bridge be preferred over inheritance?
2. What two dimensions does Bridge separate?
3. How does Bridge reduce class explosion?

---

## 12. Chain of Responsibility Pattern

### Intent
Chain of Responsibility passes a request along a chain of handlers until one handles it.

### When to use
- When multiple objects can process a request.
- When sender should not know the exact receiver.

### UML Diagram
```text
+---------+      +----------------+
| Client  |----->| Handler        |
+---------+      +----------------+
                       |
                       v
                +--------------+
                | Next Handler  |
                +--------------+
```

### Java Example
```java
abstract class Logger {
    protected Logger next;
    public void setNext(Logger next) { this.next = next; }

    public void log(String level, String message) {
        if (canHandle(level)) {
            write(message);
        } else if (next != null) {
            next.log(level, message);
        }
    }

    protected abstract boolean canHandle(String level);
    protected abstract void write(String message);
}

class ErrorLogger extends Logger {
    protected boolean canHandle(String level) { return "ERROR".equals(level); }
    protected void write(String message) { System.out.println("Error: " + message); }
}
```

### Explanation
Each handler decides whether to process the request or pass it onward. This creates a flexible request-processing pipeline.

### Pros
- Decouples sender and receiver.
- Flexible ordering of handlers.
- Easy to add or remove handlers.

### Cons
- Request may go unhandled.
- Debugging the chain can be tricky.

### Real-world use case
- Logging frameworks.
- Servlet filters.
- Approval workflows.

### Interview Questions
1. What happens if no handler processes the request?
2. How is this pattern used in middleware?
3. How is Chain of Responsibility different from Command?

---

## 13. Observer Pattern

### Intent
Observer defines a one-to-many dependency so when one object changes state, all dependents are notified automatically.

### When to use
- When multiple objects depend on one subject.
- For event-driven systems.

### UML Diagram
```text
+---------+        +-----------+
| Subject |<-------| Observer  |
+---------+        +-----------+
| attach()|        | update()  |
| notify()|        +-----------+
+---------+
```

### Java Example
```java
import java.util.*;

interface Observer {
    void update(String message);
}

class User implements Observer {
    private String name;
    public User(String name) { this.name = name; }
    public void update(String message) {
        System.out.println(name + " received: " + message);
    }
}

class Channel {
    private List<Observer> subscribers = new ArrayList<>();
    public void subscribe(Observer observer) { subscribers.add(observer); }
    public void notifySubscribers(String msg) {
        for (Observer o : subscribers) o.update(msg);
    }
}
```

### Explanation
Observers register themselves with a subject. When the subject changes, it notifies all registered observers.

### Pros
- Supports loose coupling.
- Good for event broadcasting.
- Dynamic subscription management.

### Cons
- Notification chains can be hard to trace.
- Risk of memory leaks if observers are not removed.

### Real-world use case
- GUI event listeners.
- Publish-subscribe systems.
- Stock price notifications.

### Interview Questions
1. How is Observer different from Pub/Sub?
2. What problems arise with too many observers?
3. How do you avoid memory leaks in Observer?

---

## 14. Prototype Pattern

### Intent
Prototype creates new objects by copying an existing object, called the prototype.

### When to use
- When object creation is expensive.
- When many similar objects are needed.

### UML Diagram
```text
+-------------+
| Prototype   |
+-------------+
| clone()     |
+-------------+
      ^
      |
+-------------+
| Concrete    |
+-------------+
```

### Java Example
```java
class Document implements Cloneable {
    private String text;

    public Document(String text) {
        this.text = text;
    }

    public Document clone() {
        try {
            return (Document) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
```

### Explanation
Instead of creating objects from scratch, the system clones an existing one. This is useful when setup cost is high.

### Pros
- Faster than repeated initialization.
- Simplifies creation of similar objects.

### Cons
- Deep cloning can be difficult.
- Clone behavior may be tricky with references.

### Real-world use case
- Copying graphic objects in editors.
- Game enemy templates.

### Interview Questions
1. What is shallow vs deep copy?
2. When is Prototype better than Factory?
3. Why can cloning be risky in Java?

---

## 15. Composite Pattern

### Intent
Composite lets clients treat individual objects and compositions of objects uniformly.

### When to use
- When working with tree structures.
- When leaf and container objects should be used the same way.

### UML Diagram
```text
+-----------+
| Component |
+-----------+
      ^
      |
+-----------+      +-----------+
| Leaf      |      | Composite |
+-----------+      +-----------+
                   | children   |
                   +-----------+
```

### Java Example
```java
import java.util.*;

interface Employee {
    void showDetails();
}

class Developer implements Employee {
    private String name;
    public Developer(String name) { this.name = name; }
    public void showDetails() { System.out.println("Developer: " + name); }
}

class Manager implements Employee {
    private List<Employee> employees = new ArrayList<>();
    public void add(Employee e) { employees.add(e); }
    public void showDetails() {
        for (Employee e : employees) e.showDetails();
    }
}
```

### Explanation
Composite defines a common interface for both leaf objects and containers. This allows recursive tree processing with uniform client code.

### Pros
- Simplifies tree operations.
- Uniform handling of objects.
- Easy recursive composition.

### Cons
- Can make design too generic.
- Hard to restrict allowed child types sometimes.

### Real-world use case
- File systems with files and folders.
- UI component trees.
- Organization hierarchies.

### Interview Questions
1. Why is Composite useful for recursive structures?
2. What is the role of the component interface?
3. Can leaves support child operations?

---

## 16. Facade Pattern

### Intent
Facade provides a simplified interface to a complex subsystem.

### When to use
- When a subsystem is complex.
- When client code should be shielded from many dependencies.

### UML Diagram
```text
+--------+      +---------+
| Client |----->| Facade  |
+--------+      +---------+
                     |
         +-----------+-----------+
         |           |           |
     +-------+   +-------+   +-------+
     | Sub1  |   | Sub2  |   | Sub3  |
     +-------+   +-------+   +-------+
```

### Java Example
```java
class CPU { void start() { System.out.println("CPU started"); } }
class Memory { void load() { System.out.println("Memory loaded"); } }
class Disk { void read() { System.out.println("Disk read"); } }

class ComputerFacade {
    private CPU cpu = new CPU();
    private Memory memory = new Memory();
    private Disk disk = new Disk();

    public void startComputer() {
        cpu.start();
        memory.load();
        disk.read();
    }
}
```

### Explanation
Facade wraps several subsystem classes and offers a smaller, easier API. It reduces complexity for the client.

### Pros
- Simplifies usage.
- Reduces coupling to subsystem.
- Improves readability.

### Cons
- Can become a god object if overloaded.
- May hide useful subsystem capabilities.

### Real-world use case
- Spring `JdbcTemplate` as simplified database access.
- Payment gateway wrapper APIs.

### Interview Questions
1. How is Facade different from Adapter?
2. Does Facade reduce subsystem complexity or just hide it?
3. When can Facade become harmful?

---

## 17. Mediator Pattern

### Intent
Mediator centralizes communication between related objects so they do not reference each other directly.

### When to use
- When many objects communicate in complex ways.
- When object interactions become tangled.

### UML Diagram
```text
+-----------+
| Mediator  |
+-----------+
      ^
      |
+-----------+   +-----------+   +-----------+
| Colleague |   | Colleague |   | Colleague |
+-----------+   +-----------+   +-----------+
```

### Java Example
```java
interface ChatMediator {
    void sendMessage(String msg, UserColleague user);
    void addUser(UserColleague user);
}

class ChatRoom implements ChatMediator {
    private java.util.List<UserColleague> users = new java.util.ArrayList<>();
    public void addUser(UserColleague user) { users.add(user); }
    public void sendMessage(String msg, UserColleague sender) {
        for (UserColleague u : users) {
            if (u != sender) u.receive(msg);
        }
    }
}

abstract class UserColleague {
    protected ChatMediator mediator;
    protected String name;
    public UserColleague(ChatMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }
    abstract void send(String msg);
    abstract void receive(String msg);
}
```

### Explanation
Objects communicate through the mediator instead of directly. This reduces dependencies between colleague objects.

### Pros
- Reduces tight coupling.
- Centralizes communication rules.
- Easier to modify interactions.

### Cons
- Mediator can become overly complex.
- Central object may become a bottleneck.

### Real-world use case
- Chat room systems.
- Air traffic control.
- GUI dialog coordination.

### Interview Questions
1. How is Mediator different from Observer?
2. What risk exists if Mediator grows too large?
3. Why does Mediator reduce coupling?

---

## 18. Memento Pattern

### Intent
Memento captures and restores an object's internal state without violating encapsulation.

### When to use
- For undo/rollback features.
- When snapshots of state are required.

### UML Diagram
```text
+----------+      +----------+      +-----------+
| Originator|---->| Memento  |<-----| Caretaker |
+----------+      +----------+      +-----------+
```

### Java Example
```java
class EditorMemento {
    private final String content;
    public EditorMemento(String content) { this.content = content; }
    public String getContent() { return content; }
}

class Editor {
    private String content;
    public void setContent(String content) { this.content = content; }
    public EditorMemento save() { return new EditorMemento(content); }
    public void restore(EditorMemento memento) { this.content = memento.getContent(); }
}
```

### Explanation
The originator creates a snapshot object that stores state. A caretaker holds the snapshot and can restore it later.

### Pros
- Supports undo/restore cleanly.
- Preserves encapsulation.

### Cons
- Can consume memory with many snapshots.
- Snapshot management may become expensive.

### Real-world use case
- Text editor undo.
- Game save points.
- Transaction rollback snapshots.

### Interview Questions
1. How is Memento different from Command in undo systems?
2. What is the role of caretaker?
3. Why can Memento be memory intensive?

---

## 19. Template Method Pattern

### Intent
Template Method defines the skeleton of an algorithm in a base class and lets subclasses redefine certain steps.

### When to use
- When algorithm structure is fixed but some steps vary.
- When common workflow should be reused.

### UML Diagram
```text
+----------------+
| AbstractClass  |
+----------------+
| templateMethod()|
| step1()        |
| step2()        |
+----------------+
        ^
        |
+----------------+
| ConcreteClass  |
+----------------+
```

### Java Example
```java
abstract class DataProcessor {
    public final void process() {
        readData();
        processData();
        saveData();
    }

    abstract void readData();
    abstract void processData();

    void saveData() {
        System.out.println("Saving data");
    }
}

class CSVProcessor extends DataProcessor {
    void readData() { System.out.println("Reading CSV"); }
    void processData() { System.out.println("Processing CSV"); }
}
```

### Explanation
The base class controls the overall algorithm sequence. Subclasses customize only selected steps.

### Pros
- Reuses algorithm structure.
- Enforces workflow consistency.
- Reduces duplicate code.

### Cons
- Inheritance-based, so less flexible than composition.
- Changes in base algorithm affect all subclasses.

### Real-world use case
- Data import pipelines.
- Framework lifecycle hooks.
- Game loop skeletons.

### Interview Questions
1. How is Template Method different from Strategy?
2. Why is the template method often final?
3. What is a hook method?

---

## 20. Visitor Pattern

### Intent
Visitor lets new operations be added to object structures without changing the classes of the elements.

### When to use
- When operations change more often than element classes.
- When working with structured object trees.

### UML Diagram
```text
+---------+        +---------+
| Visitor |<-------| Element |
+---------+        +---------+
      ^                  ^
      |                  |
+-------------+    +-------------+
| ConcreteVis |    | ConcreteElem|
+-------------+    +-------------+
```

### Java Example
```java
interface ComputerPart {
    void accept(ComputerPartVisitor visitor);
}

class Keyboard implements ComputerPart {
    public void accept(ComputerPartVisitor visitor) { visitor.visit(this); }
}

interface ComputerPartVisitor {
    void visit(Keyboard keyboard);
}

class DisplayVisitor implements ComputerPartVisitor {
    public void visit(Keyboard keyboard) {
        System.out.println("Displaying keyboard");
    }
}
```

### Explanation
Each element accepts a visitor, and the visitor performs type-specific logic. This is a form of double dispatch.

### Pros
- Adds operations without modifying element classes.
- Good for stable object structures.

### Cons
- Hard to add new element types.
- Can be complex to understand.

### Real-world use case
- AST processing in compilers.
- Reporting over object hierarchies.

### Interview Questions
1. What is double dispatch?
2. When is Visitor a bad choice?
3. Why is Visitor good for compiler design?

---

## 21. Interpreter Pattern

### Intent
Interpreter defines a grammar for a language and uses an interpreter to evaluate sentences in that language.

### When to use
- For simple languages or expression evaluation.
- When grammar rules can be modeled as classes.

### UML Diagram
```text
+----------------+
| AbstractExpr   |
+----------------+
| interpret()    |
+----------------+
      ^
      |
+-------------+   +--------------+
| Terminal    |   | NonTerminal  |
+-------------+   +--------------+
```

### Java Example
```java
interface Expression {
    int interpret();
}

class NumberExpression implements Expression {
    private int number;
    public NumberExpression(int number) { this.number = number; }
    public int interpret() { return number; }
}

class AddExpression implements Expression {
    private Expression left, right;
    public AddExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    public int interpret() { return left.interpret() + right.interpret(); }
}
```

### Explanation
Expressions are represented as objects and combined into trees. Evaluating the tree interprets the language.

### Pros
- Simple to extend for small grammars.
- Makes grammar structure explicit.

### Cons
- Becomes complex and slow for large grammars.
- Many classes may be needed.

### Real-world use case
- Rule engines.
- Arithmetic expression evaluators.
- Query language interpreters.

### Interview Questions
1. When should Interpreter be avoided?
2. How does it relate to expression trees?
3. Why is it unsuitable for complex languages?

---

## 22. Strategy Pattern

### Intent
Strategy defines a family of algorithms, encapsulates each one, and makes them interchangeable.

### When to use
- When multiple algorithms solve the same problem.
- When behavior should be chosen at runtime.

### UML Diagram
```text
+---------+      +-----------+
| Context |----->| Strategy  |
+---------+      +-----------+
                       ^
                       |
           +-----------+-----------+
           |                       |
      +-----------+          +-----------+
      | StrategyA |          | StrategyB |
      +-----------+          +-----------+
```

### Java Example
```java
interface PaymentStrategy {
    void pay(int amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid by credit card: " + amount); }
}

class UpiPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid by UPI: " + amount); }
}

class ShoppingCart {
    private PaymentStrategy strategy;
    public void setStrategy(PaymentStrategy strategy) { this.strategy = strategy; }
    public void checkout(int amount) { strategy.pay(amount); }
}
```

### Explanation
Different algorithms are packaged into interchangeable classes. The context delegates execution to the selected strategy.

### Pros
- Eliminates conditional logic for algorithm selection.
- Easy runtime switching.
- Supports open/closed principle.

### Cons
- Client must know available strategies.
- More classes are introduced.

### Real-world use case
- Payment modes.
- Sorting or compression algorithm selection.
- Route planning methods.

### Interview Questions
1. How is Strategy different from State?
2. Why is Strategy preferred over large switch statements?
3. Who chooses the strategy, client or context?

---

## 23. Adapter Pattern

### Intent
Adapter converts the interface of one class into another interface the client expects.

### When to use
- When integrating incompatible interfaces.
- When reusing legacy code with new systems.

### UML Diagram
```text
+--------+      +---------+      +---------+
| Client |----->| Target  |<-----| Adapter |
+--------+      +---------+      +---------+
                                      |
                                      v
                                  +--------+
                                  | Adaptee|
                                  +--------+
```

### Java Example
```java
interface MediaPlayer {
    void play(String audioType, String fileName);
}

class AdvancedMediaPlayer {
    public void playVlc(String fileName) {
        System.out.println("Playing VLC file: " + fileName);
    }
}

class MediaAdapter implements MediaPlayer {
    private AdvancedMediaPlayer advancedPlayer = new AdvancedMediaPlayer();

    public void play(String audioType, String fileName) {
        if ("vlc".equalsIgnoreCase(audioType)) {
            advancedPlayer.playVlc(fileName);
        }
    }
}
```

### Explanation
Adapter acts as a translator between the client and an incompatible class. It allows old and new systems to work together.

### Pros
- Reuses existing code.
- Helps integrate legacy systems.
- Keeps client code unchanged.

### Cons
- Adds an extra layer.
- Too many adapters can complicate design.

### Real-world use case
- Third-party API integration.
- Legacy service wrapping.
- Power plug adapters.

### Interview Questions
1. How is Adapter different from Facade?
2. What is the difference between class adapter and object adapter?
3. Why is Adapter important in legacy modernization?

---

## Pattern Selection Guide

| Scenario | Suitable Pattern |
|---------|------------------|
| Complex object creation | Builder |
| Single shared instance | Singleton |
| Centralized object creation | Factory |
| Related object families | Abstract Factory |
| Access control/lazy load | Proxy |
| Memory optimization | Flyweight |
| Add behavior dynamically | Decorator |
| Sequential traversal | Iterator |
| Request as object | Command |
| Behavior based on state | State |
| Separate abstraction and implementation | Bridge |
| Request pipeline | Chain of Responsibility |
| Event notification | Observer |
| Clone existing object | Prototype |
| Tree structures | Composite |
| Simplify subsystem | Facade |
| Centralized communication | Mediator |
| Save and restore snapshots | Memento |
| Fixed algorithm skeleton | Template Method |
| Add external operations | Visitor |
| Evaluate grammar/expression | Interpreter |
| Switch algorithms | Strategy |
| Interface conversion | Adapter |

## Common Interview Comparison Topics

### Builder vs Factory
- Factory focuses on which object to create.
- Builder focuses on how to create a complex object step by step.

### Decorator vs Proxy
- Decorator primarily adds behavior.
- Proxy primarily controls access.

### State vs Strategy
- State changes behavior based on internal transitions.
- Strategy selects one of many interchangeable algorithms.

### Adapter vs Facade
- Adapter makes incompatible interfaces work together.
- Facade simplifies access to a subsystem.

### Observer vs Mediator
- Observer is one-to-many notification.
- Mediator centralizes many-to-many communication.

## Study Tips
- Learn the intent of each pattern first.
- Understand the problem each pattern solves.
- Practice identifying patterns in frameworks like Spring, Java Collections, and Java I/O.
- In interviews, explain trade-offs instead of only definitions.