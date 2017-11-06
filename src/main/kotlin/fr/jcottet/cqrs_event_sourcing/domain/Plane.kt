package fr.jcottet.cqrs_event_sourcing.domain

import fr.jcottet.cqrs_event_sourcing.application.plane.PlaneLanded
import java.util.*

class Plane(val id:UUID) {
    fun land(history: MutableList<Any>, airport: Airport) {
        history.add(PlaneLanded(airport.city, id))
    }
}
