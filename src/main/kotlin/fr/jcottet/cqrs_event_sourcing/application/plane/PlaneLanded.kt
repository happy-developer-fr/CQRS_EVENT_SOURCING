package fr.jcottet.cqrs_event_sourcing.application.plane

import java.util.*

data class PlaneLanded(val city: String, val planeId: UUID)
