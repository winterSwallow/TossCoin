package com.winter.tosscoin;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.appcompat.widget.AppCompatImageView;

import java.util.HashSet;
import java.util.Set;

public class TossImageView extends AppCompatImageView {

    public static final int RESULT_FRONT = 1; // 正面
    public static final int RESULT_REVERSE = -1; // 反面

    /**
     * 圈数
     */
    private int mCircleCount;
    /**
     * x轴旋转方向
     */
    private int mXAxisDirection;
    /**
     * y轴旋转方向
     */
    private int mYAxisDirection;
    /**
     * z轴旋转方向
     */
    private int mZAxisDirection;
    /**
     * 抛硬币的结果
     */
    private int mResult;
    /**
     * 动画时间
     */
    private int mDuration;
    /**
     * 延迟时间
     */
    private int mStartOffset;
    /**
     * Interpolator
     */
    private Interpolator mInterpolator = new DecelerateInterpolator();

    /**
     * 硬币的正面
     */
    private Drawable mFrontDrawable;

    /**
     * 硬币的反面
     */
    private Drawable mReversetDrawable;

    /**
     * 抛硬币的回调函数
     */
    private TossAnimation.TossAnimationListener mTossAnimationListener;

    private Set<Animation> mOtherAnimation = new HashSet<Animation>();

    public TossImageView(Context context) {
        this(context, null);
    }

