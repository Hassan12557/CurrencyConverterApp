@Composable
fun ThemeSwitcher(
    isDarkMode: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Dark Theme")
        Switch(checked = isDarkMode, onCheckedChange = { onToggle(it) })
    }
}
