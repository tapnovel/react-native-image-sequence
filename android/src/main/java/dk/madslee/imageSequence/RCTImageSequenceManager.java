package dk.madslee.imageSequence;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.common.MapBuilder;

import java.util.ArrayList;
import java.util.Map;

public class RCTImageSequenceManager extends SimpleViewManager<RCTImageSequenceView> {
    @Override
    public String getName() {
        return "RCTImageSequence";
    }

    @Override
    protected RCTImageSequenceView createViewInstance(ThemedReactContext reactContext) {
        return new RCTImageSequenceView(reactContext);
    }

    /**
     * sets the speed of the animation.
     *
     * @param view
     * @param framesPerSecond
     */
    @ReactProp(name = "framesPerSecond")
    public void setFramesPerSecond(final RCTImageSequenceView view, Integer framesPerSecond) {
        view.setFramesPerSecond(framesPerSecond);
    }

    /**
     * @param view
     * @param images an array of ReadableMap's {uri: "http://...."} return value of the resolveAssetSource(....)
     */
    @ReactProp(name = "images")
    public void setImages(final RCTImageSequenceView view, ReadableArray images) {
        ArrayList<String> uris = new ArrayList<>();
        for (int index = 0; index < images.size(); index++) {
            ReadableMap map = images.getMap(index);
            uris.add(map.getString("uri"));
        }

        view.setImages(uris);
    }

    /**
     * sets if animations is looped indefinitely.
     *
     * @param view
     * @param loop
     */
    @ReactProp(name = "loop")
    public void setLoop(final RCTImageSequenceView view, Boolean loop) {
        view.setLoop(loop);
    }


    /**
     * sets if animations is started.
     *
     * @param view
     * @param start
     */
    @ReactProp(name = "start")
    public void setStart(final RCTImageSequenceView view, Boolean start) {
        view.setStart(start);
    }

    @Override
    public Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of("onAnimationFinish", MapBuilder.of("registrationName", "onAnimationFinish"));
    }
}
