package fr.sdecout.dddeurope.supply

import java.time.DayOfWeek

data class SupplyPlanVersion(
    val scheduleDays: Set<DayOfWeek>,
    val demandEntries: DemandEntries
) {
    companion object {
        val none = SupplyPlanVersion(emptySet(), emptyMap())
    }
}