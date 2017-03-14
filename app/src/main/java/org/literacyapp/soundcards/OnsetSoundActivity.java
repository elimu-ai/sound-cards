package org.literacyapp.soundcards;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.literacyapp.contentprovider.ContentProvider;
import org.literacyapp.contentprovider.model.content.Word;
import org.literacyapp.contentprovider.model.content.multimedia.Audio;
import org.literacyapp.soundcards.util.MediaPlayerHelper;
import org.literacyapp.soundcards.util.MultimediaHelper;
import org.literacyapp.soundcards.util.TtsHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OnsetSoundActivity extends AppCompatActivity {

    private List<Word> wordsWithMatchingAudioAndImage;

    private List<Word> wordsCorrectlySelected;

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

        progressBar = (ProgressBar) findViewById(R.id.listenAndSelectProgressBar);

        alt1CardView = (CardView) findViewById(R.id.alt1CardView);
        alt1ImageView = (ImageView) findViewById(R.id.alt1ImageView);

        alt2CardView = (CardView) findViewById(R.id.alt2CardView);
        alt2ImageView = (ImageView) findViewById(R.id.alt2ImageView);

//        // Fetch 10 most frequent words with matching audio and image
//        wordsWithMatchingAudioAndImage = new ArrayList<>();
//        List<Word> words = ContentProvider.getAllWordsOrderedByFrequency();
//        Log.i(getClass().getName(), "words.size(): " + words.size());
//        for (Word word : words) {
//            Audio matchingAudio = ContentProvider.getAudio(word.getText());
//            Image matchingImage = ContentProvider.getImage(word.getText());
//            // TODO: add audio as requirement
//            if (/*(matchingAudio != null) &&*/ (matchingImage != null)) {
//                Log.i(getClass().getName(), "Adding \"" + word.getText() + "\"...");
//                Log.i(getClass().getName(), "matchingImage.getDominantColor(): " + matchingImage.getDominantColor());
//                wordsWithMatchingAudioAndImage.add(word);
//            }
//            if (wordsWithMatchingAudioAndImage.size() == 10) {
//                break;
//            }
//        }
//        Log.i(getClass().getName(), "wordsWithMatchingAudioAndImage.size(): " + wordsWithMatchingAudioAndImage.size());
    }

    @Override
    protected void onStart() {
        Log.i(getClass().getName(), "onStart");
        super.onStart();

        alt1CardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ObjectAnimator animator = ObjectAnimator.ofFloat(alt1CardView, "cardElevation", alt1CardView.getCardElevation(), 0, alt1CardView.getCardElevation());
                animator.setDuration(500);
                animator.start();

                return false;
            }
        });

        alt1CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(getClass().getName(), "alt1CardView onClick");

                MediaPlayerHelper.play(getApplicationContext(), R.raw.alternative_incorrect);

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_shake);
                alt1CardView.startAnimation(animation);
            }
        });

