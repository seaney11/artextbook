package com.artextbook.artextbook.image_nodes.solar;

import android.content.Context;

import com.artextbook.artextbook.R;
import com.artextbook.artextbook.image_nodes.AugmentedImageNode;
import com.artextbook.artextbook.image_nodes.spring.SpringNode;
import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.util.concurrent.CompletableFuture;

public class SolarSystemImageNode extends AugmentedImageNode {

    CompletableFuture<ModelRenderable> sun;

    public SolarSystemImageNode(Context context, AugmentedImage image) {
        super(context, image);
    }

    @Override
    protected void loadRenderables(Context context) {
        sun = ModelRenderable.builder()
                .setSource(context, R.raw.spring)
                .build();
        completableFutures.add(sun);
    }

    @Override
    protected void createNodes(Context context) {
        Node sunNode = new Node();
        sunNode.setParent(this);
        sunNode.setRenderable(sun.getNow(null));
        nodes.add(sunNode);
    }

}
