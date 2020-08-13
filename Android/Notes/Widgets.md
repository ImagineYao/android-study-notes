设置组件的可见性：

```kotlin
// 以Button为例
val addButton: Button = findViewById(R.id.add_fragment)
addButton.visibility = View.VISIBLE    // 可见
addButton.visibility = View.INVISIBLE  // 不可见但占用位置
addButton.visibility = View.GONE       // 隐藏且不占位置
```

输入完成收起键盘：

```kotlin
val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
imm.hideSoftInputFromWindow(view.windowToken, 0)
```

设置颜色：

```kotlin
view.setBackgroundColor(resources.getColor(R.color.my_red))  // 已过时

// 正确方法
view.setBackgroundResource(R.color.my_red)
```

