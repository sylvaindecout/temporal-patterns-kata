package fr.sdecout.dddeurope.supply

import java.time.DayOfWeek
import java.time.LocalDate

data class SupplyPlan(
    val title: String,
    val activePeriod: DateRange,
    val scheduleDays: Set<DayOfWeek>,
    val demandEntries: DemandEntries
) {

    fun totalDemandsIn(calculationRange: DateRange): DemandEntries = activePeriod
        .filter { it in calculationRange }
        .filter { it.dayOfWeek in scheduleDays }
        .fold(emptyMap()) { acc, _ -> acc + demandEntries }

    fun demandsIn(calculationRange: DateRange): Map<LocalDate, DemandEntries> = activePeriod
        .filter { it in calculationRange }
        .filter { it.dayOfWeek in scheduleDays }
        .associateWith { demandEntries }

}