package com.github.jing332.tts_server_android.ui.custom.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.*
import android.widget.RelativeLayout
import android.widget.SeekBar
import com.github.jing332.tts_server_android.R
import com.github.jing332.tts_server_android.databinding.ConvenientSeekbarBinding


@SuppressLint("ClickableViewAccessibility")
class ConvenientSeekbar(context: Context, attrs: AttributeSet?, defaultStyle: Int) :
    RelativeLayout(context, attrs, defaultStyle) {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    private lateinit var binding: ConvenientSeekbarBinding
    val seekBar by lazy { binding.seekBar }
    var max: Int
        inline get() = seekBar.max
        inline set(value) {
            seekBar.max = value
        }
    var progress: Int
        inline get() = seekBar.progress
        inline set(value) {
            seekBar.progress = value
        }

    init {
        binding = ConvenientSeekbarBinding.inflate(LayoutInflater.from(context))
        addView(
            binding.root,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val ta = context.obtainStyledAttributes(attrs, R.styleable.ConvenientSeekbar)
        seekBar.progress = ta.getInteger(R.styleable.ConvenientSeekbar_progress, 0)
        seekBar.max = ta.getInteger(R.styleable.ConvenientSeekbar_max, 100)
        ta.recycle()

        binding.add.setOnClickListener {
            progress += 1
            onSeekBarChangeListener?.onStopTrackingTouch(this)
        }
        binding.add.setOnLongClickListener {
            progress += 10
            onSeekBarChangeListener?.onStopTrackingTouch(this)
            true
        }

        binding.remove.setOnClickListener {
            progress -= 1
            onSeekBarChangeListener?.onStopTrackingTouch(this)
        }
        binding.remove.setOnLongClickListener {
            progress -= 10
            onSeekBarChangeListener?.onStopTrackingTouch(this)
            true
        }


        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                onSeekBarChangeListener?.onProgressChanged(this@ConvenientSeekbar, progress, fromUser)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                onSeekBarChangeListener?.onStartTrackingTouch(this@ConvenientSeekbar)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                onSeekBarChangeListener?.onStopTrackingTouch(this@ConvenientSeekbar)
            }
        })


    }

    var onSeekBarChangeListener: OnSeekBarChangeListener? = null

    interface OnSeekBarChangeListener {
        fun onProgressChanged(seekBar: ConvenientSeekbar, progress: Int, fromUser: Boolean)
        fun onStartTrackingTouch(seekBar: ConvenientSeekbar)
        fun onStopTrackingTouch(seekBar: ConvenientSeekbar)
    }

}