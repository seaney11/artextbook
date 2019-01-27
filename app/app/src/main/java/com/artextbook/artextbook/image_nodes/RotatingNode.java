package com.artextbook.artextbook.image_nodes;

import android.util.Log;

import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

import java.util.concurrent.TimeUnit;

public class RotatingNode extends Node {

    private Vector3 initialPos;
    int maxResetTime;
    int modTime;
    int counter;

    public RotatingNode(int maxResetTime, int modTime) {
        super();
        this.maxResetTime = maxResetTime;
        this.modTime = modTime;
        Vector3 my_vector = this.getLocalPosition();
        my_vector.x += 0.1;
        my_vector.z += 0.1;
        this.setLocalPosition(my_vector);
        initialPos = my_vector;
        counter = 0;
    }

    @Override
    public void onUpdate(FrameTime frameTime) {
        super.onUpdate(frameTime);
        long ms = frameTime.getDeltaTime(TimeUnit.MILLISECONDS);
        if (((counter + ms) % modTime > maxResetTime && (counter % modTime) < maxResetTime) || (counter + ms - maxResetTime) % modTime < (counter - maxResetTime) % modTime){
            this.setLocalPosition(initialPos);
        }
        counter += ms;
        Vector3 my_vector = this.getLocalPosition();
        Vector3 keep_safe = new Vector3(my_vector);
        my_vector.y += ms * 0.0001;
        my_vector.x = (float) ( Math.cos(0.1) * keep_safe.x + Math.sin(0.1) * keep_safe.z);
        my_vector.z = (float) ( - Math.sin(0.1) * keep_safe.x + Math.cos(0.1) * keep_safe.z);
        this.setLocalPosition(my_vector);
    }
}