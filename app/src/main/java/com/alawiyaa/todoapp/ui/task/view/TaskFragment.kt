package com.alawiyaa.todoapp.ui.task.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alawiyaa.todoapp.R
import com.alawiyaa.todoapp.databinding.FragmentTaskBinding
import com.alawiyaa.todoapp.ui.task.TaskAdapter
import com.alawiyaa.todoapp.viewmodel.ToDoViewModelFactory
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager
import com.michalsvec.singlerowcalendar.utils.DateUtils
import java.util.*



class TaskFragment : Fragment() {
    private var _binding : FragmentTaskBinding?=null
    private val binding get() = _binding
    private lateinit var mainViewModel : TaskViewModel

    private val calendar = Calendar.getInstance()
    private var currentMonth = 0
    private lateinit var adapter: TaskAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set current date to calendar and current month to currentMonth variable
        calendar.time = Date()
        currentMonth = calendar[Calendar.MONTH]
        binding?.tvDate?.text = "${DateUtils.getMonthName(calendar.time)}, ${DateUtils.getDayNumber(calendar.time)} "
        binding?.tvDay?.text = DateUtils.getDayName(calendar.time)


        // enable white status bar with black icons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
         activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity?.window?.statusBarColor = Color.WHITE
        }

        // calendar view manager is responsible for our displaying logic
        val myCalendarViewManager = object :
            CalendarViewManager {
            override fun setCalendarViewResourceId(
                position: Int,
                date: Date,
                isSelected: Boolean
            ): Int {
                // set date to calendar according to position where we are
                val cal = Calendar.getInstance()
                cal.time = date
                // if item is selected we return this layout items
                // in this example. monday, wednesday and friday will have special item views and other days
                // will be using basic item view
                return if (isSelected)
                    when (cal[Calendar.DAY_OF_WEEK]) {
                        Calendar.MONDAY -> R.layout.first_special_selected_calendar_item
                        else -> R.layout.selected_calender_item
                    }
                else
                // here we return items which are not selected
                    when (cal[Calendar.DAY_OF_WEEK]) {
                        Calendar.MONDAY -> R.layout.first_special_calendar_item

                        else -> R.layout.calender_item
                    }

                // NOTE: if we don't want to do it this way, we can simply change color of background
                // in bindDataToCalendarView method
            }

            override fun bindDataToCalendarView(
                holder: SingleRowCalendarAdapter.CalendarViewHolder,
                date: Date,
                position: Int,
                isSelected: Boolean
            ) {

                // using this method we can bind data to calendar view
                // good practice is if all views in layout have same IDs in all item views
                val aa = holder.itemView.findViewById<TextView>(R.id.tv_date_calendar_item)
                val bb = holder.itemView.findViewById<TextView>(R.id.tv_day_calendar_item)
                aa?.text = DateUtils.getDayNumber(date)
                bb?.text = DateUtils.getDay3LettersName(date)



            }

            }

        // using calendar changes observer we can track changes in calendar
        val myCalendarChangesObserver = object :
            CalendarChangesObserver {
            // you can override more methods, in this example we need only this one
            override fun whenSelectionChanged(isSelected: Boolean, position: Int, date: Date) {
                binding?.tvDate?.text = "${DateUtils.getMonthName(date)}, ${DateUtils.getDayNumber(date)} "
                binding?.tvDay?.text = DateUtils.getDayName(date)
                super.whenSelectionChanged(isSelected, position, date)
            }


        }

        // selection manager is responsible for managing selection
        val mySelectionManager = object : CalendarSelectionManager {
            override fun canBeItemSelected(position: Int, date: Date): Boolean {
                // set date to calendar according to position
                val cal = Calendar.getInstance()
                cal.time = date
                // in this example sunday and saturday can't be selected, others can
                return when (cal[Calendar.DAY_OF_WEEK]) {
                    else -> true
                }
            }
        }




        // here we init our calendar, also you can set more properties if you haven't specified in XML layout
        val singleRowCalendar = binding?.mainSingleRowCalendar?.apply {
            calendarViewManager = myCalendarViewManager
            calendarChangesObserver = myCalendarChangesObserver
            calendarSelectionManager = mySelectionManager
            setDates(getFutureDatesOfCurrentMonth())
            init()
        }

       binding?.btnRight?.setOnClickListener {
            singleRowCalendar?.setDates(getDatesOfNextMonth())
        }

        binding?.btnLeft?.setOnClickListener {
            singleRowCalendar?.setDates(getDatesOfPreviousMonth())
        }
        if (activity != null) {
            adapter = TaskAdapter(requireActivity())
            val factory = ToDoViewModelFactory.getInstance(requireActivity())
            mainViewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]

            mainViewModel.getAllTask().observe(viewLifecycleOwner, { listTask ->
                if (listTask != null && listTask.size > 0) {
                    adapter.submitList(listTask)
                    binding?.viewData?.root?.visibility = View.GONE
                }
            })


            with(binding?.rvTask) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = adapter
            }
            if (adapter.itemCount == 0) {
                binding?.viewData?.root?.visibility = View.VISIBLE
            }
        }
    }

    private fun showBookmark(){
        mainViewModel.getAllTask().observe(viewLifecycleOwner, {listTask->
            if (listTask != null && listTask.size > 0) {
                adapter.submitList(listTask)
                binding?.viewData?.root?.visibility = View.GONE


            }
        })
    }






    private fun getDatesOfNextMonth(): List<Date> {
        currentMonth++ // + because we want next month
        if (currentMonth == 12) {
            // we will switch to january of next year, when we reach last month of year
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] + 1)
            currentMonth = 0 // 0 == january
        }
        return getDates(mutableListOf())
    }

    private fun getDatesOfPreviousMonth(): List<Date> {
        currentMonth-- // - because we want previous month
        if (currentMonth == -1) {
            // we will switch to december of previous year, when we reach first month of year
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] - 1)
            currentMonth = 11 // 11 == december
        }
        return getDates(mutableListOf())
    }

    private fun getFutureDatesOfCurrentMonth(): List<Date> {
        // get all next dates of current month
        currentMonth = calendar[Calendar.MONTH]
        return getDates(mutableListOf())
    }


    private fun getDates(list: MutableList<Date>): List<Date> {
        // load dates of whole month
        calendar.set(Calendar.MONTH, currentMonth)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        list.add(calendar.time)
        while (currentMonth == calendar[Calendar.MONTH]) {
            calendar.add(Calendar.DATE, +1)
            if (calendar[Calendar.MONTH] == currentMonth)
                list.add(calendar.time)
        }
        calendar.add(Calendar.DATE, -1)
        return list
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}