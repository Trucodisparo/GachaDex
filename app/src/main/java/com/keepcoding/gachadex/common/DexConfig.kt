package com.keepcoding.gachadex.common

sealed class DexConfig(val region: String, val first: Int, val last: Int, val name: String, val code: Int){

    companion object{
        fun getDexByRegion(region: String) : DexConfig {
            return when (region) {
                "national" -> NatDex
                "kanto" -> KantoDex
                "johto" -> JohtoDex
                "hoenn" -> HoennDex
                "sinnoh" -> SinnohDex
                "unova" -> UnovaDex
                "alola" -> AlolaDex
                "galar" -> GalarDex
                "hisui" -> HisuiDex
                "paldea" -> PaldeaDex
                else -> NatDex
            }
        }

        val regions = listOf(
            "national",
            "kanto",
            "johto",
            "hoenn",
            "sinnoh",
            "unova",
            "alola",
            "galar",
            "hisui",
            "paldea"
        )
    }

    object NatDex : DexConfig(
        region = "national",
        first = 1,
        last = 1010,
        name = "national",
        code = 1
    )

    object KantoDex : DexConfig(
        region = "kanto",
        first = 1,
        last = 151,
        name = "kanto",
        code = 2
    )

    object JohtoDex : DexConfig(
        region = "johto",
        first = 152,
        last = 251,
        name = "updated-johto",
        code = 7
    )

    object HoennDex : DexConfig(
        region = "hoenn",
        first = 252,
        last = 386,
        name = "updated-hoenn",
        code = 15
    )

    object SinnohDex : DexConfig(
        region = "sinnoh",
        first = 387,
        last = 493,
        name = "extended-sinnoh",
        code = 6
    )

    object UnovaDex : DexConfig(
        region = "unova",
        first = 494,
        last = 649,
        name = "updated-unova",
        code = 9
    )

    object AlolaDex : DexConfig(
        region = "alola",
        first = 645,
        last = 809,
        name = "updated-alola",
        code = 21
    )

    object GalarDex : DexConfig(
        region = "galar",
        first = 810,
        last = 898,
        name = "galar",
        code = 27
    )

    object HisuiDex : DexConfig(
        region = "hisui",
        first = 899,
        last = 905,
        name = "hisui",
        code = 30
    )

    object PaldeaDex : DexConfig(
        region = "paldea",
        first = 96,
        last = 1010,
        name = "paldea",
        code = 31
    )
}
