## Fragment + TabLayout + ViewPager2构建滑动标签页

#### Fragment生命周期

<img src="../Screenshot/viewpager/fragment lifecycle.png" alt="Fragment生命周期" style="zoom:67%;" />

注：`onCreateView()`与`onViewCreated()`的区别：

`onViewCreated()`在`onCreateView()`返回之后立即执行。应该在 `onCreateView()`中渲染布局，在`onViewCreated()`中进行其他View的初始化与逻辑的编写。

<img src="../Screenshot/viewpager/onCreateView.png" alt="Fragment源码中对onCreateView方法的说明" style="zoom:33%;" />



##### 注意点

`TabLayout`的`addOnTabSelectedListener()`方法应该放在`TabLayoutMediator`的`attach()`方法之前。否则初始化之后选择的Tab不会执行`addOnTabSelectedListener()`中的方法。

<figure>
<img src="../Screenshot/viewpager/tablayout2.png" style="width: 60%">
<img src="../Screenshot/viewpager/tablayoutscreenshot2.png" style="width: 30%">
<p align="center">正确用法</p>
</figure>


<figure style="backgroud: #ff0000">
<img src="../Screenshot/viewpager/tablayout1.png" style="width: 60%">
<img src="../Screenshot/viewpager/tablayoutscreenshot1.png" style="width: 30%">
</figure>
<p align="center">错误用法</p>


