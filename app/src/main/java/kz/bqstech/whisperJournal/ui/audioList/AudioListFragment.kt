package kz.bqstech.whisperJournal.ui.audioList

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.MainScope
import kz.bqstech.AudioListParentAdapter
import kz.bqstech.whisperJournal.JournalListUiState
import kz.bqstech.whisperJournal.JournalListViewModel
import kz.bqstech.whisperJournal.data.remote.JournalRequestItem
import kz.bqstech.whisperJournal.databinding.FragmentAudioListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

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

    lateinit var binding: FragmentAudioListBinding

    val viewModel: JournalListViewModel by viewModel()

    lateinit var adapter: AudioListParentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = viewModel.state

        val s = data.value ?: JournalListUiState()
        val adapter = AudioListParentAdapter(
            (data.value ?: JournalListUiState()).journalEntries
        ) { audioUri ->
            viewModel.playAudioFromUri(
                requireContext(),
                audioUri = "http://webaudioapi.com/samples/audio-tag/chrono.mp3"
            )
            //viewModel.playAudio()

        }
        binding.audioListRecyclerView.adapter = adapter
        binding.audioListRecyclerView.layoutManager = LinearLayoutManager(context)




        viewModel.state.observe(viewLifecycleOwner) {
            Log.d("sadfsadfrfrf", it.toString())



//            binding.audioListRecyclerView.adapter =
//                AudioListParentAdapter(it.journalEntries, { audioUri ->
//                    viewModel.playAudioFromUri(
//                        requireContext(),
//                        "http://webaudioapi.com/samples/audio-tag/chrono.mp3"
//                    )
//                    //viewModel.playAudio()
//                })

            adapter.updateList(it.journalEntries)



            //TODO: look usufficient ,check
        }
        val musicDir = context?.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val mediaFiles = musicDir?.listFiles()?.filter { it.extension == "3gp" } ?: emptyList()
        Log.d("asdfasdffrfrfrf", mediaFiles.toString())

//        for (a in mediaFiles) {
//
//            viewModel.addItemToList(
//                JournalUiItem(
//                    id = 1,
//                    name = "asdfadsf ${a}",
//                    time = "sadfasdf ${a}",
//                    text = "asdfasdf",
//                    duration = "asdfasdf",
//                    textStatus = TextStatus.IN_PROCESS,
//                    date = "das",
//                    audioUri = ""
//                )
//            )
//
//        }


//        viewModel.addTestJournalList(
//            JournalRequestItem(
//                audio = mediaFiles[0],
//                name = mediaFiles[0].name,
//                time = "time"
//            )
//        )

        viewModel.getTestJournalList(onSuccess = {
            viewModel.setState(it)
        }, onFail = {
            viewModel.setState(it)
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAudioListBinding.inflate(layoutInflater)
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

