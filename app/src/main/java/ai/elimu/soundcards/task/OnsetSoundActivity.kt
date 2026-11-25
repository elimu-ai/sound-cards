package ai.elimu.soundcards.task

import ai.elimu.content_provider.utils.ContentProviderUtil
import ai.elimu.content_provider.utils.converter.CursorToLetterSoundGsonConverter
import ai.elimu.model.v2.gson.content.ImageGson
import ai.elimu.model.v2.gson.content.LetterSoundGson
import ai.elimu.model.v2.gson.content.WordGson
import ai.elimu.soundcards.BuildConfig
import ai.elimu.soundcards.R
import ai.elimu.soundcards.util.IpaToAndroidResourceConverter
import ai.elimu.soundcards.util.MediaPlayerHelper
import ai.elimu.soundcards.util.TtsHelper
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import java.util.Locale

/**
 * Initial sound identification: the student hears a sound and has to pick, out of two picture cards,
 * the one whose word starts with that sound.
 */
class OnsetSoundActivity : AppCompatActivity() {

    private val TAG = "OnsetSoundActivity"

    /**
     * The (max 10) most frequent words that start with an unlocked letter-sound correspondence
     * and have a corresponding image. Populated in [onCreate].
     */
    private var wordsWithImage: MutableList<WordGson> = mutableListOf()

    /**
     * The first letter-sound of each word in [wordsWithImage], i.e. the letter-sound
     * representing the onset sound of the word. The key is the word ID.
     */
    private val onsetLetterSoundByWordId: MutableMap<Long, LetterSoundGson> = mutableMapOf()

    private var wordsCorrectlySelected: MutableList<WordGson?>? = null

    private var emojiImageView: ImageView? = null

    private var progressBar: ProgressBar? = null

    private var alt1CardView: CardView? = null
    private var alt1ImageView: ImageView? = null

    private var alt2CardView: CardView? = null
    private var alt2ImageView: ImageView? = null

    /**
     * Used for scheduling the sequence of events of a task (sounds, animations, enabling of clicks).
     * All pending callbacks are removed when the activity stops.
     */
    private val handler = Handler(Looper.getMainLooper())

    private var headAnimator: ObjectAnimator? = null

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

        val allWordsByUsageCount = ContentProviderUtil.getAllWordGsons(
            applicationContext,
            BuildConfig.CONTENT_PROVIDER_APPLICATION_ID
        ).sortedByDescending { word ->
            word.usageCount ?: 0
        }
        Log.i(TAG, "allWordsByUsageCount.size: ${allWordsByUsageCount.size}")

