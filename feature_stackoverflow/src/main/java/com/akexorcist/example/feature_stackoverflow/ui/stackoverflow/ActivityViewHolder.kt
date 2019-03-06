package com.akexorcist.example.feature_stackoverflow.ui.stackoverflow

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.example.feature_stackoverflow.R
import com.akexorcist.example.feature_stackoverflow.constant.TimelineType
import kotlinx.android.extensions.LayoutContainer
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.util.*
import kotlinx.android.synthetic.main.view_holder_stackoverflow_activity.stackoverflow_text_view_date as textViewDate
import kotlinx.android.synthetic.main.view_holder_stackoverflow_activity.stackoverflow_text_view_title as textViewTitle
import kotlinx.android.synthetic.main.view_holder_stackoverflow_activity.stackoverflow_text_view_type as textViewType

class ActivityViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    fun setType(type: String?) {
        type?.let {
            textViewType.text = getType(it)
        } ?: run {
            textViewType.text = ""
        }
    }

    fun setTitle(title: String?) {
        title?.let {
            textViewTitle.text = it
        } ?: run {
            textViewTitle.text = ""
        }
    }

    fun setDate(date: Long) {
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
            .withLocale(Locale.getDefault())
            .withZone(ZoneId.systemDefault())
        textViewDate.text = formatter.format(Instant.ofEpochSecond(date))
    }

    private fun getType(type: String): String = when (type) {
        TimelineType.ANSWERED -> itemView.resources.getString(R.string.timeline_type_answered)
        TimelineType.BADGE -> itemView.resources.getString(R.string.timeline_type_badge)
        TimelineType.COMMENTED -> itemView.resources.getString(R.string.timeline_type_commented)
        TimelineType.REVISION -> itemView.resources.getString(R.string.timeline_type_revision)
        else -> "-"
    }
}