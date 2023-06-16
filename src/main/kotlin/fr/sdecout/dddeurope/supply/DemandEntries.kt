package fr.sdecout.dddeurope.supply

import fr.sdecout.dddeurope.supply.Quantity.Companion.zero

typealias DemandEntries = Map<String, Quantity>

operator fun DemandEntries.plus(other: DemandEntries): DemandEntries = (this.keys + other.keys)
    .associateWith { key -> (this[key] ?: zero) + (other[key] ?: zero) }