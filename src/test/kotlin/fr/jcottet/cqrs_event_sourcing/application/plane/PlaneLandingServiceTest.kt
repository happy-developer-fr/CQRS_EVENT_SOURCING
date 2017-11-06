package fr.jcottet.cqrs_event_sourcing.application.plane

import fr.jcottet.cqrs_event_sourcing.domain.Airport
import fr.jcottet.cqrs_event_sourcing.domain.Plane
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

class PlaneLandingServiceTest{

    val planeId = UUID.randomUUID()
    val plane = Plane(planeId)
    val airport = Airport("Mexico")

    @Test
    fun raiseMessagePlaneLandedWhenPlaneHasLand() {
        var history = mutableListOf<Any>()
        plane.land(history, airport)
        assertThat(history).containsExactly(PlaneLanded("Mexico",planeId))
    }
}

