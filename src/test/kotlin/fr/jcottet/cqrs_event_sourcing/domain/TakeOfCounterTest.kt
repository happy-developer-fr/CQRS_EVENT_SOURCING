package fr.jcottet.cqrs_event_sourcing.domain

import org.assertj.core.api.Assertions.*
import org.junit.Test
import java.util.stream.IntStream

class TakeOfCounterTest {

    @Test
    fun whenTookOfThenIncrement() {
        IntStream.rangeClosed(1, 7).forEach {
            var takeOfCounter = TakeOfCounter()
            for (i in 1..it) {
                takeOfCounter.handle(PlaneTookOf())
            }
            assertThat(takeOfCounter.count).isEqualTo(it)
        }
    }
}
