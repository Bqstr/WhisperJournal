package kz.bqstech.whisperJournal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.bqstech.AudioListParentAdapter
import kz.bqstech.whisperJournal.databinding.FragmentAudioListBinding
import org.koin.androidx.compose.koinViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AudioListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AudioListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    lateinit var binding : FragmentAudioListBinding

    val viewModel : JournalListViewModel by viewModels()

     lateinit var adapter : AudioListParentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data =viewModel.state

        val s=data.value ?: JournalListUiState()
        val adapter = AudioListParentAdapter((data.value ?: JournalListUiState()).journalEntries)
        binding.audioListRecyclerView.adapter =adapter
        binding.audioListRecyclerView.layoutManager= LinearLayoutManager(context)




        viewModel.state.observe(viewLifecycleOwner){
            Log.d("sadfsadfrfrf",it.toString())
            binding.audioListRecyclerView.adapter = AudioListParentAdapter(it.journalEntries)
            //TODO: look usufficient ,check
        }

        for (a in 0 until 10) {
            viewModel.addItemToList(
                JournalItem(
                    id = 1,
                    name = "asdfadsf ${a}",
                    time = "sadfasdf ${a}",
                    text = "asdfasdf",
                    duration = "asdfasdf",
                    textStatus = TextStatus.IN_PROCESS
                )
            )

        }





    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentAudioListBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AudioListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AudioListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}