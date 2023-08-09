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
        first = 1,
        last = 256,
        name = "updated-johto",
        code = 7
    )

    object HoennDex : DexConfig(
        region = "hoenn",
        first = 1,
        last = 211,
        name = "updated-hoenn",
        code = 15
    )

    object SinnohDex : DexConfig(
        region = "sinnoh",
        first = 1,
        last = 210,
        name = "extended-sinnoh",
        code = 6
    )

    object UnovaDex : DexConfig(
        region = "unova",
        first = 1,
        last = 300,
        name = "updated-unova",
        code = 9
    )

    object AlolaDex : DexConfig(
        region = "alola",
        first = 1,
        last = 403,
        name = "updated-alola",
        code = 21
    )

    object GalarDex : DexConfig(
        region = "galar",
        first = 1,
        last = 400,
        name = "galar",
        code = 27
    )

    object HisuiDex : DexConfig(
        region = "hisui",
        first = 1,
        last = 242,
        name = "hisui",
        code = 30
    )

    object PaldeaDex : DexConfig(
        region = "paldea",
        first = 1,
        last = 400,
        name = "paldea",
        code = 31
    )
}
