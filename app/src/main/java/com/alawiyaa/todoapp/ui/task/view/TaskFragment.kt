package com.alawiyaa.todoapp.ui.task.view

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.size
import androidx.fragment.app.Fragment
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
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding
    private lateinit  var mainViewModel: TaskViewModel

    private val calendar = Calendar.getInstance()
    private var currentMonth = 0
    private var dateNow =""
    private lateinit var adapter: TaskAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            calendar.time = Date()
            currentMonth = calendar[Calendar.MONTH]
            binding?.tvDate?.text =
                "${DateUtils.getMonthName(calendar.time)}, ${DateUtils.getDayNumber(calendar.time)} "
            binding?.tvDay?.text = DateUtils.getDayName(calendar.time)
            dateNow = "${DateUtils.getYear(calendar.time)}/${DateUtils.getMonthNumber(calendar.time)}/${DateUtils.getDayNumber(calendar.time)}"
            Log.d("PAUSE", "View Created")
            adapter = TaskAdapter(requireActivity())
            val factory = ToDoViewModelFactory.getInstance(requireActivity())
            mainViewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]
            mainViewModel.getByDate(dateNow).observe(viewLifecycleOwner, { listTask ->
                if (listTask != null && listTask.size > 0) {
                    adapter.submitList(listTask)
                    binding?.viewData?.root?.visibility = View.GONE
                    binding?.cardRv?.visibility = View.VISIBLE
                }
            })
        }

            with(binding?.rvTask) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = adapter
            }
            if (adapter.itemCount == 0) {
                binding?.cardRv?.visibility = View.GONE
                binding?.viewData?.root?.visibility = View.VISIBLE

            }

        // set current date to calendar and current month to currentMonth variable





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

                        else -> R.layout.selected_calender_item
                    }
                else
                // here we return items which are not selected
                    when (cal[Calendar.DAY_OF_WEEK]) {


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
                binding?.tvDate?.text =
                    "${DateUtils.getMonthName(date)}, ${DateUtils.getDayNumber(date)} "
                binding?.tvDay?.text = DateUtils.getDayName(date)
                var dates = "${DateUtils.getYear(date)}/${DateUtils.getMonthNumber(date)}/${DateUtils.getDayNumber(date)}"

                mainViewModel.getByDate(dates).observe(viewLifecycleOwner, { listTask ->
                    if (listTask != null && listTask.size > 0) {
                        adapter.submitList(listTask)
                        R.layout.first_special_calendar_item
                        binding?.viewData?.root?.visibility = View.GONE
                        binding?.cardRv?.visibility = View.VISIBLE
                    }else{
                        binding?.viewData?.root?.visibility = View.VISIBLE
                        binding?.cardRv?.visibility = View.GONE

                    }
                })

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



    }
     fun showBookmark() {
         mainViewModel.getByDate(dateNow).observe(viewLifecycleOwner, { listTask ->
             if (listTask != null && listTask.size > 0) {
                 adapter.submitList(listTask)
                 binding?.viewData?.root?.visibility = View.GONE
                 binding?.cardRv?.visibility = View.VISIBLE

             }

         })
    }

    override fun onPause() {
        super.onPause()
        showBookmark()
        Log.d("PAUSE","Pause")
    }

    override fun onStart() {
        super.onStart()
        showBookmark()
        Log.d("PAUSE","Start")
    }

    override fun onResume() {

        super.onResume()
        showBookmark()
        Log.d("PAUSE","Resume")
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