package com.example.a30daysoftraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.a30daysoftraining.data.Datasource
import com.example.a30daysoftraining.model.Advice
import com.example.a30daysoftraining.ui.theme._30DaysOfTrainingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _30DaysOfTrainingTheme {
                AdviceApp()
            }
        }
    }
}

@Composable
fun AdviceApp() {
    val advices = Datasource.getAdviceList()

    Scaffold(
        topBar = { AdvicesTopAppBar() },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            items(advices) {
                AdviceCard(
                    advice = it,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvicesTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium
            )
        },
        modifier = modifier
    )
}

@Composable
fun AdviceCard(advice: Advice, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
        onClick = { expanded = !expanded }
    ) {
        Column {
            Row {
                AdviceImage(
                    imageId = advice.imageId,
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.image_size))
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .clip(MaterialTheme.shapes.small)
                )
                AdviceInfo(
                    titleId = advice.titleId,
                    dayNumber = advice.dayNumber,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            if (expanded) {
                AdviceDescription(
                    descriptionId = advice.descriptionId,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                )
            }
        }
    }
}

@Composable
fun AdviceImage(@DrawableRes imageId: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(imageId),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun AdviceInfo(@StringRes titleId: Int, dayNumber: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "${stringResource(R.string.day_name)} $dayNumber",
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(titleId),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
    }
}

@Composable
fun AdviceDescription(@StringRes descriptionId: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(descriptionId),
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@Preview
@Composable
fun AdviceCardPreview() {
    _30DaysOfTrainingTheme {
        AdviceCard(Advice(R.drawable.image1, R.string.description1, R.string.title1, 1))
    }
}

@Preview
@Composable
fun AdviceAppPreview() {
    _30DaysOfTrainingTheme {
        AdviceApp()
    }
}