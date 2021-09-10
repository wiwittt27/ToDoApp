package com.alawiyaa.todoapp.ui.task.dell

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alawiyaa.todoapp.R
import com.alawiyaa.todoapp.data.source.local.entity.Task
import com.alawiyaa.todoapp.databinding.ActivityDeleteBinding
import com.alawiyaa.todoapp.ui.task.add.AddTaskViewModel
import com.alawiyaa.todoapp.viewmodel.ToDoViewModelFactory

class DeleteActivity : AppCompatActivity(), View.OnClickListener {
    companion object{
        const val EXTRA_TASK = "extra_task"
    }
    private var task : Task? = null
    private lateinit var mViewModel: DeleteUpdateViewModel

    private var _binding : ActivityDeleteBinding? =null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val factory = ToDoViewModelFactory.getInstance(this)
        mViewModel = ViewModelProvider(this, factory)[DeleteUpdateViewModel::class.java]

         task = intent.getParcelableExtra(EXTRA_TASK)

        getData(task)
        binding?.deleteTasksBtn?.setOnClickListener(this)
        binding?.updateTasksBtn?.setOnClickListener(this)
    }

    private fun getData(task: Task?) {
        binding?.edtTitle?.setText(task?.title)
        binding?.edtDesc?.setText(task?.desc)
    }

    override fun onClick(v: View?) {
        val title = binding?.edtTitle?.text.toString().trim()
        val description = binding?.edtDesc?.text.toString().trim()
        when(v?.id){
            R.id.update_tasks_btn ->{
                task.let {
                    task?.title = title
                    task?.desc = description
                }
            mViewModel.updateTask(task as Task)
                finish()
            }
            R.id.delete_tasks_btn->{
            mViewModel.deleteTask(task as Task)
                finish()
            }
        }
    }


}