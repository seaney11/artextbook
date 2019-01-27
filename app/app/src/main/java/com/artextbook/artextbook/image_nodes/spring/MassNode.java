package com.artextbook.artextbook.image_nodes.spring;

import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;

import java.util.concurrent.TimeUnit;

public class MassNode extends Node {

    private SpringNode spring;

    public MassNode(SpringNode spring){
        this.spring = spring;
    }

    @Override
    public void onUpdate(FrameTime frameTime) {
        super.onUpdate(frameTime);
        Vector3 pos = new Vector3(getWorldPosition());
        pos.y = spring.getWorldPosition().y + spring.getWorldScale().y * 2;
        this.setWorldPosition(pos);
    }

}
