## Java互操作

#### 一、Kotlin中调用Java

- ##### Getter和Setter

```java
public class Person {
    private String name;
    private String gender;
    private int wage = 1000;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public void setWage(int wage) { this.wage = wage; }
}
```

```kotlin
fun main() {
    val person = Person()
    person.name = "yao"
    person.gender = "male"
    println(person.wage)
    // 无法访问，因为wage只有setter，它在Kotlin中不会作为属性可见。
    // Kotlin目前不支持只写属性。
}
```

- ##### 返回void的方法

如果一个Java方法返回void，从Kotlin调用时返回`Unit`。

```kotlin
val person = Person()
person.gender = "male"
println(person.setWage(100))  // kotlin.Unit
```

- ##### 空安全

Java中声明的类型在Kotlin中称为平台类型。调用平台类型变量的方法时，Kotlin不会在编译时报告可空性错误，但运行时调用可能会失败。

```kotlin
val person = Person()
person.gender = "male"
println(person.name.substring(1))  // Exception
```

- ##### 平台类型

— `T!`表示`T`或者`T?`。

— `(Mutable)Collection<T>!`表示可以可变或不可变、可空或不可空的`T`的Java集合。

— `Array<(out) T>!`表示可空或者不可空的`T`（或`T`的子类型）的Java数组。

- ##### 已映射类型

原生类型：

| Java类型 | Kotlin类型     |
| -------- | -------------- |
| byte     | kotlin.Byte    |
| short    | kotlin.Short   |
| int      | kotlin.Int     |
| long     | kotlin.Long    |
| char     | kotlin.Char    |
| float    | kotlin.Float   |
| double   | kotlin.Double  |
| Boolean  | Kotlin.Boolean |

非原生类型：

| Java类型               | Kotlin类型           |
| ---------------------- | -------------------- |
| java.lang.Object       | kotlin.Any!          |
| java.lang.Cloneable    | kotlin.Cloneable!    |
| java.lang.Comparable   | kotlin.Comparable!   |
| java.lang.Enum         | kotlin.Enum!         |
| java.lang.Annotation   | kotlin.Annotation!   |
| java.lang.CharSequence | kotlin.CharSequence! |
| java.lang.String       | kotlin.String!       |
| java.lang.Number       | kotlin.Number!       |
| java.lang.Throwable    | kotlin.Throwable!    |

装箱原始类型：

| Java类型            | Kotlin类型      |
| ------------------- | --------------- |
| java.lang.Byte      | kotlin.Byte?    |
| java.lang.Short     | kotlin.Short?   |
| java.lang.Integer   | kotlin.Int?     |
| java.lang.Long      | kotlin.Long?    |
| java.lang.Character | kotlin.Char?    |
| java.lang.Float     | kotlin.Float?   |
| java.lang.Double    | kotlin.Double?  |
| java.lang.Boolean   | Kotlin.Boolean? |

集合类型：

| Java类型        | Kotlin只读类型  | Kotlin可变类型         | 加载的平台类型                     |
| --------------- | --------------- | ---------------------- | ---------------------------------- |
| Iterator<T>     | Iterator<T>     | MutableIterator<T>     | (Mutable)Iterator<T>!              |
| Iterable<T>     | Iterable<T>     | MutableIterable<T>     | (Mutable)Iterable<T>!              |
| Collection<T>   | Collection<T>   | MutableCollection<T>   | (Mutable)Collection<T>!            |
| Set<T>          | Set<T>          | MutableSet<T>          | (Mutable)Set<T>!                   |
| List<T>         | List<T>         | MutableList<T>         | (Mutable)List<T>!                  |
| ListIterator<T> | ListIterator<T> | MutableListIterator<T> | (Mutable)ListIterator<T>!          |
| Map<K, V>       | Map<K, V>       | MutableMap<K, V>       | (Mutable)Map<K, V>!                |
| Map.Entry<K, V> | Map.Entry<K, V> | MutableMap.Entry<K, V> | (Mutable)Map.(Mutable)Entry<K, V>! |

数组类型

| Java类型 | Kotlin类型                  |
| -------- | --------------------------- |
| int[]    | kotlin.IntArray!            |
| String[] | kotlin.Arrat<(out) String>! |

- ##### Kotlin中的Java泛型

