package com.example.expensetracker.ui.screen.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.R
import com.example.expensetracker.data.Expense
import com.example.expensetracker.ui.util.ConvertDecimal
import com.example.expensetracker.ui.util.DateConverter
import com.example.expensetracker.ui.util.MainViewModel

@Composable
fun DetailExpenseScreen(showBottomSheet: MutableState<Boolean>,showBottomSheetModify: MutableState<Boolean>,viewModel: MainViewModel, expense: Expense){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp).padding(bottom = 32.dp).background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dettagli Spesa",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = colorResource(id = R.color.background)
        )
        Spacer(Modifier.height(16.dp))
        Column(Modifier.fillMaxWidth()) {
            Text(
                text = expense.description,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "EUR € ${ConvertDecimal(expense.amount)}",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                color = Color.Green
            )
            Divider(Modifier.padding(vertical = 16.dp))
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Categoria",
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = expense.category,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.background)
            )
        }
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Data",
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = DateConverter(expense.date),
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.background)
            )
        }
        Spacer(Modifier.height(30.dp))
        Row(Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                          showBottomSheet.value = false
                            showBottomSheetModify.value = true
                },
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEDEFF3),
                    contentColor = colorResource(id = R.color.background)
                ),
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                Spacer(Modifier.width(10.dp))
                Text(text = "Modifica", fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = {
                    viewModel.deleteExpense(expense)
                    showBottomSheet.value = false
                          },
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFDF0EF),
                    contentColor = Color(0xFFC70039)
                ),
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                Spacer(Modifier.width(10.dp))
                Text(text = "Cancella", fontWeight = FontWeight.Bold)
            }
        }
    }
}
