package fr.jcottet.cqrs_event_sourcing.application.plane

import java.util.*

data class PlaneTookOf(val fromCity: String, val planeId: UUID)
