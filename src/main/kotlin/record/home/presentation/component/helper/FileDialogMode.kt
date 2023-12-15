package record.home.presentation.component.helper

enum class FileDialogMode {
    Load,
    Save
    ;

    fun toAwtMode(): Int = when(this) {
        Load -> java.awt.FileDialog.LOAD
        Save -> java.awt.FileDialog.SAVE
    }
}
