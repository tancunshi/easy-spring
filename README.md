# easy-spring

简易版本spring，很多地方和spring源码并不一样

#### easy-spring 使用的外部依赖
**dom4j**  

dom4j是一个Java的XML API，是jdom的升级品，用来读写XML文件的

**spring-asm**

spring-asm 可以读写字节码文件，通过观察者模式提供扩展。
使用观察者模式，对于观察者而言，无需对整个字节码文件结构了解，只需要注意其关注的一部分结构。
ASM 提供了更为现代的编程模型。对于 ASM 来说，Java class 被描述为一棵树。

**aspectJ**

AspectJ是一个面向切面的框架，它扩展了Java语言。AspectJ定义了AOP语法，它有一个专门的编译器用来生成遵守Java字节编码规范的Class文件。
需要注意的是，Spring不是通过静态编译时，增强字节码来实现aop的。
AspectJ提供了静态编译时，增强字节码的实现。但是spring并没有使用AspectJ实现的aop，spring使用了AspectJ中的一些核心类，像PointcutExpression，
来实现Pointcut相关功能。同时，封装AspectJ的使用细节，对外暴露更加简易，更加符合spring场景的接口。
Spring的aop是动态运行时，生成代理类实现的。有两种方式，一种是JDK代理，另一种是CGLib代理。

**aopalliance**

aopalliance 是Java AOP标准。

**CDLib**



#### 用到的设计模式

##### 1. 组合模式

组合模式（Composite Pattern），又叫部分整体模式，是用于把一组相似的对象当作一个单一的对象。组合模式依据树形结构来组合对象，用来表示部分以及整体层次。

**涉及的类**：InjectionElement，AutowiredFieldElement，InjectionMetadata等

**场景描述**：在实现Autowired功能时，考虑到Autowired能过作用在Field，Method，Constructor等上。
于是对target（目标对象）的需要被注入的 Metadata（元数据）进行了抽象，抽象接口为InjectionElement，实现类有AutowiredFieldElement，
AutowiredMethodElement。一个target有多个Metadata需要进行依赖注入，所以有了InjectionMetadata进行整体注入

##### 2. 观察者模式

当一个对象被修改时，则会自动通知它的依赖对象。观察者模式属于行为型模式。

**涉及的类**：spring-asm 相关的类

**场景描述**：读取类时，如果发现类被@Component修饰，通知观察者

##### 3.建造者模式

建造者模式（Builder Pattern）使用多个简单的对象一步一步构建成一个复杂的对象。这种类型的设计模式属于创建型模式。
建造者涉及product，builder，director。builder只负责装配product。

**涉及的类**：InjectionElementBuilder

**场景描述**：剥离对象建造过程，隐藏复杂实现。

##### 4.责任链模式

责任链模式（Chain of Responsibility Pattern）为请求创建了一个接收者对象的链。通常每个接收者都包含对另一个接收者的引用。
如果一个对象不能处理该请求，那么它会把相同的请求传给下一个接收者，依此类推。

**涉及的类**：ReflectiveMethodInvocation，AbstractAspectJAdvice的继承类

非典型责任链模式（自己命的名字）：责任链中，每个请求接收者如果无法处理请求，将会将请求传给下一个接收者。
但是很多情况下，接收者（AbstractAspectJAdvice的继承类）并不知道下一个接收者是谁，
而通常情况下由请求发起者（ReflectiveMethodInvocation）维护这条责任链，所以回调请求发起者，由请求发起者调用下一个接受者。

