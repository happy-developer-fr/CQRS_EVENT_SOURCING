package fr.jcottet.cqrs_event_sourcing.application.plane

import fr.jcottet.cqrs_event_sourcing.domain.Airport
import fr.jcottet.cqrs_event_sourcing.domain.History
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

    var history = History()

    @Test
    fun raisePlaneLandedWhenPlaneHasLand() {
        plane.takeOf(history, mexicoAirport)
        assertThat(history.copy()).containsExactly(PlaneTookOf(mexicoAirport,planeId))
    }

    @Test
    fun raisePlaneTookOfWhenPlaneHasTookOf() {

        plane.takeOf(history, parisAirport)
        plane.land(history, mexicoAirport)
        assertThat(history.copy()).containsExactly(PlaneTookOf(parisAirport,planeId), PlaneLanded(mexicoAirport,planeId))
    }

    @Test
    fun notRaisedLandedWhenPlaneIsOnTheGround() {
        history.addEvent(PlaneTookOf(parisAirport,planeId))
        history.addEvent(PlaneLanded(mexicoAirport,planeId))

        var plane = Plane(history, planeId)

        plane.land(history,mexicoAirport)

        assertThat(history.copy()).containsExactly(PlaneTookOf(parisAirport,planeId), PlaneLanded(mexicoAirport,planeId))
    }
}

