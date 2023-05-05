package com.example.experimentation.EditProfile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.experimentation.AuthScreen.*
import com.example.experimentation.R
import com.example.experimentation.room.UsersDb
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private val viewModel: EditProfileViewModel by viewModels()

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner).also { strategy ->
            setViewCompositionStrategy(strategy)
        }
        setContent {
            UiBinding(viewModel = viewModel)
        }
    }
}

@Composable
private fun UiBinding(viewModel: EditProfileViewModel) {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppBar(viewModel)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                EditInstruments(viewModel)
            }
        }
    }
}

@Composable
private fun AppBar(viewModel: EditProfileViewModel) {
    TopAppBar(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),

        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(end = 45.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 19.sp,
                    fontStyle = FontStyle.Normal,
                    text = "Редактирование профиля",
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        },
        backgroundColor = Color(0xFF6AAC40),
        navigationIcon = {
            IconButton(onClick = { viewModel.routeToProfile() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        elevation = 0.dp
    )
}

@Preview
@Composable
private fun CardView() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 20.dp,
        backgroundColor = Color.LightGray
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.max),
                contentDescription = "Image",
                modifier = Modifier
                    .padding(5.dp)
                    .size(90.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Имя",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditInstruments(viewModel:EditProfileViewModel) {
    viewModel.pref?.let { viewModel.takeProfile(it) }
    val profile by viewModel.userData.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        var textState by remember { mutableStateOf("") }
        OutlinedTextField(
            value = textState,
            onValueChange = { newValue ->
                textState = newValue.capitalizeFirstLetter()
            },
            label = {
                if (profile!= null) {
                    Text(text = profile!!.name)
                } else{
                    Text(text = "Name")
                }
            },
            singleLine = true,
            maxLines = 1,
            textStyle = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onSurface
            ),
            colors = outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                textColor = MaterialTheme.colors.onSurface,
                leadingIconColor = MaterialTheme.colors.onSurface
            ),
            modifier = Modifier
                .padding(top = 22.dp)
                .fillMaxWidth()
        )


        var genderDropdownExpanded by remember { mutableStateOf(false) }
        var selectedGender by remember { mutableStateOf("") }
        val genders = listOf("Мужской", "Женский")

        OutlinedTextField(
            value = selectedGender,
            onValueChange = { selectedGender = it },
            label = {
                if (profile != null) {
                    profile!!.gender?.let { Text(text = it) }
            } else{
                Text(text = "Gender")
            }  },
            textStyle = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onSurface
            ),
            trailingIcon = {
                IconButton(onClick = { genderDropdownExpanded = true }) {
                    Icon(Icons.Filled.ArrowDropDown, null)
                }
            },
            colors = outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                textColor = MaterialTheme.colors.onSurface,
                leadingIconColor = MaterialTheme.colors.onSurface
            ),
            modifier = Modifier
                .padding(top = 18.dp)
                .fillMaxWidth(),
            enabled = false
        )

        BoxWithConstraints {
            if (genderDropdownExpanded) {
                DropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.surface),
                    expanded = genderDropdownExpanded,
                    onDismissRequest = { genderDropdownExpanded = false }
                ) {
                    genders.forEach { sex ->
                        DropdownMenuItem(onClick = {
                            selectedGender = sex
                            genderDropdownExpanded = false
                        }) {
                            Text(
                                text = sex,
                                style = MaterialTheme.typography.body1,
                                color = if (sex == selectedGender) {
                                    MaterialTheme.colors.primary
                                } else {
                                    MaterialTheme.colors.onSurface
                                }
                            )
                        }
                    }
                }
            }
        }

        val selectedDate = remember { mutableStateOf(LocalDate.now()) }
        val onDateSet = { _: DatePicker, year: Int, month: Int, day: Int ->
            val newDate = LocalDate.of(year, month + 1, day)
            selectedDate.value = newDate
        }
        val ctx = LocalContext.current
        OutlinedTextField(
            value = selectedDate.value.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
            onValueChange = { },
            label = { profile?.date?.let { Text(text = it) } },
            singleLine = true,
            maxLines = 1,
            textStyle = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onSurface
            ),
            colors = outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                textColor = MaterialTheme.colors.onSurface,
                leadingIconColor = MaterialTheme.colors.onSurface
            ),
            trailingIcon = {
                IconButton(onClick = {
                    val initialDate = selectedDate.value
                    val datePickerDialog = DatePickerDialog(
                        ctx,
                        onDateSet,
                        initialDate.year,
                        initialDate.monthValue - 1,
                        initialDate.dayOfMonth
                    ).apply {
                        datePicker.calendarViewShown = true
                        datePicker.spinnersShown = true
                    }
                    datePickerDialog.show()
                }) {
                    Icon(Icons.Filled.DateRange, null)
                }
            },
            modifier = Modifier
                .padding(top = 18.dp, bottom = 8.dp)
                .fillMaxWidth(),
            enabled = false
        )
       val model = UsersDb(
            name = textState,
            date = selectedDate.toString(),
            gender = selectedGender,
           uuid = "main"
        )
        Button(viewModel, model)
    }

}


@Composable
fun Button(viewModel: EditProfileViewModel, model: UsersDb) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            onClick = {
                viewModel.registration(model)
                viewModel.routeToProfile()
            },
            modifier = Modifier
                .padding(end = 12.dp, bottom = 12.dp, start = 12.dp)
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(8.dp)),
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF6AAC40)
            ),
            content = {
                Text(text = "Сохранить")
            }
        )
    }
}


fun String.capitalizeFirstLetter(): String {
    return replaceFirstChar { it.uppercase() }
}
