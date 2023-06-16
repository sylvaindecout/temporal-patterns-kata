package fr.sdecout.dddeurope.supply

import fr.sdecout.dddeurope.supply.SupplyPlanVersion.Companion.none
import fr.sdecout.dddeurope.temporal.HistoricalData
import fr.sdecout.dddeurope.temporal.TemporalValue
import java.time.*

data class SupplyPlan(
    val title: String,
    val activePeriod: DateRange,
    val versions: HistoricalData<SupplyPlanVersion>
) {

    constructor(
        title: String, activePeriod: DateRange, scheduleDays: Set<DayOfWeek>, demandEntries: DemandEntries,
        timestamp: Instant = Instant.MIN
    ) : this(
        title = title,
        activePeriod = activePeriod,
        versions = HistoricalData(TemporalValue(SupplyPlanVersion(scheduleDays, demandEntries), timestamp))
    )

    fun totalDemandsIn(calculationRange: DateRange): DemandEntries = activePeriod
        .filter { it in calculationRange }
        .fold(emptyMap()) { acc, day ->
            acc + (versions.effectiveValueAt(day)?.takeIf { day.dayOfWeek in it.scheduleDays } ?: none).demandEntries
        }

    fun demandsIn(calculationRange: DateRange): Map<LocalDate, DemandEntries> = activePeriod
        .filter { it in calculationRange }
        .mapNotNull { day -> versions.effectiveValueAt(day)
                ?.takeIf { day.dayOfWeek in it.scheduleDays }
                ?.let { day to it.demandEntries } }
        .toMap()

    fun changePlan(scheduleDays: Set<DayOfWeek>, demandEntries: DemandEntries, timestamp: Instant): SupplyPlan = copy(
        versions = this.versions + TemporalValue(SupplyPlanVersion(scheduleDays, demandEntries), timestamp)
    )

    private fun <T> HistoricalData<T>.effectiveValueAt(localDate: LocalDate): T? = effectiveValueAt(
        localDate.atTime(LocalTime.NOON).atZone(ZoneOffset.UTC).toInstant()
    )
}