package fr.jcottet.cqrs_event_sourcing.domain

import fr.jcottet.cqrs_event_sourcing.application.plane.PlaneLanded
import fr.jcottet.cqrs_event_sourcing.application.plane.PlaneTookOf
import java.util.*

class Plane(val id:UUID) {
    fun land(history: MutableList<Any>, toAirport: Airport) {
        history.add(PlaneLanded(toAirport.city, id))
    }

    fun takeOf(history: MutableList<Any>, fromAirport: Airport) {
        history.add(PlaneTookOf(fromAirport.city, id))
    }
}
