package fr.sdecout.dddeurope.customer

import fr.sdecout.dddeurope.temporal.HistoricalData
import fr.sdecout.dddeurope.temporal.TemporalValue
import java.time.Instant

data class Customer(
    val firstName: String,
    val lastName: String,
    val address: HistoricalData<String>
) {

    constructor(
        firstName: String, lastName: String, address: String, timestamp: Instant
    ): this(firstName, lastName, HistoricalData(TemporalValue(address, timestamp)))

    fun updateAddress(address: String, timestamp: Instant): Customer = copy(
        address = this.address + TemporalValue(address, timestamp)
    )

}
