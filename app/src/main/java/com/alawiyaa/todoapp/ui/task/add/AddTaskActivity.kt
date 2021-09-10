package com.alawiyaa.todoapp.ui.task.add

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alawiyaa.todoapp.R
import com.alawiyaa.todoapp.data.source.local.entity.Task
import com.alawiyaa.todoapp.databinding.ActivityAddTaskBinding
import com.alawiyaa.todoapp.util.DataHelper

import com.alawiyaa.todoapp.viewmodel.ToDoViewModelFactory

class AddTaskActivity : AppCompatActivity(), View.OnClickListener {

    private var task: Task? = null
    private lateinit var addTasViewModel: AddTaskViewModel


    private var _binding: ActivityAddTaskBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        task = Task()



        val factory = ToDoViewModelFactory.getInstance(this)
        addTasViewModel = ViewModelProvider(this, factory)[AddTaskViewModel::class.java]



        binding?.addTasksBtn?.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        if (v?.id == R.id.add_tasks_btn) {
            val title = binding?.edtTitle?.text.toString().trim()
            val description = binding?.edtDesc?.text.toString().trim()


            if (title.isEmpty()) {
                binding?.edtTitle?.error = "Field can not be blank"
                return
            }

            task.let { task ->
                task?.title = title
                task?.desc = description
                task?.status = "Complete"

                task?.startTime = DataHelper.currentTime()
            }
            addTasViewModel.insertTask(task as Task)
            Toast.makeText(this,"Task Ditambahkan",Toast.LENGTH_SHORT).show()
            finish()

        }
    }
}