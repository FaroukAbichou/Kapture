-keep class org.sqlite.** { *; }
#-dontwarn kotlinx.coroutines.debug.*
-keep class org.apache.** { *; }
-keepnames class javax.servlet.** { *; }
-dontwarn org.apache.**
#-dontwarn org.apache.commons.logging.impl.*
#-dontwarn org.apache.pdfbox.pdmodel.encryption.*
#-keepnames class org.apache.** { *; }
#-keepnames class org.apache.commons.logging.impl.** { *; }
#-keepnames class org.apache.pdfbox.pdmodel.encryption.** { *; }
#-keepclasseswithmembers public class MainKt {
#    public static void main(java.lang.String[]);
#}
#-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }
-keep class jpen.** { *; }
#-keep class org.jetbrains.skia.** { *; }
-keep class org.jetbrains.skiko.** { *; }
#-assumenosideeffects public class androidx.compose.runtime.ComposerKt {
#    void sourceInformation(androidx.compose.runtime.Composer,java.lang.String);
#    void sourceInformationMarkerStart(androidx.compose.runtime.Composer,int,java.lang.String);
#    void sourceInformationMarkerEnd(androidx.compose.runtime.Composer);
#}
-ignorewarnings
-dontwarn *

