package dk.madslee.imageSequence;

import android.os.Handler;
import android.graphics.drawable.AnimationDrawable;

public class CustomAnimationDrawable extends AnimationDrawable {
    private OnAnimationStateListener mListener;
    private Handler mHandler;

    public CustomAnimationDrawable() {
        mHandler = new Handler();
    }

    public void setOnAnimationStateListener(OnAnimationStateListener listener) {
        mListener = listener;
    }

    @Override
    public void start() {
        super.start();
        if(mListener != null) {
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    mListener.onAnimationFinish();
                }
            }, getTotalDuration());
        }
    }

    public int getTotalDuration() {
        int duration = 0;
        for (int i = 0; i < this.getNumberOfFrames(); i++) {
            duration += this.getDuration(i);
        }
        return duration;
    }

    public interface OnAnimationStateListener {
        public void onAnimationFinish();
    }
}
