package ai.elimu.soundcards.util

/**
 * Helper class for converting IPA values into a format allowed in an Android file name: [a-z], [0-9], [_]
 */
object IpaToAndroidResourceConverter {
    /**
     * The number 2 indicates that the X-SAMPA value is upper-case:
     *
     * E.g. 'a2' instead of 'A'
     * E.g. 'e2' instead of 'E'
     * E.g. 'e2i2' instead of 'EI'
     */
    fun getAndroidResourceName(ipaValue: String?): String? {
        var resourceName = ipaValue

        if ("ɛ" == ipaValue) {
            // X-SAMPA: 'E'
            resourceName = "e2"
        } else if ("æ" == ipaValue) {
            // X-SAMPA: '}'
            resourceName = "ae"
        } else if ("ɔ" == ipaValue) {
            // X-SAMPA: 'O'
            resourceName = "o2"
        } else if ("t" == ipaValue) {
            // X-SAMPA: 't'
        } else if ("h" == ipaValue) {
            // X-SAMPA: 'h'
        } else if ("s" == ipaValue) {
            // X-SAMPA: 's'
        } else if ("i" == ipaValue) {
            // X-SAMPA: 'i'
        } else if ("n" == ipaValue) {
            // X-SAMPA: 'n'
        } else if ("r" == ipaValue) {
            // X-SAMPA: 'r'
        } else if ("d" == ipaValue) {
            // X-SAMPA: 'd'
        } else if ("l" == ipaValue) {
            // X-SAMPA: 'l'
        } else if ("y" == ipaValue) {
            // X-SAMPA: 'j'
            resourceName = "j"
        } else if ("ʌ" == ipaValue) {
            // X-SAMPA: 'V'
            resourceName = "v2"
        } else if ("w" == ipaValue) {
            // X-SAMPA: 'w'
        } else if ("m" == ipaValue) {
            // X-SAMPA: 'm'
        } else if ("g" == ipaValue) {
            // X-SAMPA: 'g'
        } else if ("c" == ipaValue) {
            // X-SAMPA: 'k'
        } else if ("b" == ipaValue) {
            // X-SAMPA: 'b'
        } else if ("p" == ipaValue) {
            // X-SAMPA: 'p'
        } else if ("f" == ipaValue) {
            // X-SAMPA: 'f'
        } else if ("k" == ipaValue) {
            // X-SAMPA: 'k'
        } else if ("v" == ipaValue) {
            // X-SAMPA: 'v'
        } else if ("z" == ipaValue) {
            // X-SAMPA: 'z'
        } else if ("dʒ" == ipaValue) {
            // X-SAMPA: 'dZ'
            resourceName = "dz2"
        } else if ("ks" == ipaValue) {
            // X-SAMPA: 'ks'
        } else if ("kw" == ipaValue) {
            // X-SAMPA: 'kw'
        }

        return resourceName
    }
}
