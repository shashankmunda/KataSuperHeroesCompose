package com.karumi.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.karumi.R
import com.karumi.core.ui.LinkViewModelLifecycle
import com.karumi.core.ui.SuperHeroTopBar
import com.karumi.core.ui.ViewModelState
import com.karumi.domain.model.SuperHero

@Composable
fun SuperHeroDetailScreen(
    viewModel: SuperHeroDetailViewModel,
    onBackButtonTapped: () -> Unit
) {
    LinkViewModelLifecycle(viewModel)
    val state by viewModel.state.collectAsState(ViewModelState.Loading())
    when (val currentState = state) {
        is ViewModelState.Loaded -> SuperHeroDetailLoadedScreen(
            state = currentState.content,
            onBackButtonTapped = onBackButtonTapped
        )
        else -> SuperHeroDetailLoadingScreen()
    }
}

@Composable
private fun SuperHeroDetailLoadedScreen(
    state: SuperHeroDetailState,
    onBackButtonTapped: () -> Unit
) {
    SuperHeroDetailContent(
        superHero = state.superHero,
        onBackButtonTapped = onBackButtonTapped
    )
}

@Composable
private fun SuperHeroDetailLoadingScreen() {
    SuperHeroDetailLoadingContent()
}

@Composable
fun SuperHeroDetailContent(
    superHero: SuperHero,
    onBackButtonTapped: () -> Unit
) {
    // Implementation of the detail content goes here.
    // This could include displaying the super hero's name, image, description, etc.
    // For example:
    Scaffold(
        topBar = {
            SuperHeroTopBar(
                title = superHero.name,
                onBackButtonTapped = onBackButtonTapped
            )
        },
        content = {
            SuperHeroDetailContentBody(superHero)
        }
    )

}

@Composable
fun SuperHeroDetailContentBody(superHero: SuperHero) {
    Column {
        AsyncImage(
            model = superHero.photo,
            contentDescription = superHero.name,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("super_hero_detail_image"),
            contentScale = ContentScale.FillWidth,
            error = painterResource(id = R.drawable.baseline_broken_image_24)
        )
        Text(
            text = superHero.name,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = superHero.description,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .testTag("super_hero_detail_description")
        )
    }
}

@Composable
fun SuperHeroDetailLoadingContent() {
    // Implementation of the loading content goes here.
    // This could include a progress indicator or a placeholder.
    // For example:
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
