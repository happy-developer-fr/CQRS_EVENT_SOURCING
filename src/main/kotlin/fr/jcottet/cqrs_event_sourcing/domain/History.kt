package fr.jcottet.cqrs_event_sourcing.domain

class History{
    var write = true

    val myHistory: MutableList<Any> = mutableListOf()

    fun addEvent(event:Any){
        if(write){
            myHistory.add(event)
        }
    }

    fun forEach(action: (Any) -> Unit): Unit{
        return myHistory.forEach(action)
    }

    fun copy(): List<Any>{
        return myHistory.toList()
    }
}
