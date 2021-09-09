package com.alawiyaa.todoapp.ui.task.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alawiyaa.todoapp.R
import com.alawiyaa.todoapp.data.source.local.entity.Task
import com.alawiyaa.todoapp.databinding.ActivityAddTaskBinding
import com.alawiyaa.todoapp.util.EXTRA_POSITION
import com.alawiyaa.todoapp.util.EXTRA_TASK
import com.alawiyaa.todoapp.util.STATUS_PENDING
import com.alawiyaa.todoapp.util.timepickerinput.getDateDetails
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

        supportActionBar?.title = "Add Task"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ToDoViewModelFactory.getInstance(this)
        addTasViewModel = ViewModelProvider(this, factory)[AddTaskViewModel::class.java]



        binding?.addTasksBtn?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.add_tasks_btn) {
            val title = binding?.edtTitle?.text.toString().trim()
            val description = binding?.edtDesc?.text.toString().trim()
            val status = binding?.cbStatus?.text.toString()
            val taskMillis = binding?.timePickerET?.selectedMillis
            val (day, month, year) = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                getDateDetails(taskMillis!!)
            } else {
                TODO("VERSION.SDK_INT < O")
            }

            if (title.isEmpty()) {
                binding?.edtTitle?.error = "Field can not be blank"
                return
            }

            task.let { task ->
                task?.title = title
                task?.desc = description
                task?.status = status
                task?.day = day
                task?.month = month
                task?.year = year
                task?.startTime =taskMillis
            }
            addTasViewModel.insertTask(task as Task)
            Toast.makeText(this,"Task Ditambahkan",Toast.LENGTH_SHORT).show()
            finish()

        }
    }
}