package fr.sdecout.dddeurope.temporal

import java.time.Instant

class HistoricalData<T>(
    private val items: List<TemporalValue<T>>
) {

    constructor(singleItem: TemporalValue<T>) : this(listOf(singleItem))

    val value: T by lazy { items.maxBy { it.effectiveSince }.value }

    fun effectiveValueAt(instant: Instant): T? = items
        .filter { it.effectiveSince <= instant }
        .maxByOrNull { it.effectiveSince }?.value

    operator fun plus(temporalValue: TemporalValue<T>): HistoricalData<T> = HistoricalData(items + temporalValue)

}