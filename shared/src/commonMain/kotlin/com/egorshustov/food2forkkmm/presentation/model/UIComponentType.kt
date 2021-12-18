package com.egorshustov.food2forkkmm.presentation.model

sealed class UIComponentType {

    object Dialog : UIComponentType()

    object None : UIComponentType()
}