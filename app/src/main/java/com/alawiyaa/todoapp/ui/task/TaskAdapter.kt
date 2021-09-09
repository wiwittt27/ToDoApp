package com.alawiyaa.todoapp.ui.task

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alawiyaa.todoapp.data.source.local.entity.Task
import com.alawiyaa.todoapp.databinding.ItemTaskBinding
import com.alawiyaa.todoapp.util.STATUS_DONE

class TaskAdapter(private val activity: Activity) : PagedListAdapter<Task, TaskAdapter.TaskViewHolder>(DIFF_CALLBACK){


    companion  object  {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id  == newItem.id
            }
            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val news = getItem(position)
        if (news != null) {
            holder.bind(news)
        }
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            with(binding) {
                tvItemTime.text = task.day.toString()
                tvItemTitle.text = task.title
                tvItemStatus.text = task.status



                tvItemStatus.setOnClickListener {
                    Toast.makeText(activity,"Ini ${task.title}",Toast.LENGTH_SHORT).show()
                }




            }


        }

    }

}