package fr.jcottet.cqrs_event_sourcing.application.plane

import fr.jcottet.cqrs_event_sourcing.domain.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PlaneLandingServiceTest{

    val paris = "Paris"
    val mexico = "Mexico"

    val mexicoAirport = Airport(mexico)
    val parisAirport = Airport(paris)

    var history = MemoryEventStream()
    var eventBus = EventBus(history)
    val parisToMexico = FlightPlan(parisAirport, mexicoAirport)

    @Test
    fun raisePlaneLandedWhenPlaneHasLand() {
        var plane = Plane()
        plane.recordFlighPlan(eventBus, parisToMexico)
        plane.takeOf(eventBus)
        assertThat(history.events()).containsExactly(FlightPlanRecorded(parisToMexico), PlaneTookOf())
    }

    @Test
    fun raisePlaneTookOfWhenPlaneHasTookOf() {
        var plane = Plane()
        plane.recordFlighPlan(eventBus, parisToMexico)
        plane.takeOf(eventBus)
        plane.land(eventBus)
        assertThat(history.events()).containsExactly(FlightPlanRecorded(parisToMexico), PlaneTookOf(), PlaneLanded())
    }

    @Test
    fun notRaisedLandedWhenPlaneLandTwice() {
        history.add(FlightPlanRecorded(parisToMexico))
        history.add(PlaneTookOf())
        history.add(PlaneLanded())

        var plane = Plane().play(history)

        plane.land(eventBus)

        assertThat(history.events()).containsExactly(FlightPlanRecorded(parisToMexico),PlaneTookOf(), PlaneLanded())
    }

    @Test
    fun planeAirportIsLandedAirport() {
        history.add(FlightPlanRecorded(parisToMexico))
        history.add(PlaneTookOf())
        history.add(PlaneLanded())

        var plane = Plane().play(history)
        assertThat(plane.currentAirPort()).isEqualTo(mexicoAirport)
    }
}

