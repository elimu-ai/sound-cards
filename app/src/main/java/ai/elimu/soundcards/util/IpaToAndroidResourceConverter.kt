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
     *
     * Other X-SAMPA symbols which are not allowed in a file name are spelled out:
     *
     * ':' becomes '_long', '\' becomes '_bs', '`' becomes '_rfx', '@' becomes 'schwa',
     * '~' becomes 'nasal', and '*' becomes 'silent'
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
        } else if ("bʱ" == ipaValue) {
            // X-SAMPA: 'b_h\'
            resourceName = "b_h_bs"
        } else if ("pʰ" == ipaValue) {
            // X-SAMPA: 'p_h'
            resourceName = "p_h"
        } else if ("ʋ" == ipaValue) {
            // X-SAMPA: 'v\'
            resourceName = "v_bs"
        } else if ("tʰ" == ipaValue) {
            // X-SAMPA: 't_h'
            resourceName = "t_h"
        } else if ("dʱ" == ipaValue) {
            // X-SAMPA: 'd_h\'
            resourceName = "d_h_bs"
        } else if ("ɾ" == ipaValue) {
            // X-SAMPA: '4'
            resourceName = "4"
        } else if ("ʈ" == ipaValue) {
            // X-SAMPA: 't`'
            resourceName = "t_rfx"
        } else if ("ʈʰ" == ipaValue) {
            // X-SAMPA: 't`_h'
            resourceName = "t_rfx_h"
        } else if ("ɖ" == ipaValue) {
            // X-SAMPA: 'd`'
            resourceName = "d_rfx"
        } else if ("ɖʱ" == ipaValue) {
            // X-SAMPA: 'd`_h\'
            resourceName = "d_rfx_h_bs"
        } else if ("ɽ" == ipaValue) {
            // X-SAMPA: 'r`'
            resourceName = "r_rfx"
        } else if ("ʂ" == ipaValue) {
            // X-SAMPA: 's`'
            resourceName = "s_rfx"
        } else if ("ɳ" == ipaValue) {
            // X-SAMPA: 'n`'
            resourceName = "n_rfx"
        } else if ("lə" == ipaValue) {
            // X-SAMPA: 'l`'
            resourceName = "l_rfx"
        } else if ("zə" == ipaValue) {
            // X-SAMPA: 'z`'
            resourceName = "z_rfx"
        } else if ("ʃ" == ipaValue) {
            // X-SAMPA: 'S'
            resourceName = "s2"
        } else if ("ʃʰ" == ipaValue) {
            // X-SAMPA: 'S_h'
            resourceName = "s2_h"
        } else if ("ʒ" == ipaValue) {
            // X-SAMPA: 'Z'
            resourceName = "z2"
        } else if ("kʰ" == ipaValue) {
            // X-SAMPA: 'k_h'
            resourceName = "k_h"
        } else if ("ɡʱ" == ipaValue) {
            // X-SAMPA: 'g_h\'
            resourceName = "g_h_bs"
        } else if ("x" == ipaValue) {
            // X-SAMPA: 'x'
        } else if ("ɣ" == ipaValue) {
            // X-SAMPA: 'G'
            resourceName = "g2"
        } else if ("ɦ" == ipaValue) {
            // X-SAMPA: 'h\'
            resourceName = "h_bs"
        } else if ("j" == ipaValue) {
            // X-SAMPA: 'j'
        } else if ("iː" == ipaValue) {
            // X-SAMPA: 'i:'
            resourceName = "i_long"
        } else if ("ɪ" == ipaValue) {
            // X-SAMPA: 'I'
            resourceName = "i2"
        } else if ("eː" == ipaValue) {
            // X-SAMPA: 'e:'
            resourceName = "e_long"
        } else if ("ɛː" == ipaValue) {
            // X-SAMPA: 'E:'
            resourceName = "e2_long"
        } else if ("aː" == ipaValue) {
            // X-SAMPA: 'a:'
            resourceName = "a_long"
        } else if ("ə" == ipaValue) {
            // X-SAMPA: '@'
            resourceName = "schwa"
        } else if ("ɔː" == ipaValue) {
            // X-SAMPA: 'O:'
            resourceName = "o2_long"
        } else if ("oː" == ipaValue) {
            // X-SAMPA: 'o:'
            resourceName = "o_long"
        } else if ("uː" == ipaValue) {
            // X-SAMPA: 'u:'
            resourceName = "u_long"
        } else if ("ʊ" == ipaValue) {
            // X-SAMPA: 'U'
            resourceName = "u2"
        } else if ("\u25cc\u0303" == ipaValue) {
            // X-SAMPA: '~'
            resourceName = "nasal"
        } else if ("\u2205" == ipaValue) {
            // X-SAMPA: '*'
            resourceName = "silent"
        } else if ("ɡ" == ipaValue) {
            // X-SAMPA: 'g'
            resourceName = "g"
        }

        return resourceName
    }
}
