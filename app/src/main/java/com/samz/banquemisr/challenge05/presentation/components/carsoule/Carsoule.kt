package com.samz.banquemisr.challenge05.presentation.components.carsoule

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.samz.banquemisr.challenge05.R
import com.samz.banquemisr.challenge05.presentation.theme.MoviesAppTheme


@ExperimentalMaterial3Api
@Composable
fun HomeCarousel(items: List<CarouselDataItem>) {
    HorizontalUncontainedCarousel(
        state = rememberCarouselState(initialItem = 0, itemCount = { items.count() }),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .height(240.dp),
        itemWidth = 300.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp),

        ) { i ->
        val item = items[i]
        AsyncImage(
            model = item.imageUrl,
            error = painterResource(id = item.imagePlaceholderResId ?: R.drawable.img_placeholder),
            modifier = Modifier
                .fillMaxHeight()
                .maskClip(MaterialTheme.shapes.extraLarge),
            contentDescription = item.contentDescription,// stringResource(item.contentDescriptionResId),
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun previewCarsouel() {
    MoviesAppTheme {
        HomeCarousel(items)
    }
}

val items =
    listOf(
        CarouselDataItem(0, "", R.drawable.carousel_image_1, "carousel_image_1_description"),
        CarouselDataItem(1, "", R.drawable.carousel_image_2, "carousel_image_2_description"),
        CarouselDataItem(2, "", R.drawable.carousel_image_3, "carousel_image_3_description"),
        CarouselDataItem(3, "", R.drawable.carousel_image_4, "carousel_image_4_description"),
    )
