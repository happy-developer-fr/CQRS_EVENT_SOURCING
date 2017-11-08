package fr.jcottet.cqrs_event_sourcing.domain

import fr.jcottet.cqrs_event_sourcing.domain.Airport.Companion.ø
import fr.jcottet.cqrs_event_sourcing.domain.PlaneState.FLIES
import fr.jcottet.cqrs_event_sourcing.domain.PlaneState.ON_GROUND
import java.util.*

class Plane {

    private var planeProjection:PlaneProjection
    private var eventPublisher: EventPublisher

    constructor(eventPublisher: EventPublisher) {
        planeProjection = PlaneProjection(planeId =UUID.randomUUID())
        this.eventPublisher = eventPublisher
    }

    fun replay(history: List<DomainEvent>): Plane {
        history.forEach {planeProjection = planeProjection.ApplyGeneric(it)}
        return this
    }

    fun recordFlighPlan(flightPlan: FlightPlan){
        publishAndApply(FlightPlanRecorded(flightPlan))
    }

    fun land() {
        if (flying()) {
            publishAndApply(PlaneLanded())
        }
    }

    fun takeOf() {
        if (!flying()) {
            publishAndApply(PlaneTookOf())
        }
    }

    fun  flying(): Boolean = planeProjection.planeState == FLIES

    fun currentAirPort() = planeProjection.currentAirport

    private fun publishAndApply(domainEvent: DomainEvent) {
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
