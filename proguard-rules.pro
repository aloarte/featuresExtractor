#-keepparameternames
#-renamesourcefileattribute SourceFile
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
#
-keepclassmembernames class * { java.lang.Class class$(java.lang.String); java.lang.Class class$(java.lang.String, boolean); }
-optimizations !code/allocation/variable

-keep class javax.** { *; }
-keep class java.** { *; }
-keep class org.** { *; }

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class org.nd4j.linalg.*


-keep public class * {
    public protected <fields>;
    public protected <methods>;
}
-ignorewarnings