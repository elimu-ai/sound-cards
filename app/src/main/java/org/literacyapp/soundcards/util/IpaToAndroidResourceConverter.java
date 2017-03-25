package org.literacyapp.soundcards.util;

/**
 * Helper class for converting IPA values into a format allowed in an Android file name: [a-z], [0-9], [_]
 */
public class IpaToAndroidResourceConverter {

    public static String getAndroidResourceName(String ipaValue) {
        String resourceName = ipaValue;

        // The number 2 indicates that the X-SAMPA value is upper-case (e.g. 'A' instead of 'a')
        if ("ɑ".equals(ipaValue)) {
            // X-SAMPA: '}'
            resourceName = "ae";
        } else if ("ɑ".equals(ipaValue)) {
            // X-SAMPA: 'A'
            resourceName = "a2";
        } else if ("ɛ".equals(ipaValue)) {
            // X-SAMPA: 'E'
            resourceName = "e2";
        }

        return resourceName;
    }
}