`Foo<? extends Bar>`转换成`Foo<out Bar!>!`

`Foo<? super Bar>`转换成`Foo<in Bar!>!`

`List`转换成`List<*>!`，即`List<out Any?>!`

Kotlin在运行时不保留泛型， Kotlin只允许`is`检测星投影的泛型类型：

```kotlin
if (a is List<Int>) // 错误：无法检测它是否真的是一个 Int 列表
if (a is List<*>)   // OK：不保证列表的内容
```

- ##### Java可变参数

```java
public class JavaArrayExample {
    public void removeIndicesVarArg(int... indices) {
        // 在此编码……
    }
}
```

使用展开运算符`*`来传递`IntArray`，无法传递null。

```kotlin
val javaObj = JavaArrayExample()
val array = intArrayOf(0, 1, 2, 3)
javaObj.removeIndicesVarArg(*array)
```

- ##### 受检异常

在Kotlin中，所有异常是非受检的。

```kotlin
fun render(list: List<*>, to: Appendable) {
    for (item in list) {
        to.append(item.toString()) // Java 会要求我们在这里捕获 IOException
    }
}
```

- ##### 对象方法

Java中的类型 `java.lang.Object` 的所有引用都成了Kotlin中的 `Any`。`Any`只声明了`toString()`、`hashCode()`、`equals()`作为其成员。

— `wait()` / `notify()`：需要将引用转换为`java.lang.Object`。

```kotlin
(foo as java.lang.Object).wait()
```

— `getClass()`

```kotlin
val fooClass = foo::class.java
val fooClass = foo.javaClass
```

— `clone()`：需要继承`kotlin.Cloneable`

```kotlin
class Example : Cloneable {
    override fun clone(): Any {  }
}
```

— `finalize()`

```kotlin
class C {
    protected fun finalize() {
        // 终止化逻辑
    }
}
```

根据 Java 的规则，`finalize()` 不能是 private 的。

- ##### 静态成员

要访问已映射到 Kotlin 类型的 Java 类型的静态成员，需要使用 Java 类型的完整限定名：`java.lang.Integer.bitCount(foo)`。

- ##### SAM转换

SAM = single abstract method

```kotlin
val runnable = Runnable { println("This runs in a runnable") }
```

```kotlin
val executor = ThreadPoolExecutor()
// Java 签名：void execute(Runnable command)
executor.execute { println("This runs in a thread pool") }
```

- ##### 标识符转义

一些 Kotlin 关键字在 Java 中是有效标识符：in、object、is等等。 如果一个 Java 库使用了 Kotlin 关键字作为方法，可以通过反引号（`）字符转义它来调用该方法：

```kotlin
foo.`is`(bar)
```

- ##### JNI

要声明一个在本地（C 或 C++）代码中实现的函数，需要使用 `external` 修饰符来标记它：

```kotlin
external fun foo(x: Int): Double
```



#### 二、Java中调用Kotlin

- ##### 属性

`var name: String`编译成以下Java声明：

```java
private String name;

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}
```

如果属性名称以`is`开头，例如`isOpen`，其`getter`为`isOpen()`，`setter`为`setOpen()`。

- ##### 包级函数

在`org.example`包内`app.kt`文件中声明的所有的函数和属性，包括扩展函数， 都编译成一个名为`org.example.AppKt`的Java类的静态方法。

```kotlin
// app.kt
package org.example
class Util
fun getTime() { /*……*/ }
```

```java
// Java
new org.example.Util();
org.example.AppKt.getTime();
```

可以使用`@JvmName`注解修改生成的Java类的类名。

```kotlin
@file:JvmName("DemoUtils")
package org.example
class Util
fun getTime() { /*……*/ }
```

```java
// Java
new org.example.Util();
org.example.DemoUtils.getTime();
```

如果多个文件中生成了相同的 Java 类名（包名相同并且类名相同或者有相同的`@JvmName`注解），编译器能够生成一个单一的 Java 外观类，它具有指定的名称且包含来自所有文件中具有该名称的所有声明。需要所有相关文件中使用`@JvmMultifileClass`注解。

```kotlin
// oldutils.kt
@file:JvmName("Utils")
@file:JvmMultifileClass

package org.example

