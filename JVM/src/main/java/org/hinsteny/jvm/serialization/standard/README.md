# Java standard serialization

**Do some test to java standard object serialize!**

### common questions

- java中, 如果想要对一个对象进行标准的序列化与反序列化操作, 那这个对象所属 class 必须实现 Serializable 接口;
- Serializable 是jdk中一个标识性接口, 既没有成员变量也没有方法, 类似的接口还有 Cloneable, Remote;
- 父类实现 Serializable 接口, 所有子类都继承实现此接口, 也就同样可以进行序列化与反序列化操作;
- 序列化和反序列化一个 class 的对象时, 需要有一个 serialVersionUID, 如果我们编码时不在 class 定义里面指定, 序列化时会默认生成, 
  这个版本号可以用来标识同一个 class 不同版本, 如果反序列化与序列化时的 serialVersionUID 不一致会报 InvalidClassException 异常;
- 序列化一个对象时, 如果它包含了没有实现 Serializable 接口的引用类型属性, 那就会在序列化时抛出 NotSerializableException 异常;
- 序列化的信息格式大致为: class 描述开头标志位|class name| class serialVersionUID|序列化标志位|包含属性成员个数|[{属性类型|属性值}]|结束标识符
- 如果 class 里面的属性被声明为 transient , 那对应的属性就不会被序列化;
- 与 Serializable 关联的还有个 Externalizable 接口, 它里面有两个方法, 可以实现序列化与反序列化过程中的自定义控制, 因为 Serializable 本身默认的
  序列化逻辑太重了, 它可能会生成过多的我们不需要内容, 网络传输传递的多了也会更耗时, 所以我们可以自定义序列化哪些需要的属性;

