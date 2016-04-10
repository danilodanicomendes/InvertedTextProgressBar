# Inverted Text Progress bar
Android progress bar with text that, when the bar reaches the text, <b>changes the color of the overlapping text</b>.
<p align="center">
  <img src="https://cloud.githubusercontent.com/assets/6978317/14365715/10d6d732-fd07-11e5-883d-f359a005b974.gif" />
  <br/>
  <b>Sample gif</b> - Use the <a href="https://github.com/danilodanicomendes/InvertedTextProgressBar/raw/master/sample-apk/inverted-text-progressbar-sample.apk">sample.apk</a> for better quality.
</p>

## Code usage
Example of usage of the lib in xml layout (all of this can be done programmatically):
```xml
<com.danilomendes.progressbar.InvertedTextProgressbar
                android:id="@+id/itp_progress_2"
                android:layout_width="match_parent"
                android:layout_height="48dp"
                android:src="@drawable/background_orange"
                android:onClick="onClick"
                lib:text="Colors and sizes"
                lib:text_color="#666666"
                lib:text_inverted_color="#FFFFFF"
                lib:text_size="18sp"/>
```
## How to use in project
#####*Option 1 - JCenter or maven*
```gradle
    compile 'com.danilomendes.progressbar:progressbar:1.0.2'
```
#####*Option 3 - Import project*
Import the lib "<a href="https://github.com/danilodanicomendes/InvertedTextProgressBar/tree/master/progressbar">progress</a>" or embed it into your project with your tweaks as you like.
