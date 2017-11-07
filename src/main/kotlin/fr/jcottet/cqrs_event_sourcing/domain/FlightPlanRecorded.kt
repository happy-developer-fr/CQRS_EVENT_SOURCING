package fr.jcottet.cqrs_event_sourcing.domain

data class FlightPlanRecorded(val flightPlan: FlightPlan) : DomainEvent
