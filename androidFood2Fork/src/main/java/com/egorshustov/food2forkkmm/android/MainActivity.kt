package com.egorshustov.food2forkkmm.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.egorshustov.food2forkkmm.android.presentation.navigation.Navigation
import com.egorshustov.food2forkkmm.android.util.BASE_URL
import com.egorshustov.food2forkkmm.android.util.TOKEN
import com.egorshustov.food2forkkmm.datasource.network.KtorClientFactory
import com.egorshustov.food2forkkmm.datasource.network.model.RecipeDto
import com.egorshustov.food2forkkmm.datasource.network.toRecipe
import com.egorshustov.food2forkkmm.domian.util.DateTimeUtil
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ktorClient = KtorClientFactory().build()
        CoroutineScope(Dispatchers.IO).launch {
            val recipeId = 1551
            val recipe = ktorClient.get<RecipeDto> {
                url("$BASE_URL/get?id=$recipeId")
                header("Authorization", TOKEN)
            }.toRecipe()
            println("KtorTest: ${recipe.title}")
            println("KtorTest: ${recipe.ingredients}")
            println("KtorTest: ${recipe.dateUpdated}")
            println("KtorTest: ${DateTimeUtil.humanizeDatetime(recipe.dateUpdated)}")
        }

        setContent {
            Navigation()
        }
    }
}
