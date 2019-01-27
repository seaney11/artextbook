package com.artextbook.artextbook.image_nodes.spring;

import android.util.Log;

import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;

import java.util.concurrent.TimeUnit;

public class SpringNode extends Node {

    public static final double w = 0.002;
    double x;

    public SpringNode() {
        super();
        setLocalScale(new Vector3(0.1f, 0.1f, 0.1f));
        x = 0;
    }

    @Override
    public void onUpdate(FrameTime frameTime) {
        super.onUpdate(frameTime);
        x += frameTime.getDeltaTime(TimeUnit.MILLISECONDS);
        Vector3 scale = new Vector3(0.1f, (float) (0.1 + 0.05 * Math.sin(x * w)), 0.1f);
        this.setLocalScale(scale);
    }
}
