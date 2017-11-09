package fr.jcottet.cqrs_event_sourcing.domain

interface EventPublisher {
    fun <T : DomainEvent> publish(event: T)
}

class EventBus(val eventStream: EventStream) : EventPublisher {
    private var subscribers = mutableMapOf<Class<*>, MutableList<EventSubscriber<out DomainEvent>>>()

    override fun <T : DomainEvent> publish(event: T) {
        eventStream.add(event)
        getListener(event.javaClass)?.forEach { it.handle(event) }
    }

    inline fun <reified T : DomainEvent> subscribe(evtSubcriber: EventSubscriber<T>) =
            subscribe(T::class.java, evtSubcriber)

    fun <T : DomainEvent> subscribe(type: Class<T>, evtSubcriber: EventSubscriber<T>) {
        subscribers.computeIfAbsent(type) { mutableListOf() }.add(evtSubcriber)

    }

    private fun <T : DomainEvent> getListener(type: Class<T>) = subscribers[type] as List<EventSubscriber<T>>?
}

interface EventSubscriber<T : DomainEvent> {
    fun handle(evt: T)
}
