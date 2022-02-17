package com.example.intent_stuff

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.example.intent_stuff.databinding.FragmentClockBinding

class ClockFragment : Fragment() {

    private var _binding: FragmentClockBinding? = null

    private val binding get() = _binding!!

    private val showDataNotSet = fun() =
        Toast.makeText(activity, "Data Not Set!", Toast.LENGTH_SHORT).show()


    private val btnSetTimerOnClickListener = fun(message: String, seconds: Int) =
        Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_LENGTH, seconds)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }.also { startActivity(it) }

    private val btnSetAlarmOnClickListener = fun(
        message: String,
        hour: Int,
        minutes: Int,
    ) = Intent(AlarmClock.ACTION_SET_ALARM).apply {
        putExtra(AlarmClock.EXTRA_MESSAGE, message)
        putExtra(AlarmClock.EXTRA_HOUR, hour)
        putExtra(AlarmClock.EXTRA_MINUTES, minutes)
    }.also { startActivity(it) }


    private val onSeekBarChangeListener =
        fun(binding: FragmentClockBinding) = object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvTimerValue.text = binding.sbTimer.progress.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) = Unit
            override fun onStopTrackingTouch(p0: SeekBar?) = Unit
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClockBinding.inflate(inflater, container, false)

        binding.sbTimer.setOnSeekBarChangeListener(onSeekBarChangeListener(binding))
        binding.btnSetTimer.setOnClickListener {
            val timerValue = binding.tvTimerValue
            if (timerValue.text.isBlank())
                showDataNotSet()
            else
                btnSetTimerOnClickListener(
                    "", binding.tvTimerValue.text.toString().toInt()
                )
        }
        binding.btnSetAlarm.setOnClickListener {
            val hour = binding.etSetAlarmHours.text
            val minutes = binding.etSetAlarmMinutes.text
            if (hour.isBlank() || minutes.isBlank())
                showDataNotSet()
            else
                btnSetAlarmOnClickListener(
                    "Alarm",
                    hour.toString().toInt(),
                    minutes.toString().toInt()
                )
        }
        return binding.root
    }
}