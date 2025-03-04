package com.example.assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class AddTaskFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var edtTaskName: EditText
    private lateinit var spinnerPriority: Spinner
    private lateinit var radioGroupMedia: RadioGroup
    private lateinit var radioAudio: RadioButton
    private lateinit var radioVideo: RadioButton
    private lateinit var btnSaveTask: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskViewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)

        edtTaskName = view.findViewById(R.id.edt_task_name)
        spinnerPriority = view.findViewById(R.id.spinner_priority)
        radioGroupMedia = view.findViewById(R.id.radio_group_media)
        radioAudio = view.findViewById(R.id.radio_audio)
        radioVideo = view.findViewById(R.id.radio_video)
        btnSaveTask = view.findViewById(R.id.btn_save_task)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerPriority.adapter = adapter
        }

        btnSaveTask.setOnClickListener {
            val taskName = edtTaskName.text.toString()
            val priority = spinnerPriority.selectedItem.toString()
            val mediaType = when (radioGroupMedia.checkedRadioButtonId) {
                R.id.radio_audio -> "Audio"
                R.id.radio_video -> "Video"
                else -> "Unknown"
            }

            if (taskName.isNotEmpty()) {
                taskViewModel.addTask(Task(taskName, priority, mediaType))
                edtTaskName.text.clear()
                spinnerPriority.setSelection(0)
                radioGroupMedia.clearCheck()
                // Navigate back to TaskListFragment using the public replaceFragment method
                (activity as MainActivity).replaceFragment(TaskListFragment())
            }
        }
    }
}
