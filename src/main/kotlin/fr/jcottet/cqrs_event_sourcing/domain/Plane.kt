package fr.jcottet.cqrs_event_sourcing.domain

import fr.jcottet.cqrs_event_sourcing.domain.Airport.Companion.ø
import fr.jcottet.cqrs_event_sourcing.domain.PlaneState.FLIES
import fr.jcottet.cqrs_event_sourcing.domain.PlaneState.ON_GROUND
import java.util.*

class Plane {

    private var planeProjection:PlaneProjection

    constructor() {
        planeProjection = PlaneProjection(planeId =UUID.randomUUID())
    }

    fun play(eventStream: EventStream): Plane {
        eventStream.forEach {planeProjection = planeProjection.ApplyGeneric(it)}
        return this
    }

    fun recordFlighPlan(eventPublisher: EventPublisher, flightPlan: FlightPlan){
        publishAndApply(eventPublisher, FlightPlanRecorded(flightPlan))
    }

    fun land(eventPublisher: EventPublisher) {
        if (flying()) {
            publishAndApply(eventPublisher, PlaneLanded())
        }
    }

    fun takeOf(eventPublisher: EventPublisher) {
        if (!flying()) {
            publishAndApply(eventPublisher, PlaneTookOf())
        }
    }

    fun  flying(): Boolean = planeProjection.planeState == FLIES

    fun currentAirPort() = planeProjection.currentAirport

    private fun publishAndApply(eventPublisher: EventPublisher, domainEvent: DomainEvent) {
        eventPublisher.publish(domainEvent)
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
