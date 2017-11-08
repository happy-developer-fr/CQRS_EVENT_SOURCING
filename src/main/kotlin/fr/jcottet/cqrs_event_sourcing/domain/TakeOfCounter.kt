package fr.jcottet.cqrs_event_sourcing.domain

class TakeOfCounter :EventSubscriber<PlaneTookOf>{
    override fun getEventClazz(): Class<PlaneTookOf> {
        return PlaneTookOf::class.java
    }

    override fun handle(evt: PlaneTookOf) {
        count++
    }


    var count:Int=0
}
