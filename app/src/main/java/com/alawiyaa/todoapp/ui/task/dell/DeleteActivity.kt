package com.alawiyaa.todoapp.ui.task.dell

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.alawiyaa.todoapp.R
import com.alawiyaa.todoapp.data.source.local.entity.Task
import com.alawiyaa.todoapp.databinding.ActivityDeleteBinding
import com.alawiyaa.todoapp.ui.task.add.AddTaskViewModel
import com.alawiyaa.todoapp.util.STATUS_DONE
import com.alawiyaa.todoapp.viewmodel.ToDoViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class DeleteActivity : AppCompatActivity(), View.OnClickListener {
    companion object{
        const val EXTRA_TASK = "extra_task"
    }
    private var task : Task? = null
    private lateinit var mViewModel: DeleteUpdateViewModel
    var status:Int = 0
    private var _binding : ActivityDeleteBinding? =null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val factory = ToDoViewModelFactory.getInstance(this)
        mViewModel = ViewModelProvider(this, factory)[DeleteUpdateViewModel::class.java]

         task = intent.getParcelableExtra(EXTRA_TASK)

        binding?.edtDate?.inputType = InputType.TYPE_NULL
        binding?.edtTimeStart?.inputType = InputType.TYPE_NULL
        binding?.edtTimeEnd?.inputType = InputType.TYPE_NULL

        getData(task)
        binding?.deleteTasksBtn?.setOnClickListener(this)
        binding?.updateTasksBtn?.setOnClickListener(this)
        binding?.edtTimeStart?.setOnClickListener(this)
        binding?.edtTimeEnd?.setOnClickListener(this)
        binding?.edtDate?.setOnClickListener(this)
    }

    private fun getData(task: Task?) {
        binding?.edtTitle?.setText(task?.title)
        binding?.edtDesc?.setText(task?.desc)
        binding?.edtTimeStart?.setText(task?.startTime)
        binding?.edtTimeEnd?.setText(task?.endTime)
        binding?.edtDate?.setText(task?.date)
    }

    override fun onClick(v: View?) {
        val title = binding?.edtTitle?.text.toString().trim()
        val description = binding?.edtDesc?.text.toString().trim()
        val startTime =  binding?.edtTimeStart?.text
        val endTime = binding?.edtTimeEnd?.text
        val date = binding?.edtDate?.text

        if (binding?.cbComplete?.isChecked == true){
            status = STATUS_DONE
            binding?.cbComplete?.text = "Complete"
        }
        when(v?.id){
            R.id.update_tasks_btn ->{
                task.let {
                    task?.title = title
                    task?.desc = description
                    task?.startTime = startTime.toString()
                    task?.endTime = endTime.toString()
                    task?.date = date.toString()
                    task?.status = status
                }
            mViewModel.updateTask(task as Task)
                Toast.makeText(this,"Task Diubah", Toast.LENGTH_SHORT).show()
                finish()
            }
            R.id.delete_tasks_btn->{
            mViewModel.deleteTask(task as Task)
                Toast.makeText(this,"Task Dihapus",Toast.LENGTH_SHORT).show()
                finish()
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
                    this@DeleteActivity, timeSetListener,
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
                    this@DeleteActivity, timeSetListener,
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
                    }
                DatePickerDialog(
                    this@DeleteActivity,
                    dateSetListener,
                    calendar[Calendar.YEAR],
                    calendar[Calendar.MONTH],
                    calendar[Calendar.DAY_OF_MONTH]
                ).show()
            }
        }
    }


}