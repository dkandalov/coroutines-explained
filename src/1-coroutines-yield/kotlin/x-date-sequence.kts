package kotlin
import java.time.LocalDate
import kotlin.coroutines.experimental.buildSequence




data class DateRange(val from: LocalDate, val to: LocalDate) {

    fun dayIterator(): Sequence<LocalDate> = buildSequence {
        var current = from
        while (current <= to) {
            yield(current)
            current = current.plusDays(1)
        }
    }

    // More code...
}


