package fr.jcottet.cqrs_event_sourcing.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class EventBusTest{
    @Test
    fun storeEventWhenEventPublished() {
        var eventStream = MemoryEventStream()
        var eventBus = EventBus(eventStream)
        eventBus.publish(PlaneTookOf())

        assertThat(eventStream.myHistory).containsExactly(PlaneTookOf())
    }

    @Test
    fun callEachHandlersWhenPublished() {
        var eventBus = EventBus(MemoryEventStream())
        eventBus.publish(PlaneTookOf())
    }

    @Test
    fun subscribersCalledWhenEventPublished() {
        val takeOfCounter = TakeOfCounter()
        var eventStream = MemoryEventStream()
        var eventBus = EventBus(eventStream)
        eventBus.subscribe(takeOfCounter)
        eventBus.publish(PlaneTookOf())
        eventBus.publish(PlaneTookOf())
        eventBus.publish(PlaneLanded())

        assertThat(takeOfCounter.count).isEqualTo(2)
    }
}
