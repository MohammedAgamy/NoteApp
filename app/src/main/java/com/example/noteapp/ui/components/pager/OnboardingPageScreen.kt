package com.example.noteapp.ui.components.pager

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.R
import com.example.noteapp.data.OnboardingData
import com.example.noteapp.ui.ui.ui.auth.LoginActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch


// list for data need to show
val onboardingList = listOf(
    OnboardingData(
        "Manage your notes easily",
        "A completely easy way to manage and customize your notes.",
        R.drawable.page2
    ),
    OnboardingData(
        "Organize your thoughts",
        "Most beautiful note-taking application.",
        R.drawable.page1
    ),
    OnboardingData(
        "Create cards and easy styling",
        "Making your content legible has never been easier.",
        R.drawable.page3
    )
)


// ui for pages

@Composable
fun OnboardingPageScreen(
    page: OnboardingData,
    isLastPage: Boolean,
    onSkipClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val navController = rememberNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Absolute.Right, // Align items to opposite ends
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Skip",
                color = Color(0xFF007AFF),
                fontSize = 25.sp,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.clickable {
                    onSkipClick()
                }
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(250.dp)
                .padding(top = 50.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = page.title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(100.dp))
        Button(
            onClick = onNextClick,
            modifier = Modifier
                .width(300.dp)
                .height(35.dp),
            colors = ButtonDefaults.buttonColors(
                Color(0xFF007AFF)
            )
        ) {
            Text(
                text = if (isLastPage) "GetStart" else "Next"

            )

        }
    }
}

// handel clickable button and case
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val context = LocalContext.current
    val pagerState = com.google.accompanist.pager.rememberPagerState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        com.google.accompanist.pager.HorizontalPager(
            count = onboardingList.size,
            state = pagerState
        ) { page ->
            OnboardingPageScreen(
                page = onboardingList[page],
                isLastPage = page == onboardingList.lastIndex,
                onSkipClick = onFinish,
                onNextClick = {
                    if (pagerState.currentPage < onboardingList.size - 1) {
                        // Navigate to the next page
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)

                        }
                    } else {
                        // Finish onboarding
                        onFinish()
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            )
        }
    }
}
