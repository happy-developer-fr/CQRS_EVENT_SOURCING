package fr.jcottet.cqrs_event_sourcing.application.plane

import fr.jcottet.cqrs_event_sourcing.domain.Airport
import fr.jcottet.cqrs_event_sourcing.domain.Plane
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

class PlaneLandingServiceTest{

    val planeId = UUID.randomUUID()
    val plane = Plane(planeId)

    val paris = "Paris"
    val mexico = "Mexico"

    val mexicoAirport = Airport(mexico)
    val parisAirport = Airport(paris)

    @Test
    fun raisePlaneLandedWhenPlaneHasLand() {
        var history = mutableListOf<Any>()
        plane.takeOf(history, mexicoAirport)
        assertThat(history).containsExactly(PlaneTookOf(mexico,planeId))
    }

    @Test
    fun raisePlaneTookOfWhenPlaneHasTookOf() {
        var history = mutableListOf<Any>()
        plane.takeOf(history, parisAirport)
        plane.land(history, mexicoAirport)
        assertThat(history).containsExactly(PlaneTookOf(paris,planeId), PlaneLanded(mexico,planeId))
    }
}

