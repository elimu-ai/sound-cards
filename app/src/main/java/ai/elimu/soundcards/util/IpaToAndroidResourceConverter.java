package ai.elimu.soundcards.util;

/**
 * Helper class for converting IPA values into a format allowed in an Android file name: [a-z], [0-9], [_]
 */
public class IpaToAndroidResourceConverter {

    /**
     * The number 2 indicates that the X-SAMPA value is upper-case:
     *
     * E.g. 'a2' instead of 'A'
     * E.g. 'e2' instead of 'E'
     * E.g. 'e2i2' instead of 'EI'
     */
    public static String getAndroidResourceName(String ipaValue) {
        String resourceName = ipaValue;

        if ("ɛ".equals(ipaValue)) {
            // X-SAMPA: 'E'
            resourceName = "e2";
        } else if ("æ".equals(ipaValue)) {
            // X-SAMPA: '}'
            resourceName = "ae";
        } else if ("ɔ".equals(ipaValue)) {
            // X-SAMPA: 'O'
            resourceName = "o2";
        } else if ("t".equals(ipaValue)) {
            // X-SAMPA: 't'
        } else if ("h".equals(ipaValue)) {
            // X-SAMPA: 'h'
        } else if ("s".equals(ipaValue)) {
            // X-SAMPA: 's'
        } else if ("i".equals(ipaValue)) {
            // X-SAMPA: 'i'
        } else if ("n".equals(ipaValue)) {
            // X-SAMPA: 'n'
        } else if ("r".equals(ipaValue)) {
            // X-SAMPA: 'r'
        } else if ("d".equals(ipaValue)) {
            // X-SAMPA: 'd'
        } else if ("l".equals(ipaValue)) {
            // X-SAMPA: 'l'
        } else if ("y".equals(ipaValue)) {
            // X-SAMPA: 'j'
            resourceName = "j";
        } else if ("ʌ".equals(ipaValue)) {
            // X-SAMPA: 'V'
            resourceName = "v2";
        } else if ("w".equals(ipaValue)) {
            // X-SAMPA: 'w'
        } else if ("m".equals(ipaValue)) {
            // X-SAMPA: 'm'
        } else if ("g".equals(ipaValue)) {
            // X-SAMPA: 'g'
        } else if ("c".equals(ipaValue)) {
            // X-SAMPA: 'k'
        } else if ("b".equals(ipaValue)) {
            // X-SAMPA: 'b'
        } else if ("p".equals(ipaValue)) {
            // X-SAMPA: 'p'
        } else if ("f".equals(ipaValue)) {
            // X-SAMPA: 'f'
        } else if ("k".equals(ipaValue)) {
            // X-SAMPA: 'k'
        } else if ("v".equals(ipaValue)) {
            // X-SAMPA: 'v'
        } else if ("z".equals(ipaValue)) {
            // X-SAMPA: 'z'
        } else if ("dʒ".equals(ipaValue)) {
            // X-SAMPA: 'dZ'
            resourceName = "dz2";
        } else if ("ks".equals(ipaValue)) {
            // X-SAMPA: 'ks'
        } else if ("kw".equals(ipaValue)) {
            // X-SAMPA: 'kw'
        }

        return resourceName;
    }
}
