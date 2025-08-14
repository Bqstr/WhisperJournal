package kz.bqstech.whisperJournal

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.bqstech.whisperJournal.databinding.AudioListItemBinding

class AudiosAdapterViewHolder(
    binding: AudioListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    val name = binding.textTitle
    val time = binding.textTime
    val duration = binding.textDuration
    val statusDot = binding.statusDot
}

class AudiosListChildAdapter(
    val list: List<JournalItem>
) : RecyclerView.Adapter<AudiosAdapterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AudiosAdapterViewHolder {
        val binding =AudioListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AudiosAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AudiosAdapterViewHolder,
        position: Int
    ) {
        val item = list[position]
        val dot = holder.statusDot
        when (item.textStatus) {
            TextStatus.READY -> {
                val drawable =
                    dot.background.mutate() // avoid affecting other views using same drawable
                drawable.setTint(Color.GREEN)
            }

            TextStatus.IN_PROCESS -> {
                val drawable =
                    dot.background.mutate() // avoid affecting other views using same drawable
                drawable.setTint(Color.YELLOW)
            }

            TextStatus.ERROR -> {
                val drawable =
                    dot.background.mutate() // avoid affecting other views using same drawable
                drawable.setTint(Color.RED)
            }

        }
        holder.name.text =item.name
        holder.time.text =item.time
        holder.duration.text =item.duration



    }

    override fun getItemCount(): Int = list.size

}
