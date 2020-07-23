import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.math.pow

open class Point3D {
    // 伴生对象，类似Java的静态成员与静态方法
    companion object {
        const val EUCLIDEAN = 1    // 欧氏距离
        const val MANHATTAN = 2    // 曼哈顿距离

        @JvmStatic  // Java互操作
        fun getCoordinateInfo() {
            println("Using 3D Cartesian Coordinate System")
        }
    }

    var x = 0.0
    var y = 0.0
    var z = 0.0

    // 构造函数重载
    constructor()
    constructor(x: Double, y: Double, z: Double) {
        this.x = x
        this.y = y
        this.z = z
    }

    // Getter与Setter
    var coordinateInfo: String = "3D Cartesian Coordinate System"
        get() = field
        set(value) {
            println("Coordinate System Changed")
            field = value
        }

    // Getter
    val distanceToZero: Double
        get() {
            val a = this.x.pow(2)
            val b = this.y.pow(2)
            val c = this.z.pow(2)
            val distanceToZero = sqrt(a + b + c)

            return String.format("%.2f", distanceToZero).toDouble()
        }

    fun getDistance(another: Point3D, type: Int): Double {
        val distance = when (type) {
            EUCLIDEAN -> {
                val a = (this.x - another.x).pow(2)
                val b = (this.y - another.y).pow(2)
                val c = (this.z - another.z).pow(2)
                sqrt(a + b + c)
            }
            MANHATTAN -> {
                val a = abs(this.x - another.x)
                val b = abs(this.y - another.y)
                val c = abs(this.z - another.z)
                a + b + c
            }
            else -> 0.0
        }

        return String.format("%.2f", distance).toDouble()
    }

    fun toVector3D(): Vector3D {
        return Vector3D(this.x, this.y, this.z)
    }

    override fun toString(): String {
        return "Point3D(${this.x}, ${this.y}, ${this.z})"
    }
}
