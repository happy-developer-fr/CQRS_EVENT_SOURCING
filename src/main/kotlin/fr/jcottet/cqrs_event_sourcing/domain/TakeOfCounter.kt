package fr.jcottet.cqrs_event_sourcing.domain

class TakeOfCounter :EventSubscriber<PlaneTookOf>{
    override fun handle(evt: PlaneTookOf) {
        count++
    }


    var count:Int=0
}
