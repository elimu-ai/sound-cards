package ai.elimu.soundcards.util

import ai.elimu.soundcards.SoundCardApplication
import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log

object TtsHelper {
    
    private const val TAG = "TtsHelper"
    
    fun speak(context: Context?, text: String?) {
        Log.i(TAG, "speak")

        Log.i(TAG, "text: $text")

        val application = context as SoundCardApplication
        val tts = application.tts
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}
