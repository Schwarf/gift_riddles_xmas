package com.example.xmas_gift_riddles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
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
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        // Main text area
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.weight(1f)) // Adds flexible space
            GlowingText(
                text = context.getString(R.string.snowman_message, name),
                glowColor = Color.Green,
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
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        // Background image setup
        Image(
            painter = painterResource(R.drawable.family),
            contentDescription = null, // Background image does not require a description
            contentScale = ContentScale.Fit,
            modifier = Modifier.matchParentSize().align(Alignment.TopCenter)

        )
        Column(modifier = Modifier.padding(16.dp).fillMaxHeight(0.2f)) {
            Spacer(modifier = Modifier.weight(1f)) // This adds flexible space between text and input
            GlowingText(
                text = context.getString(R.string.family_task),
                glowColor = Color.Green,
                textColor = Color.Red,
                alpha = 0.8f
            )
        }
        // Button positioned at the bottom of the screen
        Button(
            onClick = { onButtonClick() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp) // Optional: Add padding around the button
        ) {
            GlowingText(
                text = "Hab es mir gemerkt. Weiter!",
                glowColor = Color.Green,
                textColor = Color.Red,
                alpha = 0.8f
            )
        }
    }
}

@Composable
fun FamilyImageRiddle(onButtonClick: () -> Unit) {
    val context = LocalContext.current
    val questions = listOf(
        context.getString(R.string.family_q_1) to "rechts",
        context.getString(R.string.family_q_2) to "mitte",
        context.getString(R.string.family_q_3) to "marta"
    )
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var userAnswer by remember { mutableStateOf("") }
    var isCorrect by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isCorrect && currentQuestionIndex == questions.size - 1) {
                // All questions answered correctly
                GlowingText(
                    text = context.getString(R.string.family_solved),
                    glowColor = Color.Red,
                    textColor = Color.Green,
                    alpha = 0.8f
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onButtonClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Weiter zur nächsten Frage.")
                }
            } else {
                // Show current question and answer input
                Text(
                    text = questions[currentQuestionIndex].first,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp)
                )

                // Input box for the user answer
                TextField(
                    value = userAnswer,
                    onValueChange = { userAnswer = it },
                    label = { Text("Deine Antwort") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Submit button
                Button(
                    onClick = {
                        if (userAnswer.trim()
                                .equals(questions[currentQuestionIndex].second, ignoreCase = true)
                        ) {
                            if (currentQuestionIndex < questions.size - 1) {
                                currentQuestionIndex++
                                userAnswer = ""
                            } else {
                                // All questions answered
                                isCorrect = true
                            }
                        } else {
                            // Show dialog if the answer is incorrect
                            showDialog = true
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Abschicken")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Feedback message for intermediate correct answers
                if (isCorrect) {
                    Text(
                        text = "Richtig! Nächste Frage:",
                        color = Color.Green,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }

    if (showDialog) {
        ShowIncorrectPasswordDialog(onDismiss = { showDialog = false })
    }
}


@Composable
fun SquareRiddle(onPasswordCorrect: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var isPasswordCorrect by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isPasswordCorrect) {
            // Display only the GlowingText and "Weiter gehts" button
            GlowingText(
                text = context.getString(R.string.square_solved),
                glowColor = Color.Green,
                textColor = Color.Red,
                alpha = 0.8f
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onPasswordCorrect,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Weiter gehts")
            }
        } else {
            // Display the question and input components
            Image(
                painter = painterResource(R.drawable.squares), // Replace with your drawable
                contentDescription = "squares",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Wie viele Quadrate findest Du in diesem Bild?",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            DigitOnlyTextField(value = password, onValueChange = { password = it })

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (password == "5") {
                    isPasswordCorrect = true
                } else {
                    showDialog = true
                }
            }) {
                Text("Zahlencode abschicken!")
            }
        }
    }

    if (showDialog) {
        ShowIncorrectPasswordDialog(onDismiss = { showDialog = false })
    }
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

@Composable
fun DigitOnlyTextField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = {
            // Check if the new value consists only of digits before updating
            if (it.all { char -> char.isDigit() }) {
                onValueChange(it)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text("Geheimer Zahlencode") }
        // Add other parameters like Modifier as needed
    )
}

@Composable
fun ShowIncorrectPasswordDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {},
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cartoon_family),
                    contentDescription = "Error",
                    modifier = Modifier.fillMaxWidth().height(200.dp).clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Text(text = "Das war leider falsch.")

            }
        },
        confirmButton = {
            Button(
                onClick = { onDismiss() }
            ) {
                Text(text = "Nochmal probieren.")
            }
        }
    )
}



@Composable
fun NameRiddle(onButtonClick: () -> Unit) {
    val context = LocalContext.current
    val questions = listOf(
        context.getString(R.string.names_q_1) to "wrb",
        context.getString(R.string.names_q_2) to "anneliese",
        context.getString(R.string.names_q_3) to "karl"
    )
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var userAnswer by remember { mutableStateOf("") }
    var isCorrect by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isCorrect && currentQuestionIndex == questions.size - 1) {
                // All questions answered correctly
                GlowingText(
                    text = context.getString(R.string.names_solved),
                    glowColor = Color.Red,
                    textColor = Color.Green,
                    alpha = 0.8f
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onButtonClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Weiter zur nächsten Frage.")
                }
            } else {
                // Show current question and answer input
                Text(
                    text = questions[currentQuestionIndex].first,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp)
                )

                // Input box for the user answer
                TextField(
                    value = userAnswer,
                    onValueChange = { userAnswer = it },
                    label = { Text("Deine Antwort") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Submit button
                Button(
                    onClick = {
                        if (userAnswer.trim()
                                .equals(questions[currentQuestionIndex].second, ignoreCase = true)
                        ) {
                            if (currentQuestionIndex < questions.size - 1) {
                                currentQuestionIndex++
                                userAnswer = ""
                            } else {
                                // All questions answered
                                isCorrect = true
                            }
                        } else {
                            // Show dialog if the answer is incorrect
                            showDialog = true
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Abschicken")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Feedback message for intermediate correct answers
                if (isCorrect) {
                    Text(
                        text = "Richtig! Nächste Frage:",
                        color = Color.Green,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }

    if (showDialog) {
        ShowIncorrectPasswordDialog(onDismiss = { showDialog = false })
    }
}
