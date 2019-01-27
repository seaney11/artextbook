package com.artextbook.artextbook.image_nodes.solar;

import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;

import java.util.concurrent.TimeUnit;

public class PlanetNode extends Node {

    double speed;

    public PlanetNode(double radius, double angle, double speed, float size) {
        super();
        radius = 0.4 * radius;
        Vector3 vector = new Vector3((float)(radius * Math.cos(angle)), 0, (float)(radius * Math.sin(angle)));
        this.setLocalScale(new Vector3(size, size, size));
        this.speed = speed;
        this.setLocalPosition(vector);
    }

    @Override
    public void onUpdate(FrameTime frameTime) {
        super.onUpdate(frameTime);
        Vector3 my_vector = this.getLocalPosition();
        Vector3 keep_safe = new Vector3(my_vector);
        my_vector.x = (float) ( Math.cos(0.01 * speed) * keep_safe.x + Math.sin(0.01 * speed) * keep_safe.z);
        my_vector.z = (float) ( - Math.sin(0.01 * speed) * keep_safe.x + Math.cos(0.01 * speed) * keep_safe.z);
        this.setLocalPosition(my_vector);
    }

}
