package com.tatara.actorsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.tatara.actorsapp.repository.CharacterRepo
import com.tatara.actorsapp.retrofit.Character
import com.tatara.actorsapp.retrofit.CharacterApi
import com.tatara.actorsapp.retrofit.RetrofitInstance
import com.tatara.actorsapp.ui.theme.ActorsAppTheme
import com.tatara.actorsapp.viewmodel.CharacterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActorsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val api = RetrofitInstance.provideApi(RetrofitInstance.provideRetrofit())
                    val repo = CharacterRepo(api)
                    val myModel = CharacterViewModel(repo)
                    MainScreen(myModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(model: CharacterViewModel) {
    val characters by model.state.collectAsState()
    val nonEmptyList = mutableListOf<Character>()
    characters.forEach {
        if (it.image != "") {
            nonEmptyList.add(it)
        }
    }
    ActorsList(charactersList = nonEmptyList)
}

@Composable
fun ActorsList(charactersList: List<Character>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(8.dp)
    ) {
        items(items = charactersList) { item ->
            CardItem(character = item)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CardItem(character: Character) {
    Column {
        GlideImage(
            model = character.image,
            contentDescription = "Character image",
            modifier = Modifier
                .padding(4.dp)
                .size(width = (140.dp), height = 180.dp)
        )
        Text(
            text = character.actor,
            fontSize = 20.sp
        )
    }
}