package ai.elimu.soundcards

import android.app.Application
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log

class SoundCardApplication : Application() {
    
    private val TAG = "SoundCardApplication"
    var tts: TextToSpeech? = null
        private set

    override fun onCreate() {
        Log.i(TAG, "onCreate")
        super.onCreate()

        // Initialize TTS
        tts = TextToSpeech(applicationContext, object : OnInitListener {
            override fun onInit(status: Int) {
                Log.i(TAG, "TextToSpeech onInit")
                Log.i(TAG, "TextToSpeech status: $status")
            }
        })
    }
}
