package fr.jcottet.cqrs_event_sourcing

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class CqrsEventSourcingApplication

fun main(args: Array<String>) {
    SpringApplication.run(CqrsEventSourcingApplication::class.java, *args)
}
