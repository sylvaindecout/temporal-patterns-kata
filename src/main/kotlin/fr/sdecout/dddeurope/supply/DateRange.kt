package fr.sdecout.dddeurope.supply

import java.time.LocalDate

data class DateRange(
    val start: LocalDate,
    val end: LocalDate
): Iterable<LocalDate> {

    override fun iterator(): Iterator<LocalDate> = generateSequence (start) { it.plusDays(1) }
        .takeWhile { it <= end }
        .iterator()

}

operator fun LocalDate.rangeTo(until: LocalDate): DateRange = DateRange(this, until)