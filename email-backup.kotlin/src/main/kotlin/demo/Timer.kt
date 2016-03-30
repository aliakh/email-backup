package demo

import java.util.Date

public class Timer(private val name: String) {

    private val start: Long

    init {
        this.start = Date().time
        println(name + " started")
    }

    public fun finish() {
        println(name + " finished in " + seconds() + " second(s)")
    }

    private fun seconds(): Int {
        return ((Date().time - start).toInt()) / 1000
    }
}
