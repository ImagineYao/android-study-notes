## 函数与Lambda表达式

### 一、函数

#### 1. 声明

`fun`关键字

```kotlin
fun double(x: Int): Int { return 2 * x }
```

#### 2. 用法

函数：传统方法

```kotlin
val res = double(2)
```

成员函数：点表示法

```kotlin
Stream().read()
```

#### 3. 参数

使用Pascal表示法，name: type。每个参数必须有显式类型。

- ##### 默认参数

```kotlin
fun read(b: Array<Byte>, off: Int = 0, len: Int = b.size) {  }
```

省略相应的参数时使用默认值。

重写方法使用与基类型相同的默认参数，重写方法的参数不能有默认值。

如果一个默认参数在无默认值的参数之前，只能使用具名参数调用该函数。

```kotlin
fun foo(bar: Int = 0, baz: Int) {  }
foo(baz = 1) // 使用默认值 bar = 0
```

- ##### 具名参数

给定以下函数：

```kotlin
fun reformat(str: String,
             normalizeCase: Boolean = true,
             upperCaseFirstLetter: Boolean = true,
             divideByCamelHumps: Boolean = false,
             wordSeparator: Char = ' ') {
/*……*/
}
```

使用默认参数调用：

```kotlin
reformat(str)
```

使用非默认参数调用：

```kotlin
reformat(str, true, false, false, '_')
```

使用具名参数调用：

```kotlin
reformat(str,
    normalizeCase = true,
    upperCaseFirstLetter = true,
    divideByCamelHumps = false,
    wordSeparator = '_'
)

reformat(str, wordSeparator = '_')
```

当一个函数调用混用未知参数与具名参数时，所有未知参数要放在第一个具名参数之前。

```kotlin
fun foo(x: Int, y: Int = 3) {
    println("x: $x, y: $y")
}

fun bar(x: Int = 1, y: Int) {
    println("x: $x, y: $y")
}

// 正确
foo(1, y = 2)
bar(1, y = 2)

// 错误
foo(x = 2, 3)
bar(x = 2, 3)
```

可以通过星号操作符将可变数量参数以具名形式传入。

```kotlin
fun foo(vararg strs: String) {
    strs.forEach (::println)
}
foo(strs = *arrayOf("hello", "world", "kotlin"))
```

对于JVM平台：调用Java函数是不能使用具名参数语法。

- ##### 返回Unit的函数

如果一个函数不返回任何有用的值，它的返回类型是`Unit`。`Unit`是只有一个值——`Unit`的类型，不需要显式返回。

```kotlin
fun printHello(name: String?): Unit {
    if (name != null)
        println("Hello $name")
    else
        println("Hi there!")
    // return Unit
    // return
}
```

`Unit`返回类型声明也是可选的。

```kotlin
fun printHello(name: String?) {  }
```

- ##### 单表达式函数

```kotlin
fun double(x: Int): Int = x * 2
fun double(x: Int) = x * 2
```

- ##### 可变数量的参数

参数（通常是最后一个）可以用`vararg`标记：

```kotlin
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}
val list1 = asList(1, 2, 3)
```

将已有数组传递给该函数，使用**伸展（spread）**操作符：数组前面加`*`。

```kotlin
val a = arrayOf(1, 2, 3)
val list2 = asList(-1, 0, *a, 4)
```

只有一个参数可以标注为 `vararg`。如果 `vararg` 参数不是参数列表中的最后一个参数， 可以使用具名参数语法传递其后的参数的值，或者，如果参数具有函数类型，则通过在括号外部传一个`lambda`。

```kotlin
fun <T> asList(vararg ts: T, x: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}
val list1 = asList(1, 2, 3, x = 4)
```

#### 4. 中缀表示法

标有`infix`关键字的函数也可以使用中缀表示法（忽略该调用的点与括号）。中缀函数必须满足：

- 必须是成员函数或扩展函数；
- 必须只有一个参数；
- 参数不得接受可变数量的参数且不能有默认值。

```kotlin
infix fun Int.add(x: Int) = this + x

// 中缀表示法
1 add 2

// 等同于
1.add(2)
```

- ##### 优先级

中缀函数调用的优先级低于算数操作符、类型转换以及rangeTo操作符；

