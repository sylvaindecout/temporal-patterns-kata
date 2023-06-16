package fr.sdecout.dddeurope.temporal

import java.time.Instant

data class TemporalValue<T>(
    val value: T,
    val effectiveSince: Instant
)