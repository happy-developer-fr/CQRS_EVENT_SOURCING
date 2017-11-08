package fr.jcottet.cqrs_event_sourcing.domain

class TakeOfCounter :EventSubscriber<PlaneTookOf>{
    override fun getEventClazz(): Class<PlaneTookOf> {
        return PlaneTookOf::class.java //To change body of created functions use File | Settings | File Templates.
    }

    override fun handle(evt: PlaneTookOf) {
        count++
    }


    var count:Int=0
}
