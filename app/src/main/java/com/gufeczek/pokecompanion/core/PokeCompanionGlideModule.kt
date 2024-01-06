package com.gufeczek.pokecompanion.core

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class PokeCompanionGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDefaultRequestOptions(
            RequestOptions().format(DecodeFormat.PREFER_RGB_565)
        )
    }

    companion object {
        const val IMAGE_PROVIDER_BASE_URL =
            "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/thumbnails-compressed"
    }
}