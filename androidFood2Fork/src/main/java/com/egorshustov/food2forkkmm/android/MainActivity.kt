package com.egorshustov.food2forkkmm.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.egorshustov.food2forkkmm.android.presentation.navigation.Navigation
import com.egorshustov.food2forkkmm.android.util.BASE_URL
import com.egorshustov.food2forkkmm.android.util.TOKEN
import com.egorshustov.food2forkkmm.datasource.network.KtorClientFactory
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ktorClient = KtorClientFactory().build()
        CoroutineScope(Dispatchers.IO).launch {
            val recipeId = 1551
            val recipe = ktorClient.get<String> {
                url("$BASE_URL/get?id=$recipeId")
                header("Authorization", TOKEN)
            }
            println("KtorTest: $recipe")
        }

        setContent {
            Navigation()
        }
    }
}
