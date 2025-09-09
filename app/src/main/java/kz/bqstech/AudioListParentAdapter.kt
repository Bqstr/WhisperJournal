package kz.bqstech

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.bqstech.whisperJournal.ui.audioList.AudiosListChildAdapter
import kz.bqstech.whisperJournal.ui.audioList.JournalUiItem
import kz.bqstech.whisperJournal.databinding.AudioListItemDateBinding
class AudioListParentAdapter(
    private var data: Map<String, List<JournalUiItem>>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<AudioListParentAdapter.ParentViewHolder>() {

    private var keys: List<String> = data.keys.toList()

    fun updateList(newMap: Map<String, List<JournalUiItem>>) {
        data = newMap
        keys = newMap.keys.toList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding = AudioListItemDateBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ParentViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val key = keys[position]
        holder.bind(key, data[key] ?: emptyList())
    }

    override fun getItemCount() = keys.size

    inner class ParentViewHolder(
        private val binding: AudioListItemDateBinding,
        private val context: Context? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(headerDate: String, items: List<JournalUiItem>) {
            binding.listDateText.text = headerDate

            val childRecycler = binding.recyclerInParentAdapter
            childRecycler.layoutManager = LinearLayoutManager(context)
            childRecycler.adapter = AudiosListChildAdapter(items) {
                onItemClick.invoke(it)
            }
        }
    }
}
