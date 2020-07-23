@file:JvmName("KotlinMain")  // Java互操作

fun main() {
    testClasses()
    testFunctions()
    testCollections()
}

fun testClasses() {
    fun testPoint2D() {
        println("----------------- Point2D Test -----------------")

        val p1 = Point2D(1.0, 1.0)
        val p2 = Point2D()
        p2.x = -1.0
        p2.y = -1.0

        Point2D.getCoordinateInfo()
        println("Point1: Point2D(${p1.x}, ${p1.y}), Point2: $p2")  // Java互操作：调用get()方法
        println("Euclidean Distance:  ${p1.getDistance(p2, Point2D.EUCLIDEAN)}")
        println("Manhattan Distance:  ${p2.getDistance(p1, Point2D.MANHATTAN)}")
        println("Origin Distance:     ${p1.distanceToZero}")  // Java互操作：getProperty()方法转换为属性property

        println("------------------------------------------------")
    }

    fun testPoint3D() {
        // Point3D的扩展函数：计算向量点乘
        fun Point3D.dotProduct(another: Point3D): Double {
            val thisVector = this.toVector3D()
            val anotherVector = another.toVector3D()
            return thisVector.dot(anotherVector)
        }

        println("----------------- Point3D Test -----------------")

        val p1 = Point3D(1.0, 0.0, 0.0)
        val p2 = Point3D()
        p2.x = 0.0
        p2.y = 1.0
        p2.z = 0.0

        Point3D.getCoordinateInfo()
        println("Point1: $p1, Point2: $p2")
        println("Euclidean Distance:  ${p1.getDistance(p2, type = Point3D.EUCLIDEAN)}")  // 具名参数
        println("Manhattan Distance:  ${p2.getDistance(p1, Point3D.MANHATTAN)}")
        println("Origin Distance:     ${p1.distanceToZero}")
        println("Dot Product:         ${p1.dotProduct(p2)}")
        println("Cross Product:       ${(p1.toVector3D()) cross (p2.toVector3D())}")  // 中缀函数
        println()
        println("p1's coordinateInfo: ${p1.coordinateInfo}")
        p1.coordinateInfo = "Spherical coordinate system"
        println("p1's coordinateInfo: ${p1.coordinateInfo}")

        println("------------------------------------------------")
    }

    println("Classes Test ===================================")
    testPoint2D()
    testPoint3D()
    println("================================================")
    println()
}

inline fun<T, R: Comparable<R>> List<T>.getMaxBy(block: (T) -> R): T? {
    if (isEmpty()) return null
    var maxElement = get(0)
    var maxValue = block(maxElement)
    for (element in this) {
        val value = block(element)
        if (value > maxValue) {
            maxElement = element
            maxValue = value
        }
    }
    return maxElement
}

fun testFunctions() {
    println("Functions Test =================================")

    val numbers = listOf("one", "two", "three", "four")
    println("Using Kotlin MaxBy:     ${numbers.maxBy { it.length }}")
    println("Using Diy getMaxBy:     ${numbers.getMaxBy { it.first() }}")

    println("================================================")
    println()
}

