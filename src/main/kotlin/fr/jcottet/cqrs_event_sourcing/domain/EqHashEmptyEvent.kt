package fr.jcottet.cqrs_event_sourcing.domain

open class EqHashEmptyEvent {
    override fun equals(other: Any?) = true
    override fun hashCode() = 0
}
