package com.example.android_keyboardanimation_example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_keyboardanimation_example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(binding.frame.id, MainFragment()).commit()
    }

}
