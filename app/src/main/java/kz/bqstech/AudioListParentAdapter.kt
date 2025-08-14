package kz.bqstech

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.bqstech.whisperJournal.AudiosListChildAdapter
import kz.bqstech.whisperJournal.JournalItem
import kz.bqstech.whisperJournal.databinding.AudioListItemDateBinding

class AudioListParentAdapter(
    private val data: Map<String, List<JournalItem>>
) : RecyclerView.Adapter<AudioListParentAdapter.ParentViewHolder>() {

    private val keys = data.keys.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding = AudioListItemDateBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ParentViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val key = keys[position]
        holder.bind(key, data[key] ?: emptyList())
    }

    override fun getItemCount() = keys.size

    inner class ParentViewHolder(
        val binding: AudioListItemDateBinding,
        //TODO: is this safe?
        val context: Context? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(headerDate: String, items: List<JournalItem>) {

            binding.listDateText.text = headerDate

            val childRecycler = binding.recyclerInParentAdapter
            childRecycler.adapter = AudiosListChildAdapter(items)
            childRecycler.layoutManager = LinearLayoutManager(context)
        }

    }
}

//class JournalListDateText(
//    text: String,
//    //margin
//    context: Context,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0
//) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr)
//{
//
//    init {
//        id = R.id.text
//
//        textSize = 22f
//
//        val onBackgroundColor = getThemeColor(context, MaterialR.attr.colorOnBackground)
//        setTextColor(onBackgroundColor)
//        if (attrs != null) {
//            val typedArray =
//                context.obtainStyledAttributes(attrs, intArrayOf(android.R.attr.background))
//            val backgroundDrawable = typedArray.getDrawable(0)
//            background = backgroundDrawable // apply it
//            typedArray.recycle()
//
//            val fontId = typedArray.getResourceId(0, -1)
//            if (fontId != -1) {
//                typeface = ResourcesCompat.getFont(context, fontId)
//            }
//            typedArray.recycle()
//        }
//    }
//
//    post
//    {
//        val params = layoutParams
//        if (params is ViewGroup.MarginLayoutParams) {
//            val marginInPx = (8 * resources.displayMetrics.density).toInt()
//            params.setMargins(marginInPx, marginInPx, marginInPx, marginInPx)
//            layoutParams = params
//        }
//    }
//}


private fun getThemeColor(context: Context, attrResId: Int): Int {

    val typedValue = TypedValue()
    val theme = context.theme
    theme.resolveAttribute(attrResId, typedValue, true)
    return typedValue.data
}

