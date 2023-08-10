package com.keepcoding.gachadex.domain.model

import com.keepcoding.gachadex.common.DexConfig

data class PokedexStatusModel(
    val current_dex: DexConfig = DexConfig.NatDex,
    val last_unlocked: DexConfig = DexConfig.NatDex,
)