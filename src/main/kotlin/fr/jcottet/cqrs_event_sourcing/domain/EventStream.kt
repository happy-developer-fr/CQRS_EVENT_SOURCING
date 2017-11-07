package fr.jcottet.cqrs_event_sourcing.domain

interface EventStream {

    fun add(event:DomainEvent)

    fun forEach(action: (DomainEvent) -> Unit): Unit

    fun events(): List<DomainEvent>
}
