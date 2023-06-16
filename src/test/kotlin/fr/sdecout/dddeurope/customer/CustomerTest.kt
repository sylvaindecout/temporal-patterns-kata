package fr.sdecout.dddeurope.customer

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.time.Instant
import java.time.temporal.ChronoUnit.DAYS

class CustomerTest : ShouldSpec({

    context("Testing history of address for customer") {

        val timestamp1 = Instant.parse("2023-01-01T10:00:00Z")
        val timestamp2 = timestamp1.plus(1, DAYS)
        val timestamp3 = timestamp2.plus(1, DAYS)

        should("introduce a customer with an address") {
            val customer = Customer("Olivia", "Johnson", "123 Maple Street", timestamp1)

            customer.address.value shouldBe "123 Maple Street"
        }

        should("get the current address after changing the address") {
            val formerCustomer = Customer("Olivia", "Johnson", "123 Maple Street", timestamp1)

            val updatedCustomer = formerCustomer.updateAddress("456 Oak Avenue", timestamp2)

            updatedCustomer.address.value shouldBe "456 Oak Avenue"
        }

        should("get an address at a specific time before last update") {
            val formerCustomer = Customer("Olivia", "Johnson", "123 Maple Street", timestamp1)

            val updatedCustomer = formerCustomer.updateAddress("456 Oak Avenue", timestamp3)

            updatedCustomer.address.effectiveValueAt(timestamp2) shouldBe "123 Maple Street"
        }

        should("get an address at a specific time after last update") {
            val formerCustomer = Customer("Olivia", "Johnson", "123 Maple Street", timestamp1)

            val updatedCustomer = formerCustomer.updateAddress("456 Oak Avenue", timestamp2)

            updatedCustomer.address.effectiveValueAt(timestamp3) shouldBe "456 Oak Avenue"
        }
    }

})