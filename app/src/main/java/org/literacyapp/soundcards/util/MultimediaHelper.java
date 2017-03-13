package org.literacyapp.soundcards.util;

import android.os.Environment;

import org.literacyapp.contentprovider.model.content.multimedia.Audio;
import org.literacyapp.contentprovider.model.content.multimedia.Image;

import java.io.File;

/**
 * Helper class for determining folder paths of multimedia content.
 */
public class MultimediaHelper {

    public static File getMultimediaDirectory() {
        File multimediaDirectory = new File(Environment.getExternalStorageDirectory() + "/.literacyapp/multimedia");
        if (!multimediaDirectory.exists()) {
            multimediaDirectory.mkdirs();
        }
        return multimediaDirectory;
    }

    public static File getAudioDirectory() {
        File audioDirectory = new File(getMultimediaDirectory(), "audio");
        if (!audioDirectory.exists()) {
            audioDirectory.mkdir();
        }
        return audioDirectory;
    }

    public static File getImageDirectory() {
        File imageDirectory = new File(getMultimediaDirectory(), "image");
        if (!imageDirectory.exists()) {
            imageDirectory.mkdir();
        }
        return imageDirectory;
    }

    public static File getFile(Audio audio) {
        File file = null;

        if (audio == null) {
            return null;
        }

        File audioDirectory = getAudioDirectory();
        file = new File(audioDirectory, audio.getId() + "_r" + audio.getRevisionNumber() + "." + audio.getAudioFormat().toString().toLowerCase());

        return file;
    }

    public static File getFile(Image image) {
        File file = null;

        if (image == null) {
            return null;
        }

        File imageDirectory = getImageDirectory();
        file = new File(imageDirectory, image.getId() + "_r" + image.getRevisionNumber() + "." + image.getImageFormat().toString().toLowerCase());

        return file;
    }
}
