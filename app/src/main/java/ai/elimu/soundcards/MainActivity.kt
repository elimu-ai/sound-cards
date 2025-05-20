package ai.elimu.soundcards

import ai.elimu.soundcards.task.OnsetSoundActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(javaClass.getName(), "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        Log.i(javaClass.getName(), "onStart")
        super.onStart()

        val intent = Intent(this, OnsetSoundActivity::class.java)
        startActivity(intent)

        finish()
    }
}