    public TossImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TossImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TossImageView);

        mCircleCount = typedArray.getInteger(R.styleable.TossImageView_circleCount, context.getResources().getInteger(R.integer.default_circleCount));
        mXAxisDirection = typedArray.getInteger(R.styleable.TossImageView_xAxisDirection, context.getResources().getInteger(R.integer.default_xAxisDirection));
        mYAxisDirection = typedArray.getInteger(R.styleable.TossImageView_yAxisDirection, context.getResources().getInteger(R.integer.default_yAxisDirection));
        mZAxisDirection = typedArray.getInteger(R.styleable.TossImageView_zAxisDirection, context.getResources().getInteger(R.integer.default_zAxisDirection));
        mResult = typedArray.getInteger(R.styleable.TossImageView_result, context.getResources().getInteger(R.integer.default_result));

        mFrontDrawable = typedArray.getDrawable(R.styleable.TossImageView_frontDrawable);
        mReversetDrawable = typedArray.getDrawable(R.styleable.TossImageView_reverseDrawable);

        mDuration = typedArray.getInteger(R.styleable.TossImageView_duration, context.getResources().getInteger(R.integer.default_duration));
        mStartOffset = typedArray.getInteger(R.styleable.TossImageView_startOffset, context.getResources().getInteger(R.integer.default_startOffset));

        typedArray.recycle();
        setCoinDrawableIfNecessage();
    }

    /**
     * 设置mFrontDrawable和mReversetDrawable的值
     */
    private void setCoinDrawableIfNecessage() {
        if (mFrontDrawable == null) {
            mFrontDrawable = getDrawable();
        }
        if (mReversetDrawable == null) {
            mReversetDrawable = getDrawable();
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        setCoinDrawableIfNecessage();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        setCoinDrawableIfNecessage();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        setCoinDrawableIfNecessage();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        setCoinDrawableIfNecessage();
    }

    /**
     * 设置硬币旋转的圈数
     *
     * @param circleCount
     * @return
     */
    public TossImageView setCircleCount(int circleCount) {
        this.mCircleCount = circleCount;
        return this;
    }

    /**
     * 设置x轴旋转方向
     *
     * @param xAxisDirection TossAnimation.DIRECTION_NONE  or  TossAnimation.DIRECTION_CLOCKWISE  or  TossAnimation.DIRECTION_ABTUCCLOCKWISE
     * @return
     */
    public TossImageView setXAxisDirection(int xAxisDirection) {
        if (Math.abs(xAxisDirection) > 1) {
            throw new RuntimeException("Math.abs(Direction) must be less than 1");
        }
        this.mXAxisDirection = xAxisDirection;
        return this;
    }

    /**
     * 设置y轴旋转方向
     *
     * @param yAxisDirection TossAnimation.DIRECTION_NONE  or  TossAnimation.DIRECTION_CLOCKWISE  or  TossAnimation.DIRECTION_ABTUCCLOCKWISE
     * @return
     */
    public TossImageView setYAxisDirection(int yAxisDirection) {
        if (Math.abs(yAxisDirection) > 1) {
            throw new RuntimeException("Math.abs(Direction) must be less than 1");
        }
        this.mYAxisDirection = yAxisDirection;
        return this;
    }

    /**
     * 设置z轴选装方向
     *
     * @param zAxisDirection TossAnimation.DIRECTION_NONE  or  TossAnimation.DIRECTION_CLOCKWISE  or  TossAnimation.DIRECTION_ABTUCCLOCKWISE
     * @return
     */
    public TossImageView setZAxisDirection(int zAxisDirection) {
        if (Math.abs(zAxisDirection) > 1) {
            throw new RuntimeException("Math.abs(Direction) must be less than 1");
        }
        this.mZAxisDirection = zAxisDirection;
        return this;
    }

    /**
     * 设置抛硬币的结果
     *
     * @param result TossAnimation.RESULT_FRONT  正面
     *               TossAnimation.RESULT_REVERSE  反面
     * @return
     */
    public TossImageView setResult(int result) {
        if (Math.abs(result) != 1) {
            throw new RuntimeException("Math.abs(Direction) must be 1");
        }
        this.mResult = result;
        return this;
    }

    /**
     * 设置动画持续时间
     *
     * @param duration
     * @return
     */
    public TossImageView setDuration(int duration) {
        this.mDuration = duration;
        return this;
    }

    /**
     * set Interpolator
     *
     * @param interpolator
     * @return
     */
    public TossImageView setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
        return this;
    }

    /**
     * 添加一个Animation
     *
     * @param animation
     * @return
     */
    public TossImageView addOtherAnimation(Animation animation) {
        mOtherAnimation.add(animation);
        return this;
    }

    /**
     * 清空Animation
     *
     * @return
     */
    public TossImageView cleareOtherAnimation() {
        mOtherAnimation.clear();
        return this;
    }

    /**
     * 设置动画回调接口
     *
     * @param tossAnimationListener
     * @return
     */
    public TossImageView setTossAnimationListener(TossAnimation.TossAnimationListener tossAnimationListener) {
        this.mTossAnimationListener = tossAnimationListener;
        return this;
    }

    /**
     * 开始抛硬币动画
     */
    public void startToss() {

        clearAnimation();

        TossAnimation tossAnimation = new TossAnimation(mCircleCount, mXAxisDirection, mYAxisDirection, mZAxisDirection, mResult);
        tossAnimation.setDuration(mDuration);
        tossAnimation.setStartOffset(mStartOffset);
        tossAnimation.setInterpolator(mInterpolator);
        tossAnimation.setTossAnimationListener(new QTTossAnimationListener(mTossAnimationListener));

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(tossAnimation);

        for (Animation animation : mOtherAnimation) {
            animationSet.addAnimation(animation);
        }
        startAnimation(animationSet);
    }

    /**
     * 可以改变ImageView的监听器
     */
    public class QTTossAnimationListener implements TossAnimation.TossAnimationListener {

        private TossAnimation.TossAnimationListener mTossAnimationListener;

        public QTTossAnimationListener(TossAnimation.TossAnimationListener tossAnimationListener) {
            mTossAnimationListener = tossAnimationListener;
        }

        @Override
        public void onDrawableChange(int result, TossAnimation animation) {
            switch (result) {
                case RESULT_FRONT:
                    setImageDrawable(mFrontDrawable);
                    break;
                case RESULT_REVERSE:
                    setImageDrawable(mReversetDrawable);
                    break;
            }
            if (mTossAnimationListener != null) {
                mTossAnimationListener.onDrawableChange(result, animation);
            }
        }

        @Override
        public void onAnimationStart(Animation animation) {
            if (mTossAnimationListener != null) {
                mTossAnimationListener.onAnimationStart(animation);
            }
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (mTossAnimationListener != null) {
                mTossAnimationListener.onAnimationEnd(animation);
            }
            Log.e("qingtian", "end");
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            if (mTossAnimationListener != null) {
                mTossAnimationListener.onAnimationRepeat(animation);
            }
        }
    }
}
