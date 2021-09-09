package com.alawiyaa.todoapp.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alawiyaa.todoapp.R
import com.alawiyaa.todoapp.databinding.FragmentSettingsBinding
import com.alawiyaa.todoapp.databinding.FragmentTaskBinding
import com.alawiyaa.todoapp.ui.task.TaskAdapter
import com.alawiyaa.todoapp.ui.task.TaskViewModel
import com.alawiyaa.todoapp.viewmodel.ToDoViewModelFactory


class SettingsFragment : Fragment() {
    private var _binding : FragmentSettingsBinding?=null
    private val binding get() = _binding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}