fun getTime() { /*……*/ }
```

```kotlin
// newutils.kt
@file:JvmName("Utils")
@file:JvmMultifileClass

package org.example

fun getDate() { /*……*/ }
```

```java
// Java
org.example.Utils.getTime();
org.example.Utils.getDate();
```

- ##### 实例字段

如果一个属性有幕后字段、非私有、没有`open` / `override`或 `const`修饰符并且不是被委托的属性，可以用`@JvmField`注解该属性。

```kotlin
class User(id: String) {
    @JvmField val ID = id
}
```

```java
// Java
class JavaClient {
    public String getID(User user) {
        return user.ID;
    }
}
```

- ##### 静态字段

在具名对象或伴生对象中声明的 Kotlin 属性会在该具名对象或包含伴生对象的类中具有静态幕后字段。

通常这些字段是私有的，但是可以通过以下方式之一暴露出来。

`@JvmField`注解；

`lateinit` 修饰符；

`const` 修饰符。

使用`@JvmField`标注这样的属性使其成为与属性本身具有相同可见性的静态字段。

```kotlin
class Key(val value: Int) {
    companion object {
        @JvmField
        val COMPARATOR: Comparator<Key> = compareBy<Key> { it.value }
    }
}
```

```kotlin
// Java
Key.COMPARATOR.compare(key1, key2);
// Key 类中的 public static final 字段
```

在具名对象或者伴生对象中的一个延迟初始化的属性具有与属性 setter 相同可见性的静态幕后字段。

```kotlin
object Singleton {
    lateinit var provider: Provider
}
```

```java
// Java
Singleton.provider = new Provider();
// 在 Singleton 类中的 public static 非-final 字段
```

（在类中以及在顶层）以`const`声明的属性在 Java 中会成为静态字段：

```kotlin
// example.kt
object Obj {
    const val CONST = 1
}
class C {
    companion object {
        const val VERSION = 9
    }
}
const val MAX = 239
```

```java
// java
int const = Obj.CONST;
int max = ExampleKt.MAX;
int version = C.VERSION;
```

- ##### 静态方法

为具名对象或伴生对象中定义的函数生成静态方法，可以将这些函数标注为`@JvmStatic`。

伴生对象：

```kotlin
class C {
    companion object {
        @JvmStatic fun callStatic() {}
        fun callNonStatic() {}
    }
}
```

```java
// java
C.callStatic(); // 没问题
C.callNonStatic(); // 错误：不是一个静态方法
C.Companion.callStatic(); // 保留实例方法
C.Companion.callNonStatic(); // 唯一的工作方式
```

具名对象：

```kotlin
object Obj {
    @JvmStatic fun callStatic() {}
    fun callNonStatic() {}
}
```

```java
// java
Obj.callStatic(); // 没问题
Obj.callNonStatic(); // 错误
Obj.INSTANCE.callNonStatic(); // 没问题，通过单例实例调用
Obj.INSTANCE.callStatic(); // 也没问题
```

自 Kotlin 1.3 起，`@JvmStatic`也适用于在接口的伴生对象中定义的函数。 这类函数会编译为接口中的静态方法。

```kotlin
interface ChatBot {
    companion object {
        @JvmStatic fun greet(username: String) {
            println("Hello, $username")
        }
    }
}
```

- ##### 接口中的默认方法

自JDK1.8起，Java重的借口可以包含默认方法，如需将一个成员声明为默认，需要使用`@JvmDefault`注解。

```kotlin
interface Robot {
    @JvmDefault fun move() { println("~walking~") }
    fun speak(): Unit
}
```

```java
//java
public class C3PO implements Robot {
    // 来自 Robot 的 move() 实现隐式可用
    @Override
    public void speak() {
        System.out.println("I beg your pardon, sir");
    }
}
```

接口的实现者可以覆盖默认方法。

为了让`@JvmDefault`生效，编译该接口必须带有`-Xjvm-default`参数。 根据添加注解的情况，指定下列值之一：

— `-Xjvm-default=enabled`只添加带有`@JvmDefault` 注解的新方法时使用。

— `-Xjvm-default=compatibility`将`@JvmDefault`添加到以往 API 中就有的方法时使用。

如果将带有 `@JvmDefault` 的方法的接口用作委托， 即使实际的委托类型提供了自己的实现，也会调用默认方法的实现。

```kotlin
interface Producer {
    @JvmDefault fun produce() {
        println("interface method")
    }
}

