package dk.madslee.imageSequence;

import android.graphics.drawable.AnimationDrawable;

public class CustomAnimationDrawable extends AnimationDrawable {
    private OnAnimationStateListener mListener;

    private Boolean finished = false;

    public CustomAnimationDrawable() {
    }

    public void setOnAnimationStateListener(OnAnimationStateListener listener) {
        mListener = listener;
    }

     @Override
    public boolean selectDrawable(int idx) {
        boolean ret = super.selectDrawable(idx);
        if ((idx != 0) && (idx == getNumberOfFrames() - 1)) {
            if (!finished) {
                if (mListener != null) {
                    finished = true;
                }
                mListener.onAnimationFinish();
            }
        }
        return ret;
    }

    public interface OnAnimationStateListener {
        public void onAnimationFinish();
    }
}
