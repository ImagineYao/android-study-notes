`FragmentManager`：管理`Activity`中的fragments。

`Activity`中获取`FragmentManager`：使用`supportFragmentManager`属性。

添加、删除、替换`Fragment`，使用`FragmentTransaction`对象，获取`FragmentManager`的Transaction对象：`supportFragmentManager.beginTransaction()`。

注：

1. 一个`Fragment`类可以有多个实例，每个实例只能被同时添加一次。

```kotlin
val fragment1 = MyFragment()
val fragment2 = MyFragment()
val fragment3 = MyFragment()
```

2. 每次对`Fragment`操作都需要执行一次`supportFragmentManager.beginTransaction()`，而不能执行`transaction = supportFragmentManager.beginTransaction()`后再使用`transaction`进行操作。

```kotlin
addButton.setOnClickListener {
    when(count) {
        0 -> supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container1, fragment1).commit()
        1 -> supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container2, fragment2).commit()
        2 -> supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container3, fragment3).commit()
    }
    if (count < 3) count++
}
```