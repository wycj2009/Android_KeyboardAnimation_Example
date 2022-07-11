package com.example.android_keyboardanimation_example

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.example.android_keyboardanimation_example.databinding.FragmentMainBinding
import kotlin.math.max

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val messageAdapter: MessageAdapter by lazy { MessageAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = messageAdapter.apply {
            submitList(
                List(50) { i -> Message("$i") }
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setupKeyboardAnimation()
        }
    }

    private fun setupKeyboardAnimation() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view: View, windowInsets: WindowInsetsCompat ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                topMargin = insets.top
                rightMargin = insets.right
                bottomMargin = insets.bottom
            }
            WindowInsetsCompat.CONSUMED
        }

        ViewCompat.setWindowInsetsAnimationCallback(binding.keyboardAnimationZone, object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {
            override fun onProgress(insets: WindowInsetsCompat, runningAnimations: MutableList<WindowInsetsAnimationCompat>): WindowInsetsCompat {
                binding.keyboardAnimationZone.translationY = -max(
                    0,
                    insets.getInsets(WindowInsetsCompat.Type.ime()).bottom - insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
                ).toFloat()

                return insets
            }
        })
    }

}
