package com.akexorcist.example.feature_blogger.ui.blogger

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.util.*
import kotlinx.android.synthetic.main.view_holder_blogger_post.blogger_text_view_title as textViewTitle
import kotlinx.android.synthetic.main.view_holder_blogger_post.blogger_text_view_updated_date as textViewUpdatedDate

class PostViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    fun setTitle(title: String?) {
        title?.let {
            textViewTitle.text = it
        } ?: run {
            textViewTitle.text = ""
        }
    }

    fun setUpdatedDate(date: String?) {
        date?.let {
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
            textViewUpdatedDate.text = formatter.format(OffsetDateTime.parse(it))
        } ?: run {
            textViewUpdatedDate.text = ""
        }
    }

    fun setOnClickListener(listener: () -> Unit) {
        itemView.setOnClickListener { listener() }
    }
}