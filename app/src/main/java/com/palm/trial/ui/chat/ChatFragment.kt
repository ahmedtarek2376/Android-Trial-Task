package com.palm.trial.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.palm.trial.R

class ChatFragment : Fragment() {

    private val viewModel: ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.chat_recycler_view)

        viewModel.messages.observe(viewLifecycleOwner) { msgs ->
            Log.d(TAG, "Messages observed, fragment = $this")
            recyclerView.adapter = MessagesAdapter(msgs)
        }

    }

    companion object {
        const val TAG = "ChatFragment"
    }
}