package fr.sdecout.dddeurope.supply

@JvmInline
value class Quantity(val value: Int) {

    companion object {
        val zero = Quantity(0)
    }

    operator fun plus(other: Quantity) = Quantity(this.value + other.value)

}