package com.alawiyaa.todoapp.ui.task.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alawiyaa.todoapp.R
import com.alawiyaa.todoapp.data.source.local.entity.Task
import com.alawiyaa.todoapp.databinding.ActivityAddTaskBinding
import com.alawiyaa.todoapp.util.STATUS_PENDING
import com.alawiyaa.todoapp.viewmodel.ToDoViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

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


        binding?.edtDate?.inputType = InputType.TYPE_NULL
        binding?.edtTimeStart?.inputType = InputType.TYPE_NULL
        binding?.edtTimeEnd?.inputType = InputType.TYPE_NULL

        binding?.addTasksBtn?.setOnClickListener(this)
        binding?.edtTimeStart?.setOnClickListener(this)
        binding?.edtTimeEnd?.setOnClickListener(this)
        binding?.edtDate?.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.add_tasks_btn -> {
                val title = binding?.edtTitle?.text.toString().trim()
                val description = binding?.edtDesc1?.text.toString().trim()
                val startTime = binding?.edtTimeStart?.text
                val endTime = binding?.edtTimeEnd?.text
                val date = binding?.edtDate?.text


                when {
                    title.isEmpty() -> {
                        binding?.edtTitle?.error = "Field can not be blank"
                        return
                    }
                    description.isEmpty() -> {
                        binding?.edtDesc1?.error = "Field can not be blank"
                        return
                    }
                    startTime?.isEmpty() == true -> {
                        binding?.edtTimeStart?.error = "Field can not be blank"
                        return
                    }
                    endTime?.isEmpty() == true -> {
                        binding?.edtTimeEnd?.error = "Field can not be blank"
                        return
                    }
                    date?.isEmpty() == true -> {
                        binding?.edtDate?.error = "Field can not be blank"
                        return
                    }
                    else -> {
                        task.let { task ->
                            task?.title = title
                            task?.desc = description
                            task?.status = STATUS_PENDING
                            task?.startTime = startTime.toString()
                            task?.endTime = endTime.toString()
                            task?.date = date.toString()
                        }
                        addTasViewModel.insertTask(task as Task)
                        Toast.makeText(this, "Task Ditambahkan", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    }
                }

            }
            R.id.edt_time_start -> {
                val calendar = Calendar.getInstance()
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        calendar[Calendar.HOUR_OF_DAY] = hourOfDay
                        calendar[Calendar.MINUTE] = minute
                        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                        binding?.edtTimeStart?.setText(simpleDateFormat.format(calendar.time))
                    }
                TimePickerDialog(
                    this@AddTaskActivity, timeSetListener,
                    calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], false
                ).show()

            }
            R.id.edt_time_end -> {
                val calendar = Calendar.getInstance()
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        calendar[Calendar.HOUR_OF_DAY] = hourOfDay
                        calendar[Calendar.MINUTE] = minute
                        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                        binding?.edtTimeEnd?.setText(simpleDateFormat.format(calendar.time))
                    }
                TimePickerDialog(
                    this@AddTaskActivity, timeSetListener,
                    calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], false
                ).show()
            }
            R.id.edt_date -> {
                val calendar = Calendar.getInstance()
                val dateSetListener =
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        calendar[Calendar.YEAR] = year
                        calendar[Calendar.MONTH] = month
                        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                        binding?.edtDate?.setText(simpleDateFormat.format(calendar.time))
                        Log.d("DATEE", "Date : ${simpleDateFormat.format(calendar.time)}")

                    }
                DatePickerDialog(
                    this@AddTaskActivity,
                    dateSetListener,
                    calendar[Calendar.YEAR],
                    calendar[Calendar.MONTH],
                    calendar[Calendar.DAY_OF_MONTH]
                ).show()

            }
        }


    }

}
