class Vector3D(x: Double, y: Double, z: Double) : Point3D(x, y, z) {

    infix fun dot(another: Vector3D): Double {
        return this.x * another.x + this.y * another.y + this.z * another.z
    }

    infix fun cross(another: Vector3D): Vector3D {
        val vx = this.y * another.z - another.y * this.z
        val vy = this.z * another.x - another.z * this.x
        val vz = this.x * another.y - another.x * this.y
        return Vector3D(vx, vy, vz)
    }

    override fun toString(): String {
        return "Vector3D(${this.x}, ${this.y}, ${this.z})"
    }
}
