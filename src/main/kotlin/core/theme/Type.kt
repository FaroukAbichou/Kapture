package core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp

val CircularStd = FontFamily(
    Font(
        resource = "font/circularstd_black.otf",
        weight = FontWeight.Black,
        style = FontStyle.Normal
    ),
    Font(
        resource = "font/circularstd_black_italic.otf",
        weight = FontWeight.Black,
        style = FontStyle.Italic
    ),
    Font(
        resource = "font/circularstd_bold.otf",
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),
    Font(
        resource = "font/circularstd_bold_italic.otf",
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    ),
    Font(
        resource = "font/circularstd_book.otf",
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    ),
    Font(
        resource = "font/circularstd_book_italic.otf",
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    ),
    Font(
        resource = "font/circularstd_light.otf",
        weight = FontWeight.Light,
        style = FontStyle.Normal
    ),
    Font(
        resource = "font/circularstd_light_italic.otf",
        weight = FontWeight.Light,
        style = FontStyle.Italic
    ),
    Font(
        resource = "font/circularstd_medium.otf",
        weight = FontWeight.Medium,
        style = FontStyle.Normal
    ),
    Font(
        resource = "font/circularstd_medium_italic.otf",
        weight = FontWeight.Medium,
        style = FontStyle.Italic
    )
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.W500,
        fontSize = (5.61 * 16).sp
    ),
    displayMedium = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.W400,
        fontSize = (4.209 * 16).sp
    ),
    displaySmall = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.W300,
        fontSize = (3.157 * 16).sp
    ),

    headlineLarge = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.W400,
        fontSize = ( 2.369 * 16).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.W300,
        fontSize = (1.777 * 16).sp
    ),
    headlineSmall = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.W200,
        fontSize = ( 1.333 * 16).sp
    ),


    bodyLarge = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.Normal,
        fontSize = (1 * 16).sp
    ),
    bodyMedium = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.Normal,
        fontSize = (0.875 * 16).sp
    ),
    bodySmall = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.Normal,
        fontSize = (0.75 * 16).sp
    ),
//
//    // Adjustments based on your initial values
//    titleLarge = TextStyle(
//        fontFamily = CircularStd,
//        fontWeight = FontWeight.W400,
//        fontSize = 24.sp
//    ),
//    titleMedium = TextStyle(
//        fontFamily = CircularStd,
//        fontWeight = FontWeight.W500,
//        fontSize = 18.sp
//    ),
//    titleSmall = TextStyle(
//        fontFamily = CircularStd,
//        fontWeight = FontWeight.W400,
//        fontSize = 16.sp
//    ),
//    bodyLarge = TextStyle(
//        fontFamily = CircularStd,
//        fontWeight = FontWeight.Normal,
//        fontSize = 18.sp
//    ),
//    labelLarge = TextStyle(
//        fontFamily = CircularStd,
//        fontWeight = FontWeight.Normal,
//        fontSize = 18.sp
//    ),
//    labelMedium = TextStyle(
//        fontFamily = CircularStd,
//        fontWeight = FontWeight.Normal,
//        fontSize = 14.sp
//    ),
//    labelSmall = TextStyle(
//        fontFamily = CircularStd,
//        fontWeight = FontWeight.Normal,
//        fontSize = 12.sp
//    )
)
