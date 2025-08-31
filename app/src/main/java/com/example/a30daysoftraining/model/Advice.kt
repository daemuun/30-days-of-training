package com.example.a30daysoftraining.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Advice(
    @DrawableRes val imageId: Int,
    @StringRes val descriptionId: Int,
    @StringRes val titleId: Int,
    val dayNumber: Int
)
