package com.egorshustov.food2forkkmm.presentation.model

class GenericMessageInfo private constructor(builder: Builder) {

    // required
    val id: String
    val title: String
    val uiComponentType: UIComponentType

    // optional
    val onDismiss: (() -> Unit)?
    val description: String?
    val positiveAction: PositiveAction?
    val negativeAction: NegativeAction?

    init {
        val builderId = builder.id
        val builderTitle = builder.title
        val builderUiComponentType = builder.uiComponentType

        if (builderId == null) throw Exception("GenericDialog id cannot be null.")
        if (builderTitle == null) throw Exception("GenericDialog title cannot be null.")
        if (builderUiComponentType == null) throw Exception("GenericDialog uiComponentType cannot be null.")

        id = builderId
        title = builderTitle
        uiComponentType = builderUiComponentType
        onDismiss = builder.onDismiss
        description = builder.description
        positiveAction = builder.positiveAction
        negativeAction = builder.negativeAction
    }

    class Builder {

        var id: String? = null
            private set

        var title: String? = null
            private set

        var onDismiss: (() -> Unit)? = null
            private set

        var uiComponentType: UIComponentType? = null
            private set

        var description: String? = null
            private set

        var positiveAction: PositiveAction? = null
            private set

        var negativeAction: NegativeAction? = null
            private set

        fun id(id: String): Builder {
            this.id = id
            return this
        }

        fun title(title: String): Builder {
            this.title = title
            return this
        }

        fun onDismiss(onDismiss: () -> Unit): Builder {
            this.onDismiss = onDismiss
            return this
        }

        fun uiComponentType(uiComponentType: UIComponentType): Builder {
            this.uiComponentType = uiComponentType
            return this
        }

        fun description(description: String): Builder {
            this.description = description
            return this
        }

        fun positive(positiveAction: PositiveAction?): Builder {
            this.positiveAction = positiveAction
            return this
        }

        fun negative(negativeAction: NegativeAction): Builder {
            this.negativeAction = negativeAction
            return this
        }

        fun build() = GenericMessageInfo(this)
    }
}

data class PositiveAction(
    val positiveBtnTxt: String,
    val onPositiveAction: () -> Unit,
)

data class NegativeAction(
    val negativeBtnTxt: String,
    val onNegativeAction: () -> Unit,
)