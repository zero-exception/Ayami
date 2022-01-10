package moe.kadosawa.ayami.genshin.enums

/**
 * Enumeration of playable Genshin Impact
 * characters as of version 2.4
 */
enum class GenshinCharacters(vararg val fullNames: String) {
    ALBEDO("Albedo"),
    ALOY("Aloy"),
    ARATAKI_ITTO("Arataki Itto", "Itto Arataki"),
    BARBARA("Barbara"),
    BEIDOU("Beidou"),
    BENNETT("Bennett"),
    CHONGYUN("Chongyun"),
    DILUC("Diluc"),
    DIONA("Diona"),
    EULA("Eula"),
    FISCHL("Fischl"),
    GANYU("Ganyu"),
    GOROU("Gorou"),
    HU_TAO("Hu Tao"),
    JEAN("Jean Gunnhildr", "Gunnhildr Jean"),
    KAEDEHARA_KAZUHA("Kaedehara Kazuha", "Kazuha Kaedehara"),
    KAEYA("Kaeya"),
    KAMISATO_AYAKA("Kamisato Ayaka", "Ayaka Kamisato"),
    KEQING("Keqing"),
    KLEE("Klee"),
    KUJOU_SARA("Kujou Sara", "Sara Kujou"),
    LISA("Lisa"),
    MONA("Mona"),
    NINGGUANG("Ningguang"),
    NOELLE("Noelle"),
    QIQI("Qiqi"),
    RAIDEN_SHOGUN("Ei", "Baal", "Raiden Shogun", "Shogun Raiden"),
    RAZOR("Razor"),
    ROSARIA("Rosaria"),
    SANGONOMIYA_KOKOMI("Sangonomiya Kokomi", "Kokomi Sangonomiya"),
    SAYU("Sayu"),
    SHENHE("Shenhe"),
    SUCROSE("Sucrose"),
    TARTAGLIA("Tartaglia"),
    THOMA("Thoma"),
    VENTI("Venti"),
    XIANGLING("Xiangling"),
    XIAO("Xiao"),
    XINGQIU("Xingqiu"),
    XINYAN("Xinyan"),
    YANFEI("Yanfei"),
    YOIMIYA("Yoimiya"),
    YUN_JIN("Yun Jin"),
    ZHONGLI("Zhongli");

    companion object {
        fun fromFullName(s: String) =
            values().find { c -> c.fullNames.any { name -> name == s } }
    }
}