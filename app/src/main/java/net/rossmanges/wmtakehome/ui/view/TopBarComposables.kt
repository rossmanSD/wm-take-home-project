package net.rossmanges.wmtakehome.ui.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A custom composable function to add an input field to the top app bar.
 * @param onSearchTextChanged   The callback to be invoked when text is changed in the input field.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithSearch(onSearchTextChanged: (String) -> Unit) {
    TopAppBar(
        title = { Text(text = "") },
        actions = {
            InputTextField(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
                value = "",
                placeholder = "Search countries...",
                shape = RoundedCornerShape(6.dp),
                onTextChanged = { onSearchTextChanged(it) }
            )
        }
    )
}

/**
 * A Composable function to display a customized text input field
 */
@Composable
fun InputTextField(
    modifier: Modifier,
    value: String,
    placeholder: String,
    shape: RoundedCornerShape,
    onTextChanged: (String) -> Unit
) {
    var inputValue by rememberSaveable { mutableStateOf(value) }
    TextField(
        value = inputValue,
        onValueChange = {
            inputValue = it
            onTextChanged(it)
        },
        modifier = modifier,
        placeholder = { Text(text = placeholder) },
        shape = shape,
        singleLine = true,
        trailingIcon = {
            if (inputValue.isNotEmpty()) {
                IconButton(onClick = {
                    inputValue = ""
                    onTextChanged("")
                }) {
                    Icon(
                        imageVector = Icons.Default.Close, // "X" icon
                        contentDescription = "Clear text"
                    )
                }
            }
        }
    )
}