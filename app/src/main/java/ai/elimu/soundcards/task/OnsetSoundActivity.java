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

        if (wordsCorrectlySelected.size() == wordsWithImage.size()) {
            // TODO: show congratulations page
            finish();
            return;
        }

        int progress = (wordsCorrectlySelected.size() * 100) / wordsWithImage.size();
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(progressBar, "progress", progress);
        objectAnimator.setDuration(1000);
        objectAnimator.start();

        final WordGson alt1Word = wordsWithImage.get(wordsCorrectlySelected.size());
        Log.i(getClass().getName(), "alt1Word.getText(): " + alt1Word.getText());

//        List<ImageGson> alt1Images = ContentProvider.getAllImagesLabeledByWord(alt1Word);
//        final ImageGson alt1Image = alt1Images.get((int) (Math.random() * alt1Images.size()));
//        File alt1ImageFile = MultimediaHelper.getFile(alt1Image);
//        Bitmap alt1ImageBitmap = BitmapFactory.decodeFile(alt1ImageFile.getAbsolutePath());
//        final String alt1ImageDominantColor = alt1Image.getDominantColor();
//        final int alt1ColorIdentifier = parseRgbColor(alt1ImageDominantColor, 20);

        // TODO: fetch words with different onset sound
        List<WordGson> otherWords = new ArrayList<>(wordsWithImage);
        otherWords.remove(alt1Word);
        final WordGson alt2Word = otherWords.get((int) (Math.random() * otherWords.size()));
        Log.i(getClass().getName(), "alt2Word.getText(): " + alt2Word.getText());

//        List<ImageGson> alt2Images = ContentProvider.getAllImagesLabeledByWord(alt2Word);
//        final ImageGson alt2Image = alt2Images.get((int) (Math.random() * alt2Images.size()));
//        File alt2ImageFile = MultimediaHelper.getFile(alt2Image);
//        Bitmap alt2ImageBitmap = BitmapFactory.decodeFile(alt2ImageFile.getAbsolutePath());
//        final String alt2ImageDominantColor = alt2Image.getDominantColor();
//        final int alt2ColorIdentifier = parseRgbColor(alt2ImageDominantColor, 20);

        final boolean isAlt1Correct = (Math.random() > .5);
//        if (isAlt1Correct) {
//            alt1CardView.setCardBackgroundColor(alt1ColorIdentifier);
//            alt1ImageView.setImageBitmap(alt1ImageBitmap);
//            alt2CardView.setCardBackgroundColor(alt2ColorIdentifier);
//            alt2ImageView.setImageBitmap(alt2ImageBitmap);
//        } else {
//            alt1CardView.setCardBackgroundColor(alt2ColorIdentifier);
//            alt1ImageView.setImageBitmap(alt2ImageBitmap);
//            alt2CardView.setCardBackgroundColor(alt1ColorIdentifier);
//            alt2ImageView.setImageBitmap(alt1ImageBitmap);
//        }

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_swing_up_left);
        alt1CardView.startAnimation(animation);
        alt2CardView.startAnimation(animation);
        MediaPlayerHelper.play(getApplicationContext(), R.raw.shuffle_cards);

        alt1CardView.postDelayed(new Runnable() {
            @Override
            public void run() {
                MediaPlayerHelper.play(getApplicationContext(), R.raw.which_word_begins_with_this_sound);

                // TODO
            }
        }, 1000);
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
