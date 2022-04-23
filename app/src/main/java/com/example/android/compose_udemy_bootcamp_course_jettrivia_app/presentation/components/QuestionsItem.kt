package com.example.android.compose_udemy_bootcamp_course_jettrivia_app.presentation.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.data.remote.dto.QuestionDtoItem
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.presentation.trivia_home.TriviaScreenViewModel
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.util.AppColors
import java.lang.Exception

@Composable
fun QuestionsItem(viewModel: TriviaScreenViewModel = hiltViewModel()) {
    val questions = viewModel.data.value.data?.toMutableList()
    val questionIndex = remember {
        mutableStateOf(0)
    }

    if (viewModel.data.value.isLoading == true) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }
        Log.d("Test", "Questions...loading")
    } else {
        val question = try {
            questions?.get(questionIndex.value)
        } catch (e: Exception) {
            null
        }
        if (questions != null) {
            QuestionDisplay(
                viewModel = viewModel,
                triviaInfo = question!!,
                questionIndex = questionIndex
            ) {
                questionIndex.value = questionIndex.value + 1
            }
        }
//        val questionIndices = (0 until 10)
//            .map {
//                questions?.get(it)?.question
//            }
//        Log.d("Question", "Questions..$questionIndices")
    }
}

@Composable
fun QuestionDisplay(
    viewModel: TriviaScreenViewModel,
    triviaInfo: QuestionDtoItem,
    questionIndex: MutableState<Int>,
    onNextClick: (Int) -> Unit = {}
) {
    val choicesState = remember(triviaInfo) {
        triviaInfo.choices.toMutableList()
    }
    val answerState = remember(triviaInfo) {
        mutableStateOf(0)
    }
    val correctAnswerState = remember(triviaInfo) {
        mutableStateOf<Boolean?>(null)
    }
    val updateAnswer: (Int) -> Unit = remember(triviaInfo) {
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == triviaInfo.answer
        }
    }
    val pathEffect = PathEffect.dashPathEffect(
        intervals = floatArrayOf(10f, 20f),
        phase = 0f
    )
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = AppColors.mDarkPurple
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            if (questionIndex.value >= 3) ShowProgress(score = questionIndex.value)
            QuestionsTracker(counter = questionIndex.value, outOf = viewModel.getQuestionCount())
            DrawDottedLine(pathEffect = pathEffect)
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = triviaInfo.question,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxHeight(fraction = 0.3f),
                    color = AppColors.mOffWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )
                choicesState.forEachIndexed { index, answerText ->
                    Row(
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(
                                width = 4.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        AppColors.mOffDarkPurple,
                                        AppColors.mOffDarkPurple
                                    )
                                ),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .clip(
                                RoundedCornerShape(
                                    topStartPercent = 50,
                                    topEndPercent = 50,
                                    bottomEndPercent = 50,
                                    bottomStartPercent = 50
                                )
                            )
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (answerState.value == index),
                            onClick = { updateAnswer(index) },
                            modifier = Modifier.padding(16.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (correctAnswerState.value == true
                                    && index == answerState.value
                                ) {
                                    Color.Green.copy(alpha = 0.2f)
                                } else Color.Red.copy(0.2f)
                            )
                        )
                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Light,
                                    color = if (correctAnswerState.value == true
                                        && index == answerState.value
                                    ) {
                                        Color.Green
                                    } else if (correctAnswerState.value == false && index == answerState.value) {
                                        Color.Red
                                    } else AppColors.mOffWhite, fontSize = 17.sp
                                )
                            ) {
                                append(text = answerText)
                            }
                        }
                        Text(text = annotatedString)
                    }
                }
                Button(
                    onClick = { onNextClick(questionIndex.value) },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppColors.mLightBlue
                    )
                ) {
                    Text(
                        text = "Next",
                        modifier = Modifier.padding(4.dp),
                        color = AppColors.mOffWhite,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}

@Composable
fun DrawDottedLine(pathEffect: PathEffect) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
    ) {
        drawLine(
            color = AppColors.mLightGray,
            start = Offset(x = 0f, y = 0f),
            end = Offset(size.width, y = 0f),
            pathEffect = pathEffect
        )
    }
}

@Composable
fun QuestionsTracker(
    counter: Int = 10,
    outOf: Int = 100
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
                withStyle(
                    style = SpanStyle(
                        color = AppColors.mLightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp
                    )
                ) {
                    append("Question ${counter.plus(1)}/")
                    withStyle(
                        style = SpanStyle(
                            color = AppColors.mLightGray,
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp
                        )
                    ) {
                        append("$outOf")
                    }
                }
            }
        },
        modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 20.dp)
    )
}

@Preview
@Composable
fun ShowProgress(score: Int = 12) {
    val gradient = Brush.linearGradient(listOf(
        Color(0XFFF95075), Color(0XFFBE6BE5))
    )
    val progressFactor by remember(score) {
        mutableStateOf(score * 0.005f)
    }
    Row(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(45.dp)
            .border(
                width = 4.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        AppColors.mLightPurple, AppColors.mLightPurple
                    )
                ),
                shape = RoundedCornerShape(34.dp)
            )
            .clip(
                RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50,
                    bottomEndPercent = 50,
                    bottomStartPercent = 50
                )
            )
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            contentPadding = PaddingValues(1.dp),
            onClick = { },
            modifier = Modifier
                .fillMaxWidth(fraction = progressFactor)
                .background(brush = gradient),
            enabled = false,
            elevation = null,
            colors = buttonColors(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent
            )
        ) {
            Text(
                text = (score * 10).toString(),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(23.dp))
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()
                    .padding(6.dp),
                color = AppColors.mOffWhite,
                textAlign = TextAlign.Center
            )
        }
    }
}