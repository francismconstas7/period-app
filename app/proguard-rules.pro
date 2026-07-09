# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /sdk/tools/proguard/proguard-android.txt

# Keep Room entities
-keep class com.periodcalendar.tracker.models.** { *; }

# Keep Gson serialization
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.periodcalendar.tracker.models.** { *; }

# Hilt
-dontwarn dagger.internal.**
-dontwarn javax.inject.**
-dontwarn sun.misc.**
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Wear OS
-dontwarn com.google.android.wearable.**
-keep class com.google.android.wearable.** { *; }

# MPAndroidChart
-keep class com.github.mikephil.charting.** { *; }
