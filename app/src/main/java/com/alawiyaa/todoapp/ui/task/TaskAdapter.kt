package com.alawiyaa.todoapp.ui.task

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alawiyaa.todoapp.data.source.local.entity.Task
import com.alawiyaa.todoapp.databinding.ItemTaskBinding
import com.alawiyaa.todoapp.ui.task.dell.DeleteActivity
import com.alawiyaa.todoapp.ui.task.dell.DeleteActivity.Companion.EXTRA_TASK

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
                tvItemTime.text = task.startTime
                tvItemTitle.text = task.title
                tvItemStatus.text = "Complete"
                tvStart.text    = task.startTime
                tvEnd.text = task.endTime



                layoutItem.setOnClickListener {
                   val detail = Intent(activity,DeleteActivity::class.java)
                    detail.putExtra(EXTRA_TASK,task)
                    activity.startActivity(detail)
                }




            }


        }

    }

}