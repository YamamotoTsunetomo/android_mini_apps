package com.example.intent_stuff

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.intent_stuff.databinding.FragmentCalendarBinding
import java.util.*


class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null

    private val binding get() = _binding!!

    private val btnSetEventOnClickListener =
        fun(title: String, location: String, begin: Long, end: Long) =
            Intent(Intent.ACTION_INSERT).apply {
                data = CalendarContract.Events.CONTENT_URI
                putExtra(CalendarContract.Events.TITLE, title)
                putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end).also {
                    startActivity(it)
                }
            }

    private val hasInvalidData = fun(fcb: FragmentCalendarBinding): Boolean {
        return listOf(
            fcb.etBeginTimeYear.text.isBlank(),
            fcb.etBeginTimeMonth.text.isBlank(),
            fcb.etBeginTimeDay.text.isBlank(),
            fcb.etBeginTimeHour.text.isBlank(),
            fcb.etBeginTimeMinutes.text.isBlank(),

            fcb.etEndTimeYear.text.isBlank(),
            fcb.etEndTimeMonth.text.isBlank(),
            fcb.etEndTimeDay.text.isBlank(),
            fcb.etEndTimeHour.text.isBlank(),
            fcb.etEndTimeMinutes.text.isBlank(),

            ).reduce { a, b -> a or b }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        binding.btnSetEvent.setOnClickListener {
            if (hasInvalidData(binding))
                Toast.makeText(activity, "Invalid Data", Toast.LENGTH_SHORT).show()
            else {
                val calendar = Calendar.getInstance()

                // Begin Year
                calendar.set(
                    Calendar.YEAR, binding.etBeginTimeYear.text.toString().toInt()
                )

                // Begin Month
                calendar.set(
                    Calendar.MONTH,
                    binding.etBeginTimeMonth.text.toString().toInt() - 1
                )

                // Begin Day
                calendar.set(
                    Calendar.DAY_OF_MONTH,
                    binding.etBeginTimeDay.text.toString().toInt()
                )

                // Begin Hour
                calendar.set(
                    Calendar.HOUR_OF_DAY,
                    binding.etBeginTimeHour.text.toString().toInt()
                )

                // Begin Minutes
                calendar.set(
                    Calendar.MINUTE,
                    binding.etBeginTimeMinutes.text.toString().toInt()
                )
                val beginTime = calendar.time.time

                // End Year
                calendar.set(
                    Calendar.YEAR, binding.etEndTimeYear.text.toString().toInt()
                )

                // End Month
                calendar.set(
                    Calendar.MONTH,
                    binding.etEndTimeMonth.text.toString().toInt() - 1
                )

                // End Day
                calendar.set(
                    Calendar.DAY_OF_MONTH,
                    binding.etEndTimeDay.text.toString().toInt()
                )

                // End Hour
                calendar.set(
                    Calendar.HOUR_OF_DAY,
                    binding.etEndTimeHour.text.toString().toInt()
                )

                // End Minutes
                calendar.set(
                    Calendar.MINUTE,
                    binding.etEndTimeMinutes.text.toString().toInt()
                )

                val endTime = calendar.time.time

                val title = binding.etEventTitle.text.toString()

                btnSetEventOnClickListener(title, "", beginTime, endTime)
            }
        }
        return binding.root
    }
}