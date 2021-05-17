package live.adabe.fiesty.util

import androidx.recyclerview.widget.RecyclerView

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