        // Pick the most frequent words that start with an unlocked letter-sound and have been
        // used as an image label. Stop as soon as enough words have been found.
        for (word in allWordsByUsageCount) {
            val wordId = word.id ?: continue

            // The onset sound of a word is the first letter-sound of its letter-sound sequence
            val onsetLetterSound = getOnsetLetterSound(wordId) ?: continue
            if (onsetLetterSound.id !in unlockedLetterSoundIds) {
                continue
            }

            val imageGsons = ContentProviderUtil.getImageGsons(
                wordId,
                applicationContext,
                BuildConfig.CONTENT_PROVIDER_APPLICATION_ID
            )
            if (imageGsons.isEmpty()) {
                continue
            }

            Log.i(TAG, "Adding \"${word.text}\"...")
            wordsWithImage.add(word)
            onsetLetterSoundByWordId[wordId] = onsetLetterSound
            if (wordsWithImage.size == MAX_WORDS) {
                break
            }
        }
        Log.i(TAG, "wordsWithImage.size: ${wordsWithImage.size}")
    }

    override fun onStart() {
        Log.i(TAG, "onStart")
        super.onStart()

        // Each task needs two different words to choose between
        if (wordsWithImage.size < 2) {
            Log.w(TAG, "Not enough words with images to create a task")
            Toast.makeText(applicationContext, "Not enough words with images", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Animate subtle head movements
        headAnimator = ObjectAnimator.ofFloat(emojiImageView, "rotation", 2f).apply {
            setDuration(1000)
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }

        loadNextTask()
    }

    override fun onStop() {
        Log.i(TAG, "onStop")
        super.onStop()

        // Cancel the pending events of the current task. It will be restarted in onStart().
        handler.removeCallbacksAndMessages(null)
        headAnimator?.cancel()
    }

    private fun loadNextTask() {
        Log.i(TAG, "loadNextTask")

        val alt1CardView = alt1CardView!!
        val alt1ImageView = alt1ImageView!!
        val alt2CardView = alt2CardView!!
        val alt2ImageView = alt2ImageView!!

        // Prevent clicks until the sound and both words have been presented
        alt1CardView.setOnClickListener(null)
        alt2CardView.setOnClickListener(null)

        if (wordsCorrectlySelected!!.size == wordsWithImage.size) {
            // TODO: show congratulations page
            finish()
            return
        }

        val progress = (wordsCorrectlySelected!!.size * 100) / wordsWithImage.size
        ObjectAnimator.ofInt(progressBar, "progress", progress).apply {
            setDuration(1000)
            start()
        }

        // The word whose onset sound will be played
        val targetWord = wordsWithImage[wordsCorrectlySelected!!.size]
        Log.i(TAG, "targetWord.text: ${targetWord.text}")
        val targetOnsetLetterSound = onsetLetterSoundByWordId[targetWord.id]

        // The distractor, preferably a word with a different onset sound
        val otherWords = wordsWithImage.filter { it != targetWord }
        val wordsWithDifferentOnsetSound = otherWords.filter {
            onsetLetterSoundByWordId[it.id]?.id != targetOnsetLetterSound?.id
        }
        val distractorWord = (wordsWithDifferentOnsetSound.ifEmpty { otherWords }).random()
        Log.i(TAG, "distractorWord.text: ${distractorWord.text}")

        val targetCard = createCard(targetWord)
        val distractorCard = createCard(distractorWord)

        // Randomize which of the two cards is the correct one
        val isAlt1Correct = Math.random() > .5
        val alt1Card = if (isAlt1Correct) targetCard else distractorCard
        val alt2Card = if (isAlt1Correct) distractorCard else targetCard

        alt1CardView.setCardBackgroundColor(alt1Card.backgroundColor)
        alt1ImageView.setImageBitmap(alt1Card.bitmap)
        alt2CardView.setCardBackgroundColor(alt2Card.backgroundColor)
        alt2ImageView.setImageBitmap(alt2Card.bitmap)

        alt1CardView.visibility = View.VISIBLE
        alt2CardView.visibility = View.VISIBLE
        val swingAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.anim_swing_up_left)
        alt1CardView.startAnimation(swingAnimation)
        alt2CardView.startAnimation(swingAnimation)
        MediaPlayerHelper.play(applicationContext, R.raw.shuffle_cards)

        val correctCardView = if (isAlt1Correct) alt1CardView else alt2CardView
        val correctCard = if (isAlt1Correct) alt1Card else alt2Card
        val incorrectCardView = if (isAlt1Correct) alt2CardView else alt1CardView
        val firstWordToPlay = if (isAlt1Correct) alt1Card else alt2Card
        val secondWordToPlay = if (isAlt1Correct) alt2Card else alt1Card

        // The onset sound, e.g. "k" for the word "cat"
        val onsetIpa = targetOnsetLetterSound?.sounds
            ?.joinToString(separator = "") { it.valueIpa.orEmpty() }
            .orEmpty()
        Log.i(TAG, "onsetIpa: /$onsetIpa/")
        val androidResourceName = IpaToAndroidResourceConverter.getAndroidResourceName(onsetIpa)
        Log.i(TAG, "androidResourceName: $androidResourceName")

        var pauseBeforePlayingSound = 2500L
        if ("sw" == Locale.getDefault().language) {
            pauseBeforePlayingSound = 5000L
        }

        // 1. "Which word begins with this sound?"
        handler.postDelayed({
            MediaPlayerHelper.play(applicationContext, R.raw.which_word_begins_with_this_sound)

            // 2. Play the sound, and animate the mouth of the emoji
            handler.postDelayed({
                playSound(onsetIpa)
                animateMouth(androidResourceName)

                // 3. Play the first word
                handler.postDelayed({
                    playWord(firstWordToPlay.word)
                    pulseCard(if (firstWordToPlay === alt1Card) alt1CardView else alt2CardView)

                    // 4. Play the second word, and enable selection
                    handler.postDelayed({
                        playWord(secondWordToPlay.word)
                        pulseCard(if (secondWordToPlay === alt1Card) alt1CardView else alt2CardView)

                        correctCardView.setOnClickListener {
                            Log.i(TAG, "correct card onClick")
                            onCorrectCardSelected(
                                correctCardView,
                                incorrectCardView,
                                correctCard
                            )
                        }
                        incorrectCardView.setOnClickListener {
                            Log.i(TAG, "incorrect card onClick")
                            MediaPlayerHelper.play(applicationContext, R.raw.alternative_incorrect)
                            incorrectCardView.startAnimation(
                                AnimationUtils.loadAnimation(applicationContext, R.anim.anim_shake)
                            )
                        }
                    }, 2000)
                }, 2000)
            }, pauseBeforePlayingSound)
        }, 1000)
    }

    private fun onCorrectCardSelected(
        correctCardView: CardView,
        incorrectCardView: CardView,
        correctCard: Card
    ) {
        Log.i(TAG, "onCorrectCardSelected")

        MediaPlayerHelper.play(applicationContext, R.raw.alternative_correct)
        correctCardView.setCardBackgroundColor(withAlpha(correctCard.backgroundColor, 100))

        wordsCorrectlySelected!!.add(correctCard.word)

        // Prevent further clicks
        correctCardView.setOnClickListener(null)
        incorrectCardView.setOnClickListener(null)

        handler.postDelayed({
            val disappearAnimation =
                AnimationUtils.loadAnimation(applicationContext, R.anim.anim_disappear_left)
            alt1CardView?.startAnimation(disappearAnimation)
            alt2CardView?.startAnimation(disappearAnimation)
            MediaPlayerHelper.play(applicationContext, R.raw.swipe_cards)

            // Wait for the cards to disappear before loading the next task
            handler.postDelayed({
                loadNextTask()
            }, disappearAnimation.duration)
        }, 2000)
    }

    /**
     * A picture card, showing one of the images that is labeled by [word].
     */
    private class Card(
        val word: WordGson,
        val bitmap: Bitmap?,
        val backgroundColor: Int
    )

    private fun createCard(word: WordGson): Card {
        val imageGsons = ContentProviderUtil.getImageGsons(
            word.id,
            applicationContext,
            BuildConfig.CONTENT_PROVIDER_APPLICATION_ID
        )
        val bitmap = imageGsons.randomOrNull()?.let { loadBitmap(it) }
        return Card(word, bitmap, getDominantColor(bitmap, 20))
    }

    /**
     * Loads the image file through the Content Provider application.
     */
    private fun loadBitmap(imageGson: ImageGson): Bitmap? {
        val imageUri =
            "content://${BuildConfig.CONTENT_PROVIDER_APPLICATION_ID}.provider.image_provider/images/${imageGson.id}".toUri()
        Log.i(TAG, "imageUri: $imageUri")
        return try {
            contentResolver.openInputStream(imageUri)?.use { BitmapFactory.decodeStream(it) }
        } catch (e: Exception) {
            Log.w(TAG, "Failed to load image ${imageGson.id}", e)
            null
        }
    }

    /**
     * Returns the average color of the (non-transparent) pixels of the [bitmap], with the given
     * [alpha] applied.
     */
    private fun getDominantColor(bitmap: Bitmap?, alpha: Int): Int {
        if (bitmap == null) {
            return Color.argb(alpha, 103, 58, 183)
        }

        val sampleSize = 16
        val sample = Bitmap.createScaledBitmap(bitmap, sampleSize, sampleSize, true)
        var red = 0L
        var green = 0L
        var blue = 0L
        var count = 0L
        for (x in 0 until sampleSize) {
            for (y in 0 until sampleSize) {
                val pixel = sample.getPixel(x, y)
                if (Color.alpha(pixel) < 128) {
                    continue
                }
                red += Color.red(pixel)
                green += Color.green(pixel)
                blue += Color.blue(pixel)
                count++
            }
        }
        if (count == 0L) {
            return Color.argb(alpha, 103, 58, 183)
        }
        return Color.argb(alpha, (red / count).toInt(), (green / count).toInt(), (blue / count).toInt())
    }

    private fun withAlpha(color: Int, alpha: Int): Int {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color))
    }

    /**
     * Returns the first letter-sound of the given word, or `null` if it has none.
     */
    private fun getOnsetLetterSound(wordId: Long): LetterSoundGson? {
        val letterSoundsUri =
            "content://${BuildConfig.CONTENT_PROVIDER_APPLICATION_ID}.provider.letter_sound_provider/letter_sounds/by-word-id/$wordId".toUri()
        val cursor = contentResolver.query(letterSoundsUri, null, null, null, null) ?: return null
        cursor.use {
            // The letter-sounds are ordered by their position in the word
            if (it.moveToFirst()) {
                return CursorToLetterSoundGsonConverter.getLetterSoundGson(
                    it,
                    applicationContext,
                    BuildConfig.CONTENT_PROVIDER_APPLICATION_ID
                )
            }
        }
        return null
    }

    private fun animateMouth(androidResourceName: String?) {
        Log.i(TAG, "Looking up resource: animated_emoji_u1f603_mouth_$androidResourceName")
        val drawableResourceId = resources.getIdentifier(
            "animated_emoji_u1f603_mouth_$androidResourceName",
            "drawable",
            packageName
        )
        if (drawableResourceId == 0) {
            // No mouth animation exists for this sound
            return
        }
        emojiImageView?.setImageDrawable(getDrawable(drawableResourceId))
        (emojiImageView?.drawable as? AnimatedVectorDrawable)?.start()
    }

    private fun pulseCard(cardView: CardView) {
        ObjectAnimator.ofFloat(
            cardView,
            "cardElevation",
            cardView.cardElevation,
            cardView.cardElevation + 10,
            cardView.cardElevation
        ).apply {
            setDuration(1000)
            start()
        }
    }

    private fun playSound(ipaValue: String?) {
        Log.i(TAG, "playSound")

        // Look up corresponding Audio
        val audioFileName = "sound_${IpaToAndroidResourceConverter.getAndroidResourceName(ipaValue)}"
        Log.d(TAG, "Looking up \"$audioFileName\"")
        val resourceId = resources.getIdentifier(audioFileName, "raw", packageName)
        if (resourceId != 0) {
            MediaPlayerHelper.play(applicationContext, resourceId)
        } else {
            // Audio not found. Fall back to TTS.
            TtsHelper.speak(applicationContext, ipaValue)
        }
    }

    private fun playWord(word: WordGson) {
        Log.i(TAG, "playWord")

        // Look up corresponding Audio recording
        Log.d(TAG, "Looking up \"" + word.text + "\"")
        // TODO: Play the audio recording of the word once the Content Provider exposes it.
        //  Until then, fall back to TTS.
        TtsHelper.speak(applicationContext, word.text)
    }

    companion object {
        /**
         * The number of tasks (words) in one session.
         */
        private const val MAX_WORDS = 10
    }
}
