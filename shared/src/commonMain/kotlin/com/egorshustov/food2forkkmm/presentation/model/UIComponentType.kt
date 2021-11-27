package com.egorshustov.food2forkkmm.presentation.model

sealed interface UIComponentType {

    object Dialog : UIComponentType

    object None : UIComponentType
}