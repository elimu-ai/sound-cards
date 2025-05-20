package ai.elimu.soundcards

import android.app.Application
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log

class SoundCardApplication : Application() {
    var tts: TextToSpeech? = null
        private set

    override fun onCreate() {
        Log.i(javaClass.getName(), "onCreate")
        super.onCreate()

        // Initialize TTS
        tts = TextToSpeech(getApplicationContext(), object : OnInitListener {
            override fun onInit(status: Int) {
                Log.i(javaClass.getName(), "TextToSpeech onInit")
                Log.i(javaClass.getName(), "TextToSpeech status: " + status)
            }
        })
    }
}
