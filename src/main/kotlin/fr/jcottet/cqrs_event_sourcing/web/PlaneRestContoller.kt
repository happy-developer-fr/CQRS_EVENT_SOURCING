package fr.jcottet.cqrs_event_sourcing.web

import fr.jcottet.cqrs_event_sourcing.domain.Plane
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.*

@RestController
class PlaneRestController {

    @GetMapping("/")
    fun getPlane() : Mono<Plane>{
        return Mono.just(Plane(UUID.randomUUID()))
    }
}
