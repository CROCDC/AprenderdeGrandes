
##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Application classes that will be serialized/deserialized over Gson
-keep class com.cr.o.cdc.aprenderdegrandes.repos.model.Card

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken

##---------------Begin: proguard configuration for Crashlytics  ----------
##-keepattributes *Annotation* ##already in GSON
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

# Prevent proguard from stripping GooglePlayServices ads class
-keep class com.google.android.gms.ads.** { *; }