package fr.jcottet.cqrs_event_sourcing.domain

import fr.jcottet.cqrs_event_sourcing.domain.Airport.Companion.ø
import fr.jcottet.cqrs_event_sourcing.domain.PlaneState.*
import java.util.*

class Plane {

    private var planeProjection:PlaneProjection

    constructor(eventStream: EventStream) {
        planeProjection = PlaneProjection(planeId =UUID.randomUUID())
        eventStream.forEach {planeProjection = planeProjection.ApplyGeneric(it)}
    }

    fun recordFlighPlan(eventStream: EventStream, flightPlan: FlightPlan){
        publishAndApply(eventStream, FlightPlanRecorded(flightPlan))
    }

    fun land(eventStream: EventStream) {
        if (flying()) {
            publishAndApply(eventStream, PlaneLanded())
        }
    }

    fun takeOf(eventStream: EventStream) {
        if (!flying()) {
            publishAndApply(eventStream, PlaneTookOf())
        }
    }

    fun  flying(): Boolean = planeProjection.planeState == FLIES

    fun currentAirPort() = planeProjection.currentAirport

    private fun publishAndApply(eventStream: EventStream, domainEvent: DomainEvent) {
        eventStream.add(domainEvent)
        planeProjection = planeProjection.ApplyGeneric(domainEvent)
    }
}

class PlaneProjection(val planeState: PlaneState = ON_GROUND, val currentAirport: Airport = ø, val flightPlan: FlightPlan = FlightPlan.ø, val planeId:UUID){
    fun ApplyGeneric(evt:DomainEvent) : PlaneProjection =
        when (evt) {
            is PlaneLanded -> Apply(evt)
            is PlaneTookOf -> Apply(evt)
            is FlightPlanRecorded -> Apply(evt)
            else -> this
        }

    fun Apply(evt : PlaneLanded) = PlaneProjection(ON_GROUND, this.flightPlan.destinationAirport, FlightPlan.ø, planeId)

    fun Apply(evt : PlaneTookOf) = PlaneProjection(FLIES, ø, this.flightPlan, planeId)

    fun Apply(evt : FlightPlanRecorded) = PlaneProjection(planeState, currentAirport, evt.flightPlan, planeId)
}
