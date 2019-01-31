package cn.ljp.xuanwoanim;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView mIvFlower1;
    private ImageView mIvFlower2;
    private RotateCircleView mRotateView;
    private Context mContext;
    private boolean isStopAnim = false;
    private List<String> imagePathList = new ArrayList<>();
    private Handler mHandler = new Handler();
    private RelativeLayout mRlAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        mRotateView = findViewById(R.id.rotate_view);
        mIvFlower2 = findViewById(R.id.iv_flower2);
        mIvFlower1 = findViewById(R.id.iv_flower1);
        mRlAnim = findViewById(R.id.rl_anim);

        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAnim();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopIvAnim();
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            imagePathList.add("");
        }

    }

    private void startAnim() {
        isStopAnim = false;
        mRotateView.startAnim();
        Animation animation1 = AnimationUtils.loadAnimation(mContext, R.anim.anim_compose_guide_flower1);
        LinearInterpolator lir = new LinearInterpolator();
        animation1.setInterpolator(lir);
        Animation animation2 = AnimationUtils.loadAnimation(mContext, R.anim.anim_compose_guide_flower2);
        animation2.setInterpolator(lir);
        mIvFlower1.startAnimation(animation1);
        mIvFlower2.startAnimation(animation2);

        addViewStartAnim(0);
    }

    private void stopIvAnim() {
        isStopAnim = true;
        mRotateView.stopAnim();
        mIvFlower1.clearAnimation();
        mIvFlower2.clearAnimation();
    }


    private void addViewStartAnim(int position) {
        if (isStopAnim) {
            return;
        }
        if (position >= imagePathList.size()) {
            addViewStartAnim(0);
            return;
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_add_photo_item, null);
        int width = ScreenUtil.dip2px(mContext, 300);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, width);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        view.setLayoutParams(layoutParams);
        ImageView ivPhoto = view.findViewById(R.id.iv_photo);

        ivPhoto.setImageResource(R.mipmap.ic_launcher);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.compose_photo_rotation_scale);
        animation.setAnimationListener(new AnimListener());
        addRlAnimView(view);

        animation.setFillAfter(true);
        view.startAnimation(animation);
        position++;
        int pos = position;
        mHandler.postDelayed(() -> addViewStartAnim(pos), 500);

    }

    public void addRlAnimView(View view) {
        mRlAnim.addView(view);
    }


    private class AnimListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //需要在动画结束后删除view  否则view会越积攒越多

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
