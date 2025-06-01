package com.karumi.ui.list


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.karumi.R
import com.karumi.core.ui.LinkViewModelLifecycle
import com.karumi.core.ui.SuperHeroTopBar
import com.karumi.core.ui.ViewModelState
import com.karumi.domain.model.SuperHero

@Composable
fun SuperHeroListScreen(
    viewModel: SuperHeroListViewModel,
    onSuperHeroTapped: (SuperHero) -> Unit
) {
    LinkViewModelLifecycle(viewModel)
    val state by viewModel.state.collectAsState(ViewModelState.Loading())
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            SuperHeroTopBar(
                title = stringResource(id = R.string.super_heroes_screen_title)
            )
        },
        content = {
            when (val currentState = state) {
                is ViewModelState.Loaded ->
                    SuperHeroListLoadedScreen(
                        state = currentState.content,
                        onSuperHeroTapped = onSuperHeroTapped
                    )
                else -> SuperHeroListLoadingScreen()
            }
        }
    )
}

@Composable
private fun SuperHeroListLoadedScreen(
    state: SuperHeroListState,
    onSuperHeroTapped: (SuperHero) -> Unit
) = when {
    state.superHeroes.isEmpty() -> SuperHeroEmptyCase()
    else -> SuperHeroesList(state.superHeroes, onSuperHeroTapped)
}

@Composable
private fun SuperHeroesList(superHeroes: List<SuperHero>, onSuperHeroTapped: (SuperHero) -> Unit) {
    LazyColumn {
        items(superHeroes.size) { index ->
            val superHero = superHeroes[index]
            SuperHeroItem(
                superHero = superHero,
                onSuperHeroTapped = onSuperHeroTapped
            )
        }
    }
}

@Composable
fun SuperHeroItem(superHero: SuperHero, onSuperHeroTapped: (SuperHero) -> Unit) {
    Box{
        AsyncImage(
            model = superHero.photo!!,
            contentDescription = superHero.name,
            modifier = Modifier
                .clickable { onSuperHeroTapped(superHero) }
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            error = painterResource(id = R.drawable.baseline_broken_image_24)
        )
        Text(
            text = superHero.name,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
                .testTag("super_hero_name_${superHero.name}"),
            style = MaterialTheme.typography.body1,
            color = Color.White
        )
    }

}

@Composable
fun AvengerBadge(modifier: Modifier = Modifier, superHero: SuperHero) {
    AsyncImage(
        model = "https://toppng.com/uploads/preview/avengers-logo-11549475712t8kuavcmbp.png" ,
        contentDescription = superHero.name,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag("super_hero_badge_${superHero.name}"),
    )
}

@Composable
private fun SuperHeroEmptyCase() {
    Text(
        text = stringResource(id = R.string.super_heroes_empty_case),
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
private fun SuperHeroListLoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ){
        CircularProgressIndicator(
            strokeWidth = 4.dp,
            color = Color(0xFF3F51B5),
            modifier = Modifier
                .padding(16.dp)
                .testTag("super_hero_list_loading")
        )
    }

}

