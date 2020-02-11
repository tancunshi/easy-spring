# easy-spring
手写个简易的spring

#### easy-spring 使用的外部依赖
**dom4j**  

dom4j是一个Java的XML API，是jdom的升级品，用来读写XML文件的

**spring-asm**

spring-asm 可以读写字节码文件，通过观察者模式提供扩展。
使用观察者模式，对于观察者而言，无需对整个字节码文件结构了解，只需要注意其关注的一部分结构。
ASM 提供了更为现代的编程模型。对于 ASM 来说，Java class 被描述为一棵树。

#### 用到的设计模式

##### 1. 组合模式

组合模式（Composite Pattern），又叫部分整体模式，是用于把一组相似的对象当作一个单一的对象。组合模式依据树形结构来组合对象，用来表示部分以及整体层次。

**涉及的类**：InjectionElement，AutowiredFieldElement，InjectionMetadata等

**场景描述**：在实现Autowired功能时，考虑到Autowired能过作用在Field，Method，Constructor等上。
于是对target（目标对象）的需要被注入的 Metadata（元数据）进行了抽象，抽象接口为InjectionElement，实现类有AutowiredFieldElement，
AutowiredMethodElement（还没实现）。一个target有多个Metadata需要进行依赖注入，所以有了InjectionMetadata进行整体注入

##### 2. 观察者模式

当一个对象被修改时，则会自动通知它的依赖对象。观察者模式属于行为型模式。

**涉及的类**：spring-asm 相关的类

**场景描述**：读取类时，如果发现类被@Component修饰，通知观察者
