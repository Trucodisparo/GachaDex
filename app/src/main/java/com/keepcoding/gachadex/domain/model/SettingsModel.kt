package com.keepcoding.gachadex.domain.model

import com.keepcoding.gachadex.common.DexConfig

data class SettingsModel(
    val current_dex: DexConfig,
    val last_unlocked: DexConfig,
)