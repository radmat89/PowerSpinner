package com.skydoves.powerspinner

data class CustomSpinnerItem(
    override val spinnerText: CharSequence,
    override val spinnerValue: CharSequence
) : CustomSpinnerItemInterface