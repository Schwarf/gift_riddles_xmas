package com.example.xmas_gift_riddles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xmas_gift_riddles.ui.theme.XmasGiftRiddlesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XmasGiftRiddlesTheme {
                Scaffold { AppNavigation() }
            }
        }
    }
}

@Composable
fun Greeting(name: String, onButtonClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Main text area
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.weight(1f)) // Adds flexible space
            GlowingText(
                text = "Hallo $name, \n" +
                        "ich bin der Weihnachts-Schneemann. Auf diesem weissen Bild kannst Du mich nicht sehen. \n" +
                        "Aber schau mal genau hin, dann findest Du vielleicht meine Nase. Drück sie.",
                glowColor = Color.Red,
                textColor = Color.Red,
                alpha = 0.8f
            )
        }

        // Small carrot image as a hidden button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Random positioning for the carrot button
            Box(
                modifier = Modifier
                    .offset(
                        x = (-100..100).random().dp, // Random X offset
                        y = (-200..200).random().dp  // Random Y offset
                    )
                    .size(25.dp), // Small size for carrot and button
                contentAlignment = Alignment.Center
            ) {
                // Carrot image
                Image(
                    painter = painterResource(R.drawable.carrot), // Replace with carrot drawable
                    contentDescription = "Hidden Carrot",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.15f), // Make the carrot slightly washed-out
                    contentScale = ContentScale.Fit
                )
                // Invisible button for interaction
                Button(
                    onClick = { onButtonClick()
                        // Action when the carrot is found and clicked
                    },
                    modifier = Modifier
                        .fillMaxSize(),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Transparent, // Make the button invisible
                        containerColor = Color.Transparent // No text or icon color
                    ),
                    elevation = null // Remove shadow
                ) {}
            }
        }
    }
}

@Composable
fun FamilyImage(onButtonClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background image setup
        Image(
            painter = painterResource(R.drawable.family),
            contentDescription = null, // Background image does not require a description
            contentScale = ContentScale.Fit,
            modifier = Modifier.matchParentSize()
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.weight(1f)) // This adds flexible space between text and input
            GlowingText(
                text = "Schau Dir das Bild genau an und merke Dir wer wo ist. Wenn Du weiter machen willst drueck den Knopf!",
                glowColor = Color.Red,
                textColor = Color.Red,
                alpha = 0.8f
            )
        }
        Button(
            onClick = { onButtonClick()},
        ){ Text(text = "Hab es mir gemerkt. Weiter!") }

    }
}

@Composable
fun FamilyImageRiddle()
{

}

@Composable
fun GlowingText(text: String, glowColor: Color, textColor: Color, alpha: Float) {
    BasicText(
        text = text,
        style = TextStyle(
            color = textColor,
            fontSize = 24.sp,
            shadow = Shadow(
                color = glowColor,
                offset = Offset(0f, 0f),
                blurRadius = 8f // Reduced blur for a sharper effect
            )
        ),
        modifier = Modifier
            .graphicsLayer(alpha = alpha)
            .drawWithContent {
                drawContent()
                withTransform({
                    translate(left = 4f, top = 4f)
                }) {
                    this@drawWithContent.drawContent()
                }
            }
    )
}
