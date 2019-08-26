package com.moonfleet.movies.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.moonfleet.movies.R;

public class FixedRatioLayout extends FrameLayout {

    private float widthRatio = 1;
    private float heightRatio = 1;

    public FixedRatioLayout(Context context) {
        super(context);
    }

    public FixedRatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FixedRatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FixedRatioLayout,
                0, 0);

        try {
            widthRatio = a.getFloat(R.styleable.FixedRatioLayout_widthRatio, 1);
            heightRatio = a.getFloat(R.styleable.FixedRatioLayout_heightRatio, 1);
        } finally {
            a.recycle();
        }
    }

	public void setWidthRatio(float widthRatio) {
		this.widthRatio = widthRatio;
	}

	public void setHeightRatio(float heightRatio) {
		this.heightRatio = heightRatio;
		requestLayout();
	}

	public float getWidthRatio() {
		return widthRatio;
	}

	public float getHeightRatio() {
		return heightRatio;
	}

	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (maxHeight == 0) {
            maxHeight = (int) getHeightFromWidth(maxWidth);
        }

        if (maxWidth == 0) {
            maxWidth = (int) getWidthFromHeight(maxHeight);
        }

        float chosenWidth;
        float chosenHeight;

        float possibleWidth = getWidthFromHeight(maxHeight);
        float possibleHeight = getHeightFromWidth(maxWidth);

        if (possibleWidth > maxWidth) {
            chosenHeight = possibleHeight;
            chosenWidth = getWidthFromHeight(possibleHeight);
        } else {
            chosenHeight = getHeightFromWidth(possibleWidth);
            chosenWidth = possibleWidth;
        }

        int finalWidthMeasureSpec = resolveSize((int) chosenWidth, widthMeasureSpec);
        int finalHeightMeasureSpec = resolveSize((int) chosenHeight, heightMeasureSpec);
        setMeasuredDimension(
                finalWidthMeasureSpec,
                finalHeightMeasureSpec);

        super.onMeasure(
                MeasureSpec.makeMeasureSpec((int) chosenWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec((int) chosenHeight, MeasureSpec.EXACTLY));
    }

    private float getHeightFromWidth(float width) {
        return width * (heightRatio / widthRatio);
    }

    private float getWidthFromHeight(float height) {
        return height * (widthRatio / heightRatio);
    }
}