//        alt2CardView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                ObjectAnimator animator = ObjectAnimator.ofFloat(alt2CardView, "cardElevation", alt2CardView.getCardElevation(), 0, alt2CardView.getCardElevation());
//                animator.setDuration(500);
//                animator.start();
//
//                return false;
//            }
//        });

        alt2CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(getClass().getName(), "alt1CardView onClick");

                MediaPlayerHelper.play(getApplicationContext(), R.raw.alternative_correct);

                ObjectAnimator animator = ObjectAnimator.ofFloat(alt2CardView, "cardElevation", alt2CardView.getCardElevation(), alt2CardView.getCardElevation() + 10);
                animator.setDuration(1000);
                animator.start();

                alt2CardView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_disappear_left);
                        alt1CardView.startAnimation(animation);
                        alt2CardView.startAnimation(animation);
                    }
                }, 2000);

                progressBar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(progressBar, "progress", 20);
                        objectAnimator.setDuration(1000);
                        objectAnimator.start();
                    }
                }, 3000);
            }
        });

        loadNextImage();
    }

    private void loadNextImage() {
        Log.i(getClass().getName(), "loadNextImage");

//        if (wordsCorrectlySelected.size() == wordsWithMatchingAudioAndImage.size()) {
//            // TODO: show congratulations page
//            finish();
//            return;
//        }
//
//        // Reset background color
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
////        getWindow().getDecorView().setBackground(defaultBackgroundDrawable);
//        getWindow().getDecorView().setBackground(new ColorDrawable(Color.parseColor("#EEEEEE")));
//        progressBar.setProgress(wordsCorrectlySelected.size() * 100 / wordsWithMatchingAudioAndImage.size());
//        alt1CardView.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        alt1ImageView.setAlpha(255);
//        alt2CardView.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        alt2ImageView.setAlpha(255);
//
//        final Word alt1Word = wordsWithMatchingAudioAndImage.get(wordsCorrectlySelected.size());
//        Log.i(getClass().getName(), "alt1Word.getText(): " + alt1Word.getText());
//        getSupportActionBar().setTitle(alt1Word.getText());
//
//        final Image alt1Image = ContentProvider.getImage(alt1Word.getText());
//        File alt1ImageFile = MultimediaHelper.getFile(alt1Image);
//        Bitmap alt1ImageBitmap = BitmapFactory.decodeFile(alt1ImageFile.getAbsolutePath());
//        String alt1ImageDominantColor = alt1Image.getDominantColor();
//        final int alt1ColorIdentifier = parseRgbColor(alt1ImageDominantColor);
//
//        List<Word> otherWords = new ArrayList<>(wordsWithMatchingAudioAndImage);
//        otherWords.remove(alt1Word);
//        final Word alt2Word = otherWords.get((int) (Math.random() * otherWords.size()));
//        Log.i(getClass().getName(), "alt2Word.getText(): " + alt2Word.getText());
//
//        Image alt2Image = ContentProvider.getImage(alt2Word.getText());
//        File alt2ImageFile = MultimediaHelper.getFile(alt2Image);
//        Bitmap alt2ImageBitmap = BitmapFactory.decodeFile(alt2ImageFile.getAbsolutePath());
//        String alt2ImageDominantColor = alt2Image.getDominantColor();
//        final int alt2ColorIdentifier = parseRgbColor(alt2ImageDominantColor);
//
//        // TODO: play instruction audio
//
//        playWord(alt1Word);
//
//        boolean isAlt1Correct = (Math.random() > .5);
//        if (isAlt1Correct) {
//            alt1ImageView.setImageBitmap(alt1ImageBitmap);
//            alt2ImageView.setImageBitmap(alt2ImageBitmap);
//
//            alt1CardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.i(getClass().getName(), "alt1CardView onClick");
//
//                    MediaPlayerHelper.play(getApplicationContext(), R.raw.alternative_correct);
//
//                    wordsCorrectlySelected.add(alt1Word);
//                    progressBar.setProgress(wordsCorrectlySelected.size() * 100 / wordsWithMatchingAudioAndImage.size());
//
//                    getWindow().getDecorView().setBackgroundColor(alt1ColorIdentifier);
//                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(alt1ColorIdentifier));
//
//                    alt1CardView.setOnClickListener(null);
//
//                    alt2CardView.setOnClickListener(null);
//                    alt2CardView.setBackgroundColor(Color.parseColor("#00FFFFFF")); // 100% transparent
//                    alt2ImageView.setAlpha(128); // 50% opaque
//
//                    alt1ImageView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            loadNextImage();
//                        }
//                    }, 2000);
//                }
//            });
//
//            alt2CardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.i(getClass().getName(), "alt2CardView onClick");
//
//                    MediaPlayerHelper.play(getApplicationContext(), R.raw.alternative_incorrect);
//
//                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_shake);
//                    alt2CardView.startAnimation(animation);
//                }
//            });
//        } else {
//            alt1ImageView.setImageBitmap(alt2ImageBitmap);
//            alt2ImageView.setImageBitmap(alt1ImageBitmap);
//
//            alt2CardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.i(getClass().getName(), "alt2CardView onClick");
//
//                    MediaPlayerHelper.play(getApplicationContext(), R.raw.alternative_correct);
//
//                    wordsCorrectlySelected.add(alt2Word);
//                    progressBar.setProgress(wordsCorrectlySelected.size() * 100 / wordsWithMatchingAudioAndImage.size());
//
//                    getWindow().getDecorView().setBackgroundColor(alt1ColorIdentifier);
//                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(alt1ColorIdentifier));
//
//                    alt1CardView.setOnClickListener(null);
//
//                    alt2CardView.setOnClickListener(null);
//                    alt1CardView.setBackgroundColor(Color.parseColor("#00FFFFFF")); // 100% transparent
//                    alt1ImageView.setAlpha(128); // 50% opaque
//
//                    alt1ImageView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            loadNextImage();
//                        }
//                    }, 2000);
//                }
//            });
//
//            alt1CardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.i(getClass().getName(), "alt1CardView onClick");
//
//                    MediaPlayerHelper.play(getApplicationContext(), R.raw.alternative_incorrect);
//
//                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_shake);
//                    alt1CardView.startAnimation(animation);
//                }
//            });
//        }
    }

    private int parseRgbColor(String input) {
        Pattern pattern = Pattern.compile("rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            int rgbRed = Integer.valueOf(matcher.group(1));
            int rgbGreen = Integer.valueOf(matcher.group(2));
            int rgbBlue = Integer.valueOf(matcher.group(3));
            int colorIdentifier = Color.rgb(rgbRed, rgbGreen, rgbBlue);
            return colorIdentifier;
        } else {
            return -1;
        }
    }

    private void playWord(Word word) {
        Log.i(getClass().getName(), "playWord");

        // Look up corresponding Audio recording
        Log.d(getClass().getName(), "Looking up \"" + word.getText() + "\"");
        Audio audio = ContentProvider.getAudio(word.getText());
        Log.i(getClass().getName(), "audio: " + audio);
        if (audio != null) {
            // Play audio
            File audioFile = MultimediaHelper.getFile(audio);
            Uri uri = Uri.parse(audioFile.getAbsolutePath());
            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Log.i(getClass().getName(), "onCompletion");
                    mediaPlayer.release();
                }
            });
            mediaPlayer.start();
        } else {
            // Audio recording not found. Fall-back to TTS.
            TtsHelper.speak(getApplicationContext(), word.getText());
        }
    }
}
