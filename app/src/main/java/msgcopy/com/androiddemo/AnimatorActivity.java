package msgcopy.com.androiddemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AnimatorActivity extends AppCompatActivity {
    private static final String TAG = "AnimatorActivity";
    private RelativeLayout viewLayout;
    private ImageView liveAnimateList;
    private ImageView liveAnimate;
    private int ImageWidth = 150;
    private int minImageWidth = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        liveAnimateList = (ImageView) findViewById(R.id.live_animate_list);
        liveAnimate = (ImageView) findViewById(R.id.live_animate);
        viewLayout = (RelativeLayout) findViewById(R.id.viewLayout);
        liveAnimateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.live_animate);
                liveAnimateList.setImageDrawable(animationDrawable);
                animationDrawable.start();
            }
        });
        liveAnimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView likeImg = new ImageView(AnimatorActivity.this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(CommonUtil.dip2px(AnimatorActivity.this, minImageWidth), CommonUtil.dip2px(AnimatorActivity.this, minImageWidth));
                params.topMargin = CommonUtil.getScreenHeight(AnimatorActivity.this) - CommonUtil.dip2px(AnimatorActivity.this, minImageWidth);
                likeImg.setLayoutParams(params);
                likeImg.setImageResource(R.drawable.live_animate_heart);
                viewLayout.addView(likeImg);
                startLiveAnimateStyleOne(likeImg);
            }
        });
        viewLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ImageView likeImg = new ImageView(AnimatorActivity.this);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(CommonUtil.dip2px(AnimatorActivity.this, ImageWidth), CommonUtil.dip2px(AnimatorActivity.this, ImageWidth));
                        params.leftMargin = (int) event.getX() - CommonUtil.dip2px(AnimatorActivity.this, ImageWidth) / 2;
                        params.topMargin = (int) event.getY() - CommonUtil.dip2px(AnimatorActivity.this, ImageWidth) / 2;
                        likeImg.setPadding(10, 10, 10, 10);
                        likeImg.setLayoutParams(params);
                        likeImg.setImageResource(R.drawable.live_animate_heart);
                        viewLayout.addView(likeImg);
                        startAnimatorStyleOne(likeImg);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e(TAG, "onTouch: ACTION_MOVE");

                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e(TAG, "onTouch: ACTION_UP");

                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.e(TAG, "onTouch: ACTION_CANCEL ");

                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 点赞烟花效果
     *
     * @param liveAnimate
     */
    private void startLiveAnimateStyleOne(final ImageView liveAnimate) {

        int rotation = (int) (Math.random() * 15);
        ObjectAnimator rotationAnimaotr = ObjectAnimator.ofFloat(liveAnimate, "rotation", 0, rotation, rotation * 2);

        int translationX = (int) (Math.random() * 100);
        int translationY = -CommonUtil.getScreenHeight(AnimatorActivity.this) / 2 + (int) (Math.random() * CommonUtil.getScreenHeight(AnimatorActivity.this) / 5);
        ObjectAnimator translationXAnimaotr = ObjectAnimator.ofFloat(liveAnimate, "translationX", 0, CommonUtil.dip2px(AnimatorActivity.this, translationX));
        ObjectAnimator translationYAnimaotr = ObjectAnimator.ofFloat(liveAnimate, "translationY", 0, translationY);

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(liveAnimate, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(liveAnimate, "scaleY", 0.5f, 1f);

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(liveAnimate, "alpha", 1.0f, 1.0f, 1.0f, 0.8f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotationAnimaotr, translationXAnimaotr, translationYAnimaotr, scaleXAnimator, scaleYAnimator, alphaAnimator);
        animatorSet.setDuration(1000);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewLayout.removeView(liveAnimate);
            }
        });
    }

    /**
     * AnimatorSet实现组合动画
     * AnimatorSet可以指定动画同时或按顺序执行
     */
    private void startAnimatorStyleOne(final ImageView liveAnimate) {
        liveAnimate.setVisibility(View.VISIBLE);
        //实现旋转动画
        ObjectAnimator rotationAnimaotr = ObjectAnimator.ofFloat(liveAnimate, "rotation", 60f, 0f, 0f);
        //缩放动画
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(liveAnimate, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(liveAnimate, "scaleY", 1f, 0.5f);
        //透明度动画
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(liveAnimate, "alpha", 0.1f, 1f);
        //然后通过AnimatorSet把几种动画组合起来
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimator).with(scaleYAnimator)
                .with(alphaAnimator);
        //设置动画时间
        animatorSet.setDuration(100);
        //开始动画
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                liveAnimate.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stopAnimatorStyleOne(liveAnimate);
                    }
                }, 500);
            }
        });
    }

    private void stopAnimatorStyleOne(final ImageView liveAnimate) {
        //缩放动画
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(liveAnimate, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(liveAnimate, "scaleY", 0.5f, 1f);
        //透明度动画
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(liveAnimate, "alpha", 1f, 0.1f);
        //然后通过AnimatorSet把几种动画组合起来
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimator).with(scaleYAnimator)
                .with(alphaAnimator);
        //设置动画时间
        animatorSet.setDuration(300);
        //开始动画
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                liveAnimate.setVisibility(View.GONE);
            }
        });
    }
}
