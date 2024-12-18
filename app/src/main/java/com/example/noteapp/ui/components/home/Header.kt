package com.example.noteapp.ui.components.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowDate() {
    val currentDate = remember {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH) // صيغة التاريخ
        LocalDate.now().format(formatter)
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(3f)) {

            Text(
                text = currentDate,
                fontSize = 15.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)

            )

            Text(
                text = "Notes",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.header),
            contentDescription = "header",
            modifier = Modifier
                .weight(1f)
                .size(18.dp)
                .padding(8.dp)
                .padding(20.dp)
        )
    }


}