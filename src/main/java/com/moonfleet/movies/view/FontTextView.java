package com.moonfleet.movies.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.moonfleet.movies.R;

import java.util.HashMap;

public class FontTextView extends AppCompatTextView {

    private static final String TAG = FontTextView.class.getSimpleName();

    private static HashMap<String, Typeface> mFontMap;

    public FontTextView(Context context) {
        this(context, null);
        setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);

        String fontName = null;

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FontTextView,
                0, 0);

        if (attrs != null) {
            try {
                fontName = a.getString(R.styleable.FontTextView_typeface);
                if (fontName != null && !isInEditMode()) {
                    setTypeface(getStaticTypeFace(context, fontName));
                }
            } catch (IllegalArgumentException e) {
                try {
                    int fontNameRes = attrs.getAttributeResourceValue("app", "font", -1);
                    if (fontNameRes != -1) {
                        fontName = context.getString(fontNameRes);
                        if (!isInEditMode()) {
                            setTypeface(getStaticTypeFace(context, fontName));
                        }
                    }
                } catch (IllegalArgumentException f) {
                    f.printStackTrace();
                }
            }
        }
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);

        String fontName = null;

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FontTextView,
                0, 0);
        if (attrs != null) {
            try {
                fontName = a.getString(R.styleable.FontTextView_typeface);

                if (fontName != null && !isInEditMode()) {
                    setTypeface(getStaticTypeFace(context, fontName));
                }
            } catch (IllegalArgumentException e) {
                try {
                    int fontNameRes = attrs.getAttributeResourceValue("app", "font", -1);
                    if (fontNameRes != -1) {
                        fontName = context.getString(fontNameRes);
                        if (!isInEditMode()) {
                            setTypeface(getStaticTypeFace(context, fontName));
                        }
                    }
                } catch (IllegalArgumentException f) {
                    f.printStackTrace();
                }
            }
        }
    }

    public static Typeface getStaticTypeFace(Context context, String fontFileName) {
        return Fonts.loadTypeface(context.getAssets(), fontFileName);
    }

}