package ai.elimu.soundcards.task

import ai.elimu.content_provider.utils.ContentProviderUtil
import ai.elimu.model.v2.gson.content.WordGson
import ai.elimu.soundcards.BuildConfig
import ai.elimu.soundcards.R
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.util.regex.Pattern

class OnsetSoundActivity : AppCompatActivity() {
    
    private val TAG = "OnsetSoundActivity"

    /**
     * The (max 10) most frequent words that start with an unlocked letter-sound correspondence
     * and have a corresponding image. Populated in [onCreate].
     */
    private var wordsWithImage: MutableList<WordGson> = mutableListOf()

    private var wordsCorrectlySelected: MutableList<WordGson?>? = null

    private var emojiImageView: ImageView? = null

    private var progressBar: ProgressBar? = null

    private var alt1CardView: CardView? = null
    private var alt1ImageView: ImageView? = null

    private var alt2CardView: CardView? = null
    private var alt2ImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_onset_sound)

        wordsCorrectlySelected = ArrayList<WordGson?>()

        emojiImageView = findViewById<View?>(R.id.emoji) as ImageView?

        progressBar = findViewById<View?>(R.id.progressBar) as ProgressBar?

        alt1CardView = findViewById<View?>(R.id.alt1CardView) as CardView?
        alt1ImageView = findViewById<View?>(R.id.alt1ImageView) as ImageView?

        alt2CardView = findViewById<View?>(R.id.alt2CardView) as CardView?
        alt2ImageView = findViewById<View?>(R.id.alt2ImageView) as ImageView?

        // Fetch words starting with one of the unlocked letter-sound correspondences
        // TODO: Only include the letter-sounds that the student has unlocked (see https://github.com/elimu-ai/herufi/issues/6).
        //  Until the unlocking mechanism exists, all available letter-sounds are treated as unlocked.
        val unlockedLetterSoundIds = ContentProviderUtil.getAllLetterSoundGsons(
            applicationContext,
            BuildConfig.CONTENT_PROVIDER_APPLICATION_ID
        ).mapNotNull { it.id }.toSet()
        Log.i(TAG, "unlockedLetterSoundIds.size: ${unlockedLetterSoundIds.size}")

        val wordsStartingWithUnlockedLetterSound = ContentProviderUtil.getAllWordGsons(
            applicationContext,
            BuildConfig.CONTENT_PROVIDER_APPLICATION_ID
        )/*.filter { word ->
            // The onset sound of a word is the first letter-sound of its letter-sound sequence
            val onsetLetterSoundId = word.letterSounds?.firstOrNull()?.id
            (onsetLetterSoundId != null) && (onsetLetterSoundId in unlockedLetterSoundIds)
        }.sortedByDescending { word ->
            word.usageCount ?: 0
        }*/
        Log.i(TAG, "wordsStartingWithUnlockedLetterSound.size: ${wordsStartingWithUnlockedLetterSound.size}")

        // Fetch 10 most frequent words with a corresponding image
        val idsOfWordsWithImage = ContentProviderUtil.getAllImageGsons(
            applicationContext,
            BuildConfig.CONTENT_PROVIDER_APPLICATION_ID
        )/*.mapNotNull { image -> image.word?.id }.toSet()
        wordsWithImage = wordsStartingWithUnlockedLetterSound
            .filter { word -> (word.id != null) && (word.id in idsOfWordsWithImage) }
            .take(10)
            .toMutableList()*/
        for (word in wordsWithImage) {
            Log.i(TAG, "word.text: \"${word.text}\", word.usageCount: ${word.usageCount}")
        }
    }

    override fun onStart() {
        Log.i(TAG, "onStart")
        super.onStart()

        // Animate subtle head movements
        val objectAnimator = ObjectAnimator.ofFloat(emojiImageView, "rotation", 2f)
        objectAnimator.setDuration(1000)
        objectAnimator.repeatCount = ValueAnimator.INFINITE
        objectAnimator.repeatMode = ValueAnimator.REVERSE
        objectAnimator.start()

        loadNextTask()
    }

    private fun loadNextTask() {
        Log.i(TAG, "loadNextTask")

        // TODO
    }

    private fun parseRgbColor(input: String, alpha: Int): Int {
        val pattern = Pattern.compile("rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)")
        val matcher = pattern.matcher(input)
        if (matcher.matches()) {
            val rgbRed = matcher.group(1).toInt()
            val rgbGreen = matcher.group(2).toInt()
            val rgbBlue = matcher.group(3).toInt()
            val colorIdentifier = Color.argb(alpha, rgbRed, rgbGreen, rgbBlue)
            return colorIdentifier
        } else {
            return -1
        }
    }

    private fun playSound(ipaValue: String?) {
        Log.i(TAG, "playSound")

        // Look up corresponding Audio
        Log.d(TAG, "Looking up \"sound_$ipaValue\"")
        // TODO
    }

    private fun playWord(word: WordGson) {
        Log.i(TAG, "playWord")

        // Look up corresponding Audio recording
        Log.d(TAG, "Looking up \"" + word.text + "\"")
        // TODO
    }
}
