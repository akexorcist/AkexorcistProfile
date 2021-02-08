package com.akexorcist.example.feature_stackoverflow.ui.stackoverflow

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.example.feature_stackoverflow.R
import com.akexorcist.example.feature_stackoverflow.constant.TimelineType
import com.akexorcist.example.feature_stackoverflow.databinding.ViewHolderStackoverflowActivityBinding
import kotlinx.android.extensions.LayoutContainer
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.util.*

class ActivityViewHolder(
    private val binding: ViewHolderStackoverflowActivityBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun setType(type: String?) {
        type?.let {
            binding.stackoverflowTextViewType.text = getType(it)
        } ?: run {
            binding.stackoverflowTextViewType.text = ""
        }
    }

    fun setTitle(title: String?) {
        title?.let {
            binding.stackoverflowTextViewTitle.text = it
        } ?: run {
            binding.stackoverflowTextViewTitle.text = ""
        }
    }

    fun setDate(date: Long) {
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
            .withLocale(Locale.getDefault())
            .withZone(ZoneId.systemDefault())
        binding.stackoverflowTextViewDate.text = formatter.format(Instant.ofEpochSecond(date))
    }

    private fun getType(type: String): String = when (type) {
        TimelineType.ANSWERED -> itemView.resources.getString(R.string.timeline_type_answered)
        TimelineType.BADGE -> itemView.resources.getString(R.string.timeline_type_badge)
        TimelineType.COMMENTED -> itemView.resources.getString(R.string.timeline_type_commented)
        TimelineType.REVISION -> itemView.resources.getString(R.string.timeline_type_revision)
        else -> "-"
    }
}
