package fr.sdecout.dddeurope.supply

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.time.DayOfWeek.*
import java.time.Instant
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

    context("Testing the demand calculation with changes during time") {

        should("calculate total demands of period") {
            val formerPlan = SupplyPlan(
                timestamp = Instant.parse("2023-01-01T10:00:00Z"),
                title = "Weekly Fruit Supply",
                activePeriod = LocalDate.parse("2023-01-01")..LocalDate.parse("2023-12-31"),
                scheduleDays = setOf(MONDAY, WEDNESDAY, FRIDAY),
                demandEntries = mapOf(
                    "Apple" to Quantity(50),
                    "Banana" to Quantity(30)
                )
            )
            val updatedPlan = formerPlan.changePlan(
                scheduleDays = setOf(TUESDAY, THURSDAY),
                demandEntries = mapOf(
                    "Apple" to Quantity(30),
                    "Orange" to Quantity(40)
                ),
                timestamp = Instant.parse("2023-02-01T10:00:00Z")
            )

            val demands = updatedPlan.totalDemandsIn(LocalDate.parse("2023-01-20")..LocalDate.parse("2023-02-10"))

            demands shouldBe mapOf(
                "Apple" to Quantity(340),
                "Banana" to Quantity(150),
                "Orange" to Quantity(120)
            )
        }

        should("calculate demands of days in a period") {
            val dailyDemands = mapOf(
                "Apple" to Quantity(50),
                "Banana" to Quantity(30)
            )
            val formerPlan = SupplyPlan(
                timestamp = Instant.parse("2023-01-01T10:00:00Z"),
                title = "Weekly Fruit Supply",
                activePeriod = LocalDate.parse("2023-01-01")..LocalDate.parse("2023-12-31"),
                scheduleDays = setOf(MONDAY, WEDNESDAY, FRIDAY),
                demandEntries = dailyDemands
            )
            val newDailyDemands = mapOf(
                "Orange" to Quantity(4)
            )
            val updatedPlan = formerPlan.changePlan(
                scheduleDays = setOf(TUESDAY, THURSDAY),
                demandEntries = newDailyDemands,
                timestamp = Instant.parse("2023-02-01T10:00:00Z")
            )

            val demands = updatedPlan.demandsIn(LocalDate.parse("2023-01-20")..LocalDate.parse("2023-02-10"))

            demands shouldBe mapOf(
                LocalDate.parse("2023-01-20") to dailyDemands,
                LocalDate.parse("2023-01-23") to dailyDemands,
                LocalDate.parse("2023-01-25") to dailyDemands,
                LocalDate.parse("2023-01-27") to dailyDemands,
                LocalDate.parse("2023-01-30") to dailyDemands,
                LocalDate.parse("2023-02-02") to newDailyDemands,
                LocalDate.parse("2023-02-07") to newDailyDemands,
                LocalDate.parse("2023-02-09") to newDailyDemands
            )
        }
    }

})