class ProducerImpl: Producer {
    override fun produce() {
        println("class method")
    }
}

class DelegatedProducer(val p: Producer): Producer by p {
}

fun main() {
    val prod = ProducerImpl()
    DelegatedProducer(prod).produce()  // interface method
}
```

- ##### 可见性

| Kotlin    | Java      |
| --------- | --------- |
| private   | private   |
| protected | protected |
| internal  | public    |
| public    | public    |

- ##### KClass

调用有`KClass`类型参数的 Kotlin 方法：

```java
kotlin.jvm.JvmClassMappingKt.getKotlinClass(MainView.class)
```

- ##### 签名冲突

```kotlin
fun List<String>.filterValid(): List<String>
fun List<Int>.filterValid(): List<Int>
```

这两个函数不能同时定义。如果我们真的希望它们在 Kotlin 中用相同名称，我们需要用`@JvmName` 去标注其中的一个（或两个），并指定不同的名称作为参数。

```kotlin
fun List<String>.filterValid(): List<String>

@JvmName("filterValidInt")
fun List<Int>.filterValid(): List<Int>
```

在Kotlin中它们可以用相同的名称 `filterValid` 来访问，而在Java中，它们分别是`filterValid`和`filterValidInt`。

同样的技巧也适用于属性 `x` 和函数 `getX()` 共存。

```kotlin
val x: Int
    @JvmName("getX_prop")
    get() = 15

fun getX() = 10
```

如需在没有显式实现 getter 与 setter 的情况下更改属性生成的访问器方法的名称，可以使用`@get:JvmName`与`@set:JvmName`：

```kotlin
@get:JvmName("x")
@set:JvmName("changeX")
var x: Int = 23
```

- ##### 生成重载

如果写一个有默认参数值的Kotlin函数，在Java中只会有一个所有参数都存在的完整参数签名的方法可见，如果希望向Java调用者暴露多个重载，可以使用`@JvmOverloads`注解。

```kotlin
class Circle @JvmOverloads constructor(centerX: Int, centerY: Int, radius: Double = 1.0) {
    @JvmOverloads fun draw(label: String, lineWidth: Int = 1, color: String = "red") { /*……*/ }
}
```

```java
// 构造函数：
Circle(int centerX, int centerY, double radius)
Circle(int centerX, int centerY)

// 方法
void draw(String label, int lineWidth, String color) { }
void draw(String label, int lineWidth) { }
void draw(String label) { }
```

- ##### 受检异常

通常Kotlin函数的Java签名不会声明抛出异常。

```kotlin
package demo
fun writeToFile() {
    /*……*/
    throw IOException()
}
```

```java
// Java
try {
  demo.Example.writeToFile();
}
catch (IOException e) { // 错误：writeToFile() 未在 throws 列表中声明 IOException
  // ……
}
```

因为`writeToFile()`没有声明`IOException`，Java 编译器得到一个报错消息。 为了解决这个问题，要在Kotlin中使用 `@Throws` 注解。

```kotlin
@Throws(IOException::class)
fun writeToFile() {
    /*……*/
    throw IOException()
}
```

- ##### 型变的泛型

```kotlin
class Box<out T>(val value: T)

interface Base
class Derived : Base

fun boxDerived(value: Derived): Box<Derived> = Box(value)
fun unboxBase(box: Box<Base>): Base = box.value
```

```java
// java

// 作为返回类型——没有通配符
Box<Derived> boxDerived(Derived value) {  }
 
// 作为参数——有通配符
Base unboxBase(Box<? extends Base> box) {  }
```

在默认不生成通配符的地方需要通配符，可以使用 `@JvmWildcard` 注解：

```kotlin
fun boxDerived(value: Derived): Box<@JvmWildcard Derived> = Box(value)
```

```java
// java
Box<? extends Derived> boxDerived(Derived value) {  }
```

如果不需要默认的通配符转换，可以使用`@JvmSuppressWildcards`注解：

```kotlin
fun unboxBase(box: Box<@JvmSuppressWildcards Base>): Base = box.value
```

```java
// java
Base unboxBase(Box<Base> box) {  }
```

