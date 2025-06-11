 @Composable
fun ConverterScreen(viewModel: ConverterViewModel) {
    val amount by viewModel.amount.collectAsState()
    val base by viewModel.baseCurrency.collectAsState()
    val target by viewModel.targetCurrency.collectAsState()
    val state by viewModel.state.collectAsState()

    val currencies = listOf("USD", "EUR", "INR", "GBP", "JPY")

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()) {

        TextField(
            value = amount,
            onValueChange = { viewModel.amount.value = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownSelector("Base Currency", base, currencies) {
            viewModel.baseCurrency.value = it
        }

        DropdownSelector("Target Currency", target, currencies) {
            viewModel.targetCurrency.value = it
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.convert() }) {
            Text("Convert")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is ConverterViewModel.UiState.Loading -> CircularProgressIndicator()
            is ConverterViewModel.UiState.Success -> {
                val result = (state as ConverterViewModel.UiState.Success)
                Text(
                    "Converted: %.2f".format(result.result) + if (!result.online) " (offline)" else "",
                    style = MaterialTheme.typography.h6
                )
            }
            is ConverterViewModel.UiState.Error -> {
                Text((state as ConverterViewModel.UiState.Error).message, color = Color.Red)
            }
            else -> {}
        }
    }
}
