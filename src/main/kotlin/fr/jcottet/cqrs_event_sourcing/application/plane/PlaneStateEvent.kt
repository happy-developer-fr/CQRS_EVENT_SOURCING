package fr.jcottet.cqrs_event_sourcing.application.plane

import fr.jcottet.cqrs_event_sourcing.domain.Airport
import java.util.*

sealed class PlaneStateEvent

data class PlaneLanded(val airport: Airport, val planeId: UUID) : PlaneStateEvent()
data class PlaneTookOf(val airport: Airport, val planeId: UUID) : PlaneStateEvent()
