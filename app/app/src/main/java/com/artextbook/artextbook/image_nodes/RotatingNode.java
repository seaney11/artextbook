package com.artextbook.artextbook.image_nodes;

import android.util.Log;

import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

import java.util.concurrent.TimeUnit;

public class RotatingNode extends Node {

    private Vector3 local_position;
    int counter;

    public RotatingNode() {
        super();
        Vector3 my_vector = this.getLocalPosition();
        my_vector.x += 0.3;
        my_vector.z += 0.3;
        this.setLocalPosition(my_vector);

        counter = 0;
    }

    @Override
    public void onUpdate(FrameTime frameTime) {
        super.onUpdate(frameTime);
        Vector3 my_vector = this.getLocalPosition();
        Vector3 keep_safe = new Vector3(my_vector);
        my_vector.y += frameTime.getDeltaTime(TimeUnit.MILLISECONDS) * 0.0001;
        my_vector.x = (float) ( Math.cos(0.1) * keep_safe.x + Math.sin(0.1) * keep_safe.z);
        my_vector.z = (float) ( - Math.sin(0.1) * keep_safe.x + Math.cos(0.1) * keep_safe.z);
        this.setLocalPosition(my_vector);
        Log.d("DEBUG","X: " +  my_vector.x);
    }
}