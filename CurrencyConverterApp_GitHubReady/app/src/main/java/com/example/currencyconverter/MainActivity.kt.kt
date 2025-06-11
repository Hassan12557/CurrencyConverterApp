 class MainActivity : ComponentActivity() {
    private lateinit var themePrefs: ThemePreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        themePrefs = ThemePreferenceManager(applicationContext)
        val repository = CurrencyRepository(applicationContext)
        val viewModel = ConverterViewModel(repository)

        runBlocking {
            val darkMode = themePrefs.isDarkMode()
            setContent {
                var isDark by remember { mutableStateOf(darkMode) }

                CurrencyConverterTheme(darkTheme = isDark) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Column {
                            ThemeSwitcher(isDarkMode = isDark) {
                                isDark = it
                                lifecycleScope.launch { themePrefs.saveTheme(it) }
                            }
                            ConverterScreen(viewModel)
                        }
                    }
                }
            }
        }
    }
}
