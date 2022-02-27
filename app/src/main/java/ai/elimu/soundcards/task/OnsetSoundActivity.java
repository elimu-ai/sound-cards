package ai.elimu.soundcards.task;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ai.elimu.model.v2.gson.content.WordGson;
import ai.elimu.soundcards.R;
import ai.elimu.soundcards.util.MediaPlayerHelper;

public class OnsetSoundActivity extends AppCompatActivity {

    private List<WordGson> wordsWithImage;

    private List<WordGson> wordsCorrectlySelected;

    private ImageView emojiImageView;

    private ProgressBar progressBar;

    private CardView alt1CardView;
    private ImageView alt1ImageView;

    private CardView alt2CardView;
    private ImageView alt2ImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(getClass().getName(), "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_onset_sound);

        wordsCorrectlySelected = new ArrayList<>();

        emojiImageView = (ImageView) findViewById(R.id.emoji);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        alt1CardView = (CardView) findViewById(R.id.alt1CardView);
        alt1ImageView = (ImageView) findViewById(R.id.alt1ImageView);

        alt2CardView = (CardView) findViewById(R.id.alt2CardView);
        alt2ImageView = (ImageView) findViewById(R.id.alt2ImageView);

        // Fetch words starting with one of the unlocked letter-sound correspondences
        // TODO

        // Fetch 10 most frequent words with a corresponding image
        // TODO
    }

    @Override
    protected void onStart() {
        Log.i(getClass().getName(), "onStart");
        super.onStart();

        // Animate subtle head movements
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(emojiImageView, "rotation", 2);
        objectAnimator.setDuration(1000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();

        loadNextTask();
    }

    private void loadNextTask() {
        Log.i(getClass().getName(), "loadNextTask");

        // TODO
    }

    private int parseRgbColor(String input, int alpha) {
        Pattern pattern = Pattern.compile("rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            int rgbRed = Integer.valueOf(matcher.group(1));
            int rgbGreen = Integer.valueOf(matcher.group(2));
            int rgbBlue = Integer.valueOf(matcher.group(3));
            int colorIdentifier = Color.argb(alpha, rgbRed, rgbGreen, rgbBlue);
            return colorIdentifier;
        } else {
            return -1;
        }
    }

    private void playSound(String ipaValue) {
        Log.i(getClass().getName(), "playSound");

        // Look up corresponding Audio
        Log.d(getClass().getName(), "Looking up \"sound_" + ipaValue + "\"");
        // TODO
    }

    private void playWord(WordGson word) {
        Log.i(getClass().getName(), "playWord");

        // Look up corresponding Audio recording
        Log.d(getClass().getName(), "Looking up \"" + word.getText() + "\"");
        // TODO
    }
}
