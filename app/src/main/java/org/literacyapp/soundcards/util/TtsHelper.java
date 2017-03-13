package org.literacyapp.soundcards.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import org.literacyapp.soundcards.SoundCardApplication;

public class TtsHelper {

    public static void speak(Context context, String text) {
        Log.i(TtsHelper.class.getName(), "speak");

        Log.i(TtsHelper.class.getName(), "text: " + text);

        SoundCardApplication application = (SoundCardApplication) context;
        TextToSpeech tts = application.getTts();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }
}
