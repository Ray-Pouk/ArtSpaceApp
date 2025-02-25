package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceAppTheme {
                ArtSpaceScreen()
            }
        }
    }
}

@Composable
fun ArtSpaceScreen() {
    val artworks = listOf(
        Artwork(R.drawable.art1, "Still Life of Blue Rose and Other Flowers", "Owen Scott", 2021),
        Artwork(R.drawable.art2, "The Persistence of Memory", "Salvador Dalí", 1931),
        Artwork(R.drawable.art3, "Mona Lisa", "Leonardo da Vinci", 1503)
    )

    var currentArtwork by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ArtworkWall(artworks[currentArtwork])
        Spacer(modifier = Modifier.height(16.dp))
        ArtworkDescriptor(artworks[currentArtwork])
        Spacer(modifier = Modifier.height(16.dp))
        DisplayController(
            onPrevious = { currentArtwork = (currentArtwork - 1 + artworks.size) % artworks.size },
            onNext = { currentArtwork = (currentArtwork + 1) % artworks.size }
        )
    }
}

@Composable
fun ArtworkWall(artwork: Artwork) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = artwork.imageRes),
            contentDescription = artwork.title,
            modifier = Modifier.size(300.dp).padding(16.dp)
        )
    }
}

@Composable
fun ArtworkDescriptor(artwork: Artwork) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEDEDED), RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = artwork.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${artwork.artist} (${artwork.year})",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun DisplayController(onPrevious: () -> Unit, onNext: () -> Unit) {
    Row {
        Button(
            onClick = onPrevious,
            modifier = Modifier.padding(8.dp),
            shape = RoundedCornerShape(50)
        ) {
            Text("Previous")
        }
        Button(
            onClick = onNext,
            modifier = Modifier.padding(8.dp),
            shape = RoundedCornerShape(50)
        ) {
            Text("Next")
        }
    }
}

data class Artwork(val imageRes: Int, val title: String, val artist: String, val year: Int)

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceAppTheme {
        ArtSpaceScreen()
    }
}
