package fr.sdecout.dddeurope.supply

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.time.DayOfWeek.*
import java.time.LocalDate

class SupplyPlanningTest : ShouldSpec({

    context("Testing the demand calculation") {

        should("calculate total demands in a period") {
            val plan = SupplyPlan(
                title = "Weekly Fruit Supply",
                activePeriod = LocalDate.parse("2023-01-01")..LocalDate.parse("2023-12-31"),
                scheduleDays = setOf(MONDAY, WEDNESDAY, FRIDAY),
                demandEntries = mapOf(
                    "Apple" to Quantity(50),
                    "Banana" to Quantity(30),
                    "Orange" to Quantity(20)
                )
            )

            val demands = plan.totalDemandsIn(LocalDate.parse("2023-01-15")..LocalDate.parse("2023-01-31"))

            demands shouldBe mapOf(
                "Apple" to Quantity(350),
                "Banana" to Quantity(210),
                "Orange" to Quantity(140)
            )
        }

        should("calculate demands of days in a period") {
            val dailyDemands = mapOf(
                "Apple" to Quantity(50),
                "Banana" to Quantity(30),
                "Orange" to Quantity(20)
            )
            val plan = SupplyPlan(
                title = "Weekly Fruit Supply",
                activePeriod = LocalDate.parse("2023-01-01")..LocalDate.parse("2023-12-31"),
                scheduleDays = setOf(MONDAY, WEDNESDAY, FRIDAY),
                demandEntries = dailyDemands
            )

            val demands = plan.demandsIn(LocalDate.parse("2023-01-15")..LocalDate.parse("2023-01-25"))

            demands shouldBe mapOf(
                LocalDate.parse("2023-01-16") to dailyDemands,
                LocalDate.parse("2023-01-18") to dailyDemands,
                LocalDate.parse("2023-01-20") to dailyDemands,
                LocalDate.parse("2023-01-23") to dailyDemands,
                LocalDate.parse("2023-01-25") to dailyDemands
            )
        }
    }

})