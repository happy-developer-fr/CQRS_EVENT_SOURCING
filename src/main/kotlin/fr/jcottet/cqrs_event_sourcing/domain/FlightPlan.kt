package fr.jcottet.cqrs_event_sourcing.domain

class FlightPlan (val departureAirport: Airport,val destinationAirport: Airport){

    companion object {
        val ø  = FlightPlan(Airport.ø, Airport.ø)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as FlightPlan

        if (departureAirport != other.departureAirport) return false
        if (destinationAirport != other.destinationAirport) return false

        return true
    }

    override fun hashCode(): Int {
        var result = departureAirport.hashCode()
        result = 31 * result + destinationAirport.hashCode()
        return result
    }


}
