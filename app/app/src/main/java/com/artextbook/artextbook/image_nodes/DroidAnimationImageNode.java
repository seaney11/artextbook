package com.artextbook.artextbook.image_nodes;

import android.content.Context;

import com.artextbook.artextbook.R;
import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

public class DroidAnimationImageNode extends AugmentedImageNode{

    CompletableFuture<Material> mat;

    public DroidAnimationImageNode(Context context, AugmentedImage image) {
        super(context, image);
    }

    @Override
    protected void createNodes(Context context) {
        ModelRenderable sphere = ShapeFactory.makeSphere(0.01f, new Vector3(0.0f, 0.0f, 0.0f), mat.getNow(null));
        RotatingNode particle = new RotatingNode(6000, 6000);
        particle.setParent(this);
        particle.setRenderable(sphere);
        nodes.add(particle);
        RotatingNode particle2 = new RotatingNode(3000, 6000);
        particle2.setParent(this);
        particle2.setRenderable(sphere);
        nodes.add(particle2);
    }

    @Override
    protected void loadRenderables(Context context) {
        mat = MaterialFactory.makeOpaqueWithColor(context, new Color(android.graphics.Color.GRAY));
        completableFutures.add(mat);
    }
}
