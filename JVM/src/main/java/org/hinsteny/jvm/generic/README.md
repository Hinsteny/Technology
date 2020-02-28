# Java 泛型的相关概念与使用

**Generics add stability to your code by making more of your bugs detectable at compile time!**

**Generic methods and generic classes enable programmers to specify,with a single method declaration, a set of related methods, or with a
single class declaration, a set of related types, respectively!**

## Questions

- 1. jdk1.5的时候引入了泛型, Java语言设计者如何实现的泛型，从而保证了jdk始终兼容之前的代码？
- 2. 使用泛型能带来哪些好处呢？
- 3. 泛型的类型边界？
- 4. 在数组中使泛型，如何既能使用数组协变带来的方便性，又不失泛型不变带来的类型安全问题？
- 5. 泛型的擦除地点--边界？
- 6. 泛型基类劫持？ 
- 7. 自限定类型？
- 8. 

### Answers

1. Java 设计者完全将泛型作为一种语法糖加入到了jdk1.5及之后的语法中;
   即泛型对JVM来说是透明的, 有泛型和没泛型代码在编译过后生成的二进制代码(不是Class文件内容)是完全相同的;
2. 泛型可以将具体的类型作为参数传递给方法，接口，类；写一些通用的工具方法，可以同时处理多种类型的数据;
   使用泛型可以替代类型强转; 参考 GenericMethodDealWithArrays
3. 使用泛型边界，我们就能得到在泛型之中调用边界类型中的方法，而不是只能得到一个Object,
   参考 GenericBoundaryCall 
4. 在数组中使用带边界的泛型 这里先说几个概念: 
```
    协变：子类能向父类转换 Person person = new Student(); // 正常
    逆变: 父类能向子类转换 Teacher teacher = (Teacher)person; // 报错
    不变: 两者均不能转变
```
   要解决数组协变与泛型不变的结合问题，还是使用泛型边界 参考 
   




