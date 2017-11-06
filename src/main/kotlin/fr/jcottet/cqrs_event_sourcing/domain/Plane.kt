package fr.jcottet.cqrs_event_sourcing.domain

import fr.jcottet.cqrs_event_sourcing.application.plane.PlaneLanded
import fr.jcottet.cqrs_event_sourcing.application.plane.PlaneState
import fr.jcottet.cqrs_event_sourcing.application.plane.PlaneState.*
import fr.jcottet.cqrs_event_sourcing.application.plane.PlaneTookOf
import java.util.*

class Plane(val id: UUID) {

    var planState: PlaneState = ON_GROUND

    constructor(history: History, id: UUID) : this(id) {
        history.write = false
        history.forEach {
           when (it) {
                is PlaneLanded -> land(history,it.airport)
                is PlaneTookOf -> takeOf(history, it.airport)
            }
        }
        history.write = true
    }

    fun land(history: History, toAirport: Airport) {
        if (planState == FLIES) {
                history.addEvent(PlaneLanded(toAirport, id))
            planState = ON_GROUND
        }
    }

    fun takeOf(history: History, fromAirport: Airport) {
        if (planState == ON_GROUND) {
                history.addEvent(PlaneTookOf(fromAirport, id))
            planState = FLIES
        }
    }

}
