package com.danilomendes.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.danilomendes.invertedtextprogressbar.R;

/**
 * ImageView that is animated like a progress view and clips
 * the text. This can be used to animate a progress like view
 * and overlap texts changing the color of the text.
 * The text and the bar are draw on canvas.
 *
 * If the pivot positions are not set, then the center of the
 * canvas will be used.
 *
 * Created by danilo on 24-03-2016.
 */
public class InvertedTextProgressbar extends ImageView {

    /**
     * Text displayed in the progress bar.
     */
    private String mText = "";

    /**
     * Uninitialized value.
     */
    private static final int UNINITIALIZED_START_TIME = -1;

    /**
     * The default text size set in the text paint.
     */
    private static final int DEFAULT_TEXT_SIZE = 12;

    /**
     * Rectangle to draw on canvas.
     */
    private Rect mRect = new Rect();

    /**
     * Flag that indicates if the bar is being animated.
     */
    private boolean mIsAnimating = false;

    private long mStartTime;
    private int mDurationMs;
    private long endTime;

    private int mMaxProgress = -1;
    private int mMinProgress = -1;
    private int mCurrProgress = -1;

    /**
     * X position of the text to be draw.
     */
    private int mPosX = -1;

    /**
     * Y position of the text to be draw.
     */
    private int mPosY = -1;

    /**
     * Paint to use for drawing the text.
     */
    private Paint mTextPaint;

    /**
     * Paint to use for drawing the text.
     */
    private Paint mTextInvertedPaint;

    /**
     * Callback observer.
     */
    private Callback mCallback;

    public InvertedTextProgressbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initComponents(context, attrs, defStyle, 0);
    }

    public InvertedTextProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponents(context, attrs, 0, 0);
    }

    public InvertedTextProgressbar(Context context) {
        super(context);
    }

    public InvertedTextProgressbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initComponents(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Initializes the text paint. This has a fix size.
     *
     * @param attrs The XML attributes to use.
     */
    private void initComponents(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InvertedTextProgressbar, defStyleAttr, defStyleRes);
        mTextPaint = new Paint();

        // Define the normal text paint.
        mTextPaint.setColor(typedArray.getColor(R.styleable.InvertedTextProgressbar_text_color, Color.BLACK));
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(typedArray.getDimensionPixelSize(R.styleable.InvertedTextProgressbar_text_size,
                context.getResources().getDimensionPixelSize(R.dimen.text_size_default)));
        mTextPaint.setTypeface(Typeface.defaultFromStyle(typedArray.getInteger(
                R.styleable.InvertedTextProgressbar_text_typeface, Typeface.defaultFromStyle(Typeface.NORMAL).getStyle())));
        mTextPaint.setTextAlign(Paint.Align.CENTER); // Text draw is started in the middle
        mTextPaint.setLinearText(true);
        mTextPaint.setAntiAlias(true);

        // Define the inverted text paint.
        mTextInvertedPaint = new Paint(mTextPaint);
        mTextInvertedPaint.setColor(typedArray.getColor(R.styleable.InvertedTextProgressbar_text_inverted_color, Color.WHITE));

        // Define the text.
        mText = typedArray.getString(R.styleable.InvertedTextProgressbar_text);
        if (mText == null) {
            mText = "Loading...";
        }

        // Recycle the TypedArray.
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.getClipBounds(mRect);

        if (mPosX == -1) {
            mPosX = (getWidth() / 2);
        }

        if (mPosY == -1) {
            mPosY = (int) ((getHeight() / 2) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2));
        }

        // Draw text to overlap.
        if (!mText.isEmpty()) {
            canvas.drawText(mText, mPosX, mPosY, mTextPaint);
        }

        if (mIsAnimating) {
            // Only start timing from first frame of animation
            if (mStartTime == UNINITIALIZED_START_TIME) {
                mStartTime = SystemClock.uptimeMillis();
                endTime = mStartTime + mDurationMs;
            }

            // Adjust clip bounds according to the time fraction
            long currentTime = SystemClock.uptimeMillis();
            if (currentTime < endTime) {
                float timeFraction = (currentTime - mStartTime) / (mDurationMs * 1f);
                int alpha = Math.round(mRect.width() * timeFraction);
                mRect.right = mRect.left + alpha;
                canvas.clipRect(mRect);
            } else {
                mIsAnimating = false;
                if (mCallback != null) {
                    mCallback.onAnimationEnd();
                }
            }
        } else if (mMinProgress > -1 && mMaxProgress > mMinProgress &&
                (mCurrProgress >= mMinProgress && mCurrProgress <= mMaxProgress)) {
            mRect.right = mRect.width() * mCurrProgress / mMaxProgress; // Regra de 3 simples.
            canvas.clipRect(mRect);
        }

        // Draw current state.
        super.onDraw(canvas);

        if (!mText.isEmpty()) {
            // Draw text in position set.
            canvas.drawText(mText, mPosX, mPosY, mTextInvertedPaint);
        }

        // Request another draw operation until time is up
        if (mIsAnimating) {
            invalidate();
        }
    }

    /**
     * Sets the text that will overlay.
     *
     * @param text The text to draw.
     */
    public void setText(String text) {
        mText = text;
    }

    /**
     * Gets the current text to draw.
     *
     * @return The current text to draw.
     */
    public String getText() {
        return mText;
    }

    /**
     * Sets the X pivot position of the text to draw.
     *
     * @param posX The position to set.
     */
    public void setTextPivotX(int posX) {
        this.mPosX = posX;
    }

    /**
     * Sets the Y pivot position of the text to draw.
     *
     * @param posY The position to set.
     */
    public void setTextPivotY(int posY) {
        this.mPosY = posY;
    }

    public void setProgress(int progress) {
        mCurrProgress = progress;
        invalidate();
    }

    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
    }

    public void setMinProgress(int minProgress) {
        mMinProgress = minProgress;
    }

    /**
     * Starts the animation of the progress and text draw.
     *
     * @param durationMs The duration of the bar filling animation.
     */
    public void startAnimation(int durationMs) {
        mIsAnimating = true;
        mStartTime = UNINITIALIZED_START_TIME;
        mDurationMs = durationMs;
        invalidate();
    }

    /**
     * Interface that holds methods that are called during the
     * animation.
     */
    public interface Callback {
        void onAnimationEnd();
    }

    /**
     * Sets the callback.
     *
     * @param callback The callback to call upon events.
     */
    public void setCallback(Callback callback) {
        mCallback = callback;
    }
}
