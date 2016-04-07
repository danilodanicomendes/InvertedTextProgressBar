# Inverted Text Progress bar
Android progress bar with text that, when the bar reaches the text, <b>changes the color of the overlapping text</b>.
<p align="center">
  <img src="https://cloud.githubusercontent.com/assets/6978317/14365715/10d6d732-fd07-11e5-883d-f359a005b974.gif" />
  <br/>
  <b>Sample gif</b> - Use the sample.apk for better quality.
</p>

Example of usage of the lib in xml layout:
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


Import the lib "<a href="https://github.com/danilodanicomendes/InvertedTextProgressBar/tree/master/progressbar">progress</a>" or embed it into your project with your tweaks as you like.
