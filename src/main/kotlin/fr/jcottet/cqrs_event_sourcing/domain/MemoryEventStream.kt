package fr.jcottet.cqrs_event_sourcing.domain

class MemoryEventStream : EventStream{

    val myHistory: MutableList<DomainEvent> = mutableListOf()

    override fun add(event:DomainEvent){
            myHistory.add(event)
    }

    override fun forEach(action: (DomainEvent) -> Unit): Unit{
        return myHistory.forEach(action)
    }

    override fun history(): List<DomainEvent>{
        return myHistory.toList()
    }
}
