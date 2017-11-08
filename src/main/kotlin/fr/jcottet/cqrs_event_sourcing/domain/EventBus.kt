package fr.jcottet.cqrs_event_sourcing.domain

interface EventPublisher{
    fun <T:DomainEvent> publish(event:T)
}

class EventBus(val eventStream: EventStream) : EventPublisher {
    val subscribers  = mutableListOf<IEventSubscriber>()

    override fun <T:DomainEvent> publish(event:T){
        eventStream.add(event)
        subscribers.map{it as EventSubscriber<T>}
                .filter{it.getEventClazz().equals(event.javaClass)}
                .forEach { it.handle(event) }
    }

    fun subscribe(evtSubcriber:IEventSubscriber){
        subscribers.add(evtSubcriber)
    }
}

interface IEventSubscriber

interface EventSubscriber<T:DomainEvent> :IEventSubscriber{
    fun  handle(evt:T)
    fun getEventClazz(): Class<T>
}