fun testCollections() {
    fun testList() {
        println("------------------ List Test -------------------")

        val pointList = listOf(Point2D(1.0, 0.0), Point2D(0.0, 1.0))
        val pointLists = listOf(pointList, setOf(Point2D(-1.0, 0.0), Point2D(0.0, -1.0)))
        println("PointList:              $pointList")
        println("PointLists:             $pointLists")
        println("Flatten PointLists:     ${pointLists.flatten()}")  // 1. 转换 —— 打平

        pointList.map {
            it.x *= 2
            it.y *= 2
            it
        }
        println("PointList * 2:          $pointList")  // 1. 转换 —— 映射

        val zippedList = listOf("Point1", "Point2").zip(pointList) { name, point -> "$name is $point" }
        println("Zip:                    $zippedList")  // 1. 转换 —— 双路合并

        println("Associate By X:         ${pointList.associateBy { it.x }}")  // 1. 转换 —— 关联
        println("Associate With X:       ${pointList.associateWith { it.x }}")
        println("Filter X > 0:           ${pointList.filter { it.x > 0 }}")  // 2. 过滤
        println("Plus:                   ${pointList + Point2D(1.0, 1.0)}")  // 3. 加减操作符
        println("Minus:                  ${pointList - Point2D(0.0, 2.0)}")
        println("Group By X:             ${pointList.groupBy { it.x }}")  // 4. 分组
        println("Parts: Take             ${pointList.take(1)}")  // 5. 取部分
        println("Parts: SubList          ${pointList.subList(0, 1)}")
        println("Parts: Drop             ${pointList.drop(1)}")
        println("Parts: Chunked          ${pointList.chunked(1)}")
        println("Parts: Windowed         ${pointList.windowed(1)}")
        println("Get One Element         ${pointList.elementAt(0)}")  // 6. 取单个
        println("Sorted Descending by Y: ${pointList.sortedByDescending { it.y }}")  // 7. 排序
        println("Reversed:               ${pointList.asReversed()}")
        println("Shuffled:               ${pointList.shuffled()}")
        println("Aggregate: Half X Sum   ${pointList.sumByDouble { it.x / 2 }}")  // 8. 聚合操作
        println("Fold: X sum             ${pointList.fold(0.0) { sum, point -> sum + point.x }}")

        println("------------------------------------------------")
    }

    fun testMutableList() {
        println("--------------- MutableList Test ---------------")

        val pointList = mutableListOf(Point2D(1.0, 0.0), Point2D(0.0, 1.0))
        println("PointList:              $pointList")

        pointList.add(0, Point2D(0.0, 0.0))
        pointList.add(Point2D(1.0, 1.0))
        println("After Add:              $pointList")  // 添加元素

        pointList.fill(Point2D(1.0, 1.0))
        pointList[0] = Point2D(2.0, 2.0)
        println("After Set:              $pointList")  // 更新元素

        val reversedList = pointList.asReversed()
        println("Reversed List:          $reversedList")  // 排序
        pointList[0].x = 0.0
        pointList[0].y = 0.0
        println("PointList:              $pointList")
        println("Reversed List:          $reversedList")
        reversedList.removeAt(0)
        println("PointList:              $pointList")
        println("Reversed List:          $reversedList")

        println(pointList.asReversed() == reversedList)

        pointList.removeAt(0)
        pointList.removeAll(setOf(Point2D(1.0, 0.0), Point2D(0.0, 1.0)))
        pointList.clear()
        println("After Remove:           $pointList")  // 删除元素

        println("------------------------------------------------")
    }

    fun testSet() {
        println("------------------- Set Test -------------------")

        val set1 = setOf(Point2D(0.0, 0.0), Point2D(0.0, 1.0))
        val set2 = setOf(Point2D(0.0, 0.0), Point2D(1.0, 0.0))
        println("Union:                  ${set1 union set2}")  // 并集
        println("Intersect:              ${set1 intersect set2}")  // 交集
        println("Subtract:               ${set1 subtract set2}")  // 差集

        println("------------------------------------------------")
    }

    fun testMap() {
        println("------------------- Map Test -------------------")

        val pointMap = mapOf("Point1" to Point3D(1.0, 0.0, 0.0), "Point2" to Point3D(0.0, 1.0, 0.0))
        println("PointMap:               $pointMap")
        println("PointMap'keys Upper:    ${pointMap.mapKeys { it.key.toUpperCase() }}")
        println("PointMap'values Double: ${pointMap.mapValues {
            it.value.x *= 2
            it.value.y *= 2
            it.value.z *= 2
            it.value
        }}")  // 1. 转换 —— 映射
        println("Filter X > 0:           ${pointMap.filter { (_, v) -> v.x > 0 }}")  // 2. 过滤

        println("------------------------------------------------")
    }

    fun testMutableMap() {
        println("--------------- MutableMap Test ----------------")

        val pointMap = mutableMapOf("Point1" to Point2D(0.0, 0.0))
        println("PointMap:               $pointMap")
        pointMap["Point1"] = Point2D(1.0, 0.0)  // 更新
        pointMap["Point2"] = Point2D(0.0, 1.0)  // 添加
        pointMap["Point3"] = Point2D(1.0, 1.0)
        println("After Set And Add:      $pointMap")
        pointMap.remove("Point1", Point2D(0.0, 0.0))  // 删除
        pointMap.remove("Point2")
        pointMap.keys.remove("Point1")
        pointMap.values.remove(Point2D(1.0, 1.0))
        println("After Remove:           $pointMap")

        println("------------------------------------------------")
    }

    println("Collections Test ===============================")
    testList()
    testMutableList()
    testSet()
    testMap()
    testMutableMap()
    println("================================================")
}