— `1 add 2 + 3`等价于`1 add (2 + 3)`

— `0 until n * 2`等价于`0 until (n * 2)`

— `xs union ys as Set<*>`等价于`xs union (ys as Set<*>)`

高于布尔操作符&&与||、is与in检测以及其他一些操作符。

— `a && b xor c`等价于`a && (b xor c)`

— `a xor b in c`等价于`(a xor b) in c`

使用中缀函数总是要求指定接收者与参数。当使用中缀表达式在当前接收者上调用方法是，需要显式使用`this`。

#### 5. 函数作用域

- ##### 局部函数

一个函数在另一个函数内部。

局部函数可以访问外部函数（即闭包）的局部变量。

```kotlin
fun dfs(graph: Graph) {
    val visited = HashSet<Vertex>()
    fun dfs(current: Vertex) {
        if (!visited.add(current)) return
        for (v in current.neighbors)
            dfs(v)
    }

    dfs(graph.vertices[0])
}
```

- ##### 成员函数

在类或对象内部定义的函数，以点表示法调用。

```kotlin
class Sample {
    fun foo() { print("Foo") }
}

Sample().foo()
```

- ##### 尾递归函数

要符合 `tailrec` 修饰符的条件的话，函数必须将其自身调用作为它执行的最后一个操作。在递归调用后有更多代码时，不能使用尾递归，并且不能用在try/catch/finally 块中。

```kotlin
val eps = 1E-10 // "good enough", could be 10^-15

tailrec fun findFixPoint(x: Double = 1.0): Double
        = if (Math.abs(x - Math.cos(x)) < eps) x else findFixPoint(Math.cos(x))
```



### 二、高阶函数函数与lambda表达式

#### 1. 函数类型

使用类似`(Int) -> String`的一系列函数类型来处理函数的声明：`val onClick() -> Unit = ...`

— 所有函数类型都有一个圆括号括起来的参数类型列表以及一个返回类型：`(A, B) -> C` 表示接受类型分别为 `A` 与 `B` 两个参数并返回一个 `C` 类型值的函数类型。 参数类型列表可以为空，如 `() -> A`。`Unit`返回类型不可省略。

— 函数类型可以有一个额外的*接收者*类型，它在表示法中的点之前指定： 类型 `A.(B) -> C` 表示可以在 `A` 的接收者对象上以一个 `B` 类型参数来调用并返回一个 `C` 类型值的函数。

— 挂起函数属于特殊种类的函数类型，它的表示法中有一个 `suspend` 修饰符 ，例如 `suspend () -> Unit` 或者 `suspend A.(B) -> C`。

**函数类型表示法**可以选择性地包含函数的参数名：`(x: Int, y: Int) -> Point`。

— 如果将函数类型指定为可空：`((Int, Int) -> Int)?`。

— 函数类型可以使用圆括号进行结合：`(Int) -> ((Int) -> Unit)`。

— 箭头表示法是右结合的，`(Int) -> (Int) -> Unit`与前述等价。

- ##### 函数类型实例化

（1）使用函数字面值的代码块：

  — lambda表达式：`{a, b -> a + b}`

  — 匿名函数：`fun (s: String): Int { return s.toIntOrNull() ?: 0 }`

（2）使用已有声明的可调用引用：

  — 顶层、局部、成员、扩展函数：`::isOdd`、`String::toInt`

  — 顶层、成员、扩展属性：`List<Int>::size`

  — 构造函数：`::Regex`

（3）使用实现函数类型接口的自定义类的实例：

```kotlin
class IntTransformer: (Int) -> Int {
    override operator fun invoke(x: Int): Int = TODO()
}

val intFunction: (Int) -> Int = IntTransformer()
```

```kotlin
val a = { i: Int -> i + 1 } // 推断出的类型是 (Int) -> Int
```

#### 2. Lambda表达式与匿名函数

- ##### Lambda表达式语法

```kotlin
val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
```

如果把所有可选标注都留下：

```kotlin
val sum = { x: Int, y: Int -> x + y }
```

- ##### 传递末尾的Lambda表达式

如果函数的最后一个参数是函数，作为相应参数传入的lambda表达式可以放在圆括号之外：

```kotlin
val product = itmes.fold(1) {acc, e -> acc * e}
```

等同于

