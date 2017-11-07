package fr.jcottet.cqrs_event_sourcing.domain

class Airport(val city: String?){

    companion object {
        val ø  = Airport(null)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Airport

        if (city != other.city) return false

        return true
    }

    override fun hashCode(): Int {
        return city?.hashCode() ?: 0
    }


}
