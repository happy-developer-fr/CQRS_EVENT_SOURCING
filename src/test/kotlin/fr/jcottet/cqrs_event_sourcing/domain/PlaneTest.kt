package fr.jcottet.cqrs_event_sourcing.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PlaneTest {

    val paris = "Paris"
    val mexico = "Mexico"

    val mexicoAirport = Airport(mexico)
    val parisAirport = Airport(paris)

    var eventStream = MemoryEventStream()
    var eventBus = EventBus(eventStream)
    val parisToMexico = FlightPlan(parisAirport, mexicoAirport)

    @Test
    fun raisePlaneLandedWhenPlaneHasLand() {
        var plane = Plane(eventBus)
        plane.recordFlighPlan(parisToMexico)
        plane.takeOf()
        assertThat(eventStream.history()).containsExactly(FlightPlanRecorded(parisToMexico), PlaneTookOf())
    }

    @Test
    fun raisePlaneTookOfWhenPlaneHasTookOf() {
        var plane = Plane(eventBus)
        plane.recordFlighPlan(parisToMexico)
        plane.takeOf()
        plane.land()
        assertThat(eventStream.history()).containsExactly(FlightPlanRecorded(parisToMexico), PlaneTookOf(), PlaneLanded())
    }

    @Test
    fun notRaisedLandedWhenPlaneLandTwice() {
        eventStream.add(FlightPlanRecorded(parisToMexico))
        eventStream.add(PlaneTookOf())
        eventStream.add(PlaneLanded())

        var plane = Plane(eventBus).replay(eventStream.history())

        plane.land()

        assertThat(eventStream.history()).containsExactly(FlightPlanRecorded(parisToMexico),PlaneTookOf(), PlaneLanded())
    }

    @Test
    fun planeAirportIsLandedAirport() {
        eventStream.add(FlightPlanRecorded(parisToMexico))
        eventStream.add(PlaneTookOf())
        eventStream.add(PlaneLanded())

        var plane = Plane(eventBus).replay(eventStream.history())
        assertThat(plane.currentAirPort()).isEqualTo(mexicoAirport)
    }
}

