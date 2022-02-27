package ai.elimu.soundcards;

import android.app.Application;
import android.speech.tts.TextToSpeech;
import android.util.Log;

public class SoundCardApplication extends Application {

    private TextToSpeech tts;

    @Override
    public void onCreate() {
        Log.i(getClass().getName(), "onCreate");
        super.onCreate();

        // Initialize TTS
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.i(getClass().getName(), "TextToSpeech onInit");
                Log.i(getClass().getName(), "TextToSpeech status: " + status);
            }
        });
    }

    public TextToSpeech getTts() {
        return tts;
    }
}
