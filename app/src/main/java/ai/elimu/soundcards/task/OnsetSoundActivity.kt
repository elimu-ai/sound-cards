package ai.elimu.soundcards.task

import ai.elimu.model.v2.gson.content.WordGson
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
    private val wordsWithImage: MutableList<WordGson?>? = null

    private var wordsCorrectlySelected: MutableList<WordGson?>? = null

    private var emojiImageView: ImageView? = null

    private var progressBar: ProgressBar? = null

    private var alt1CardView: CardView? = null
    private var alt1ImageView: ImageView? = null

    private var alt2CardView: CardView? = null
    private var alt2ImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(javaClass.getName(), "onCreate")
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
        // TODO

        // Fetch 10 most frequent words with a corresponding image
        // TODO
    }

    override fun onStart() {
        Log.i(javaClass.getName(), "onStart")
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
        Log.i(javaClass.getName(), "loadNextTask")

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
        Log.i(javaClass.getName(), "playSound")

        // Look up corresponding Audio
        Log.d(javaClass.getName(), "Looking up \"sound_" + ipaValue + "\"")
        // TODO
    }

    private fun playWord(word: WordGson) {
        Log.i(javaClass.getName(), "playWord")

        // Look up corresponding Audio recording
        Log.d(javaClass.getName(), "Looking up \"" + word.text + "\"")
        // TODO
    }
}
