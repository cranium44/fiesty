package live.adabe.fiesty.util

import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import java.io.IOException

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }
    return this
}

fun updateTime(hours: Int, mins: Int): String {
    var hoursFinal = hours
    val timeSet: String
    when {
        hours > 12 -> {
            hoursFinal -= 12
            timeSet = "PM"
        }
        hours == 0 -> {
            hoursFinal += 12
            timeSet = "AM"
        }
        hours == 12 -> timeSet = "PM"
        else -> timeSet = "AM"
    }
    val minutes: String = if (mins < 10) "0$mins" else mins.toString()

    // Append in a StringBuilder
    return StringBuilder().append(hoursFinal).append(':')
        .append(minutes).append(" ").append(timeSet).toString()
}

suspend fun <T> retryIO(
    times: Int = Int.MAX_VALUE,
    initialDelay: Long = 100, // 0.1 second
    maxDelay: Long = 1000,    // 1 second
    factor: Double = 2.0,
    block: suspend () -> T): T
{
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            return block()
        } catch (e: IOException) {
            // you can log an error here and/or make a more finer-grained
            // analysis of the cause to see if retry is needed
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
    }
    return block() // last attempt
}