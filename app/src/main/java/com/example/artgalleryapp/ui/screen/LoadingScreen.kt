package com.example.artgalleryapp.ui.screen

import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.example.bookshelfapp.R

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(dimensionResource(id = R.dimen.text_padding)),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}
