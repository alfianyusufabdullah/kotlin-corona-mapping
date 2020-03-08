package alfianyusufabdullah.corona.data.entity

data class Infected(var mainData: DataResponse?,var locations: Array<Location>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Infected

        if (mainData != other.mainData) return false
        if (!locations.contentEquals(other.locations)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mainData?.hashCode() ?: 0
        result = 31 * result + locations.contentHashCode()
        return result
    }
}