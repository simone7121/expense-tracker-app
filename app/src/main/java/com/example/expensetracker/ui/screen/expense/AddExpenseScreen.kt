package com.example.expensetracker.ui.screen.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.expensetracker.R
import com.example.expensetracker.data.Expense
import com.example.expensetracker.ui.util.DateConverter
import com.example.expensetracker.ui.util.MainViewModel
import com.example.expensetracker.ui.util.categoriesImage
import com.example.expensetracker.ui.util.categoriesTitle
import kotlinx.coroutines.launch

@Composable
fun AddExpenseScreen(
    showBottomSheet: MutableState<Boolean>,
    viewModel: MainViewModel,
    expense: Expense = Expense()
) {
    val isUpdate = expense.id != 0L
    LaunchedEffect(isUpdate) {
        if (isUpdate) {
            viewModel.onStateChange(
                date = expense.date,
                amount = expense.amount,
                category = expense.category,
                desc = expense.description
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(20.dp)
    ) {
        IconButton(
            onClick = {
                showBottomSheet.value = false
                viewModel.resetState()
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = colorResource(id = R.color.background)
            )
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = White)
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = if (isUpdate) "Modifica Spesa" else "Aggiungi Spesa",
            fontSize = 28.sp,
            fontWeight = FontWeight(600),
            color = colorResource(id = R.color.background)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Inserisci i dettagli della tua spesa, come importo, descrizione, data e categoria.",
            fontSize = 18.sp,
            color = Black,
        )
        Spacer(Modifier.height(16.dp))
        InputForm(showBottomSheet, viewModel, isUpdate, expense.id)
    }
}

@Composable
fun InputForm(
    showBottomSheet: MutableState<Boolean>,
    viewModel: MainViewModel,
    isUpdate: Boolean,
    id: Long
) {
    val scope = rememberCoroutineScope()
    val showDialogCategories = remember { mutableStateOf(false) }
    val showDialogDate = remember { mutableStateOf(false) }

    Column {
        Text(text = "Importo", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Black)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(20),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = colorResource(id = R.color.tertiary),
                focusedContainerColor = colorResource(id = R.color.secondary),
                unfocusedContainerColor = colorResource(id = R.color.secondary),
                unfocusedIndicatorColor = colorResource(id = R.color.secondary)
            ),
            value = viewModel.expenseState.amount.toString(),
            onValueChange = {
                viewModel.onStateChange(amount = if(it.isBlank())0 else it.toInt())
            },
            textStyle = TextStyle(color = Black),
            leadingIcon = {
                Text(text = "€", color = colorResource(id = R.color.background))

            },
            placeholder = { Text(text = "10.000", color = Black) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
        Spacer(Modifier.height(16.dp))
        Text(text = "Descrizione", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Black)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(20),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = colorResource(id = R.color.tertiary),
                focusedContainerColor = colorResource(id = R.color.secondary),
                unfocusedContainerColor = colorResource(id = R.color.secondary),
                unfocusedIndicatorColor = colorResource(id = R.color.secondary)
            ),
            value = viewModel.expenseState.description,
            onValueChange = { viewModel.onStateChange(desc = it) },
            textStyle = TextStyle(color = Black),
            placeholder = { Text(text = "Inserisci Descrizione", color = Gray) },
            singleLine = true
        )
        Spacer(Modifier.height(16.dp))
        Text(text = "Categoria", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Black)
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { showDialogCategories.value = true },
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .defaultMinSize(minWidth = 1.dp, minHeight = 10.dp),
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.secondary),
                contentColor = Black
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = categoriesImage[viewModel.expenseState.category]!!),
                        contentDescription = "category"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = viewModel.expenseState.category,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Black
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Button",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        Text(text = "Data", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Black)
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { showDialogDate.value = true },
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .defaultMinSize(minWidth = 1.dp, minHeight = 10.dp),
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.secondary),
                contentColor = colorResource(id = R.color.background)
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = DateConverter(viewModel.expenseState.date),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Black
                )
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Button",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Spacer(Modifier.height(24.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.background)
            ),
            onClick = {
                if (isUpdate) {
                    viewModel.updateExpense(
                        Expense(
                            id = id,
                            amount = viewModel.expenseState.amount,
                            description = viewModel.expenseState.description,
                            category = viewModel.expenseState.category,
                            date = viewModel.expenseState.date
                        )
                    )
                } else {
                    viewModel.addExpense(
                        viewModel.expenseState
                    )
                }

                viewModel.resetState()

                scope.launch {
                    showBottomSheet.value = false
                }
            }
        ) {
            Text(
                text = if (isUpdate) "Aggiorna Spesa" else "Aggiungi Spesa",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = White
            )
        }
    }

    if (showDialogCategories.value) {
        CategoryDialog(viewModel = viewModel, showDialog = showDialogCategories)
    }

    if (showDialogDate.value) {
        DateDialog(showDialog = showDialogDate, viewModel = viewModel)
    }
}

@Composable
fun CategoryDialog(viewModel: MainViewModel, showDialog: MutableState<Boolean>) {
    Dialog(
        onDismissRequest = { showDialog.value = false },
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(10)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                Modifier
                    .padding(10.dp)
                    .background(Color.White)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Seleziona la Categoria",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyVerticalGrid(columns = GridCells.Fixed(2), Modifier.padding(bottom = 16.dp)) {
                    items(categoriesTitle) { title ->
                        val isSelected = title == viewModel.expenseState.category
                        Button(
                            onClick = {
                                viewModel.onStateChange(category = title)
                                showDialog.value = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .padding(4.dp)
                                .defaultMinSize(minWidth = 1.dp, minHeight = 10.dp),
                            shape = RoundedCornerShape(20),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = if (isSelected) R.color.background else R.color.secondary),
                                contentColor = colorResource(id = if (isSelected) R.color.secondary else R.color.background),
                            )
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = categoriesImage[title]!!),
                                    contentDescription = "Button",
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = title,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (isSelected) Color.White else Gray
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(showDialog: MutableState<Boolean>, viewModel: MainViewModel) {
    val state = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = { showDialog.value = false },
        confirmButton = {
            TextButton(
                onClick = {
                    showDialog.value = false
                }
            ) {
                viewModel.onStateChange(date = state.selectedDateMillis)
                Text("Conferma")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    showDialog.value = false
                }
            ) {
                Text("Annulla")
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = Color.Black,
        )
    ) {
        DatePicker(
            state = state,
        )
    }


}

//@Preview(showBackground = true)
//@Composable
//fun CategoryDialogPreview(){
//    CategoryDialog()
//}
