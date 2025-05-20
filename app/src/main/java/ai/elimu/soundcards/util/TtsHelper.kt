package ai.elimu.soundcards.util

import ai.elimu.soundcards.SoundCardApplication
import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log

object TtsHelper {
    fun speak(context: Context?, text: String?) {
        Log.i(TtsHelper::class.java.getName(), "speak")

        Log.i(TtsHelper::class.java.getName(), "text: " + text)

        val application = context as SoundCardApplication
        val tts = application.tts
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}
