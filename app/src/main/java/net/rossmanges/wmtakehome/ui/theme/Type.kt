package net.rossmanges.wmtakehome.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import net.rossmanges.wmtakehome.R

val googleFontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Nunito Sans")

val RossWmtFontFamily = FontFamily(
    Font(
        googleFont = fontName,
        fontProvider = googleFontProvider,
        weight = FontWeight.Normal
    ),
    Font(
        googleFont = fontName,
        fontProvider = googleFontProvider,
        weight = FontWeight.Bold
    ),
    Font(
        googleFont = fontName,
        fontProvider = googleFontProvider,
        weight = FontWeight.Medium
    ),
    Font(
        googleFont = fontName,
        fontProvider = googleFontProvider,
        weight = FontWeight.Light
    ),
    Font(
        googleFont = fontName,
        fontProvider = googleFontProvider,
        weight = FontWeight.Black
    )
)

// Set of Material typography styles to start with
val RossWmtTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = RossWmtFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = RossWmtFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = RossWmtFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = RossWmtFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = RossWmtFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
)

