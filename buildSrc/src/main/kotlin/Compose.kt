object Compose {

    const val composeVersion = "1.1.0-rc01"
    const val runtime = "androidx.compose.runtime:runtime:${composeVersion}"
    const val runtimeLiveData = "androidx.compose.runtime:runtime-livedata:${composeVersion}"
    const val ui = "androidx.compose.ui:ui:${composeVersion}"
    const val material = "androidx.compose.material:material:${composeVersion}"
    const val uiTooling = "androidx.compose.ui:ui-tooling:${composeVersion}"
    const val foundation = "androidx.compose.foundation:foundation:${composeVersion}"
    const val compiler = "androidx.compose.compiler:compiler:${composeVersion}"

    private const val constraintLayoutComposeVersion = "1.0.0-rc02"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:${constraintLayoutComposeVersion}"

    private const val composeActivitiesVersion = "1.4.0"
    const val activity = "androidx.activity:activity-compose:${composeActivitiesVersion}"

    private const val composeNavigationVersion = "2.4.0-rc01"
    const val navigationCompose = "androidx.navigation:navigation-compose:${composeNavigationVersion}"
    const val navigationCommon = "androidx.navigation:navigation-common:${composeNavigationVersion}"
}