````kotlin
val product = itmes.fold(1, {acc, e -> acc * e})
````

如果该 lambda 表达式是调用时唯一的参数，那么圆括号可以完全省略。

- ##### it：单个参数的隐式名称

如果编译器自己可以识别出签名，也可以不用声明唯一的参数并忽略 `->`。 该参数会隐式声明为 `it`。

```kotlin
ints.filter { it > 0 } // 这个字面值是“(it: Int) -> Boolean”类型的
```

- ##### 从lambda表达式中返回一个值

```kotlin
ints.filter {
    val shouldFilter = it > 0 
    shouldFilter
}

ints.filter {
    val shouldFilter = it > 0 
    return@filter shouldFilter
}
```

- ##### 下划线用于未使用的变量

```kotlin
map.forEach{ _, value -> println("$value!") }
```

- ##### 匿名函数

lambda表达式的缺点：无法指定函数的返回类型。如果需要显式指定，可以使用**匿名函数**。

```kotlin
fun(x: Int, y: Int): Int = x + y

fun(x: Int, y: Int): Int {
    return x + y
}
```

- ##### 闭包

lambda表达式或者匿名函数（以及局部函数和对象表达式）可以访问起闭包，即在外部作用域中声明的变量。在lambda表达式中可以修改闭包中捕获的变量。

```kotlin
var sum = 0
ints.filter { it > 0 }.forEach {
    sum += it
}
print(sum)
```

- ##### 带有接收者的函数字面值

带有接收者的函数类型，例如`A.(B) -> C`，可以用特殊形式的函数字面值实例化——带有接收者的函数字面值。

```kotlin
val sum: Int.(Int) -> Int = { other -> plus(other) }
val sum = fun Int.(other: Int): Int = this + other

// 调用
println(sum(2, 3))
println(2.sum(3))
```



### 三、内联函数

当一个函数被声明为`inline`时，它的函数体是内联的，也就是说：函数体会被直接替换到函数被调用的地方。

#### 1. 禁用内联

如果只希望内联一部分传给内联函数的lambda表达式参数，可以用`noinline`修饰符标记不希望内联的函数参数。

```kotlin
inline fun foo(inlined: () -> Unit, noinline notInlined: () -> Unit) { …… }
```

可以内联的lambda表达式只能在内联函数内部调用或者作为可内联的参数传递，`noinline`的可以以任何方式操作。

#### 2. 非局部返回

如果要退出一个lambda表达式们必须使用一个标签，并且lambda表达式内部禁止裸`return`。

```kotlin
fun ordinaryFunction(block: () -> Unit) {
    println("hi!")
}
fun foo() {
    ordinaryFunction {
        return@ordinaryFunction  // 正确
        // return // 错误：不能使 `foo` 在此处返回
    }
}

inline fun inlined(block: () -> Unit) {
    println("hi!")
}
fun bar() {
    inlined {
        return // OK：该 lambda 表达式是内联的
    }
}

fun main() {
    foo()
    bar()
}
```

这种返回（位于 lambda 表达式中，但退出包含它的函数）称为**非局部**返回。

如果想 `lambda` 也被 `inline`，但是不影响调用方的控制流程，那么就要用 `crossinline`。

#### 3. 具体化的类型参数

访问一个作为参数传给我们的一个类型。

```kotlin
fun <T> TreeNode.findParentOfType(clazz: Class<T>): T? {  }

// 调用
treeNode.findParentOfType(MyTreeNode::class.java)
```

内联函数支持**具体化的类型参数**

```kotlin
inline fun <reified T> TreeNode.findParentOfType(): T? {  }

// 调用
treeNode.findParentOfType<MyTreeNode>()
```

#### 4. 内联属性

`inline` 修饰符可用于没有幕后字段的属性的访问器。可以标注独立的属性访问器。

```kotlin
val foo: Foo
    inline get() = Foo()

var bar: Bar
    get()
    inline set(v) {  }
```

也可以标注整个属性，将它的两个访问器都标记为内联。

```kotlin
inline var bar: Bar
    get()
    set(v) {  }
```

#### 5. 公有 API 内联函数的限制

当一个内联函数是 `public` 或 `protected` 而不是 `private` 或 `internal` 声明的一部分时，就会认为它是一个**模块级**的公有 API。

