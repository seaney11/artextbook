package com.artextbook.artextbook.image_nodes;

import android.content.Context;

import com.artextbook.artextbook.R;
import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.util.concurrent.CompletableFuture;

public class DroidAnimationImageNode extends AugmentedImageNode{

    CompletableFuture<ModelRenderable> andyR;

    public DroidAnimationImageNode(Context context, AugmentedImage image) {
        super(context, image);
    }

    @Override
    protected void createNodes() {
        RotatingNode andy = new RotatingNode();
        andy.setParent(this);
        andy.setRenderable(andyR.getNow(null));
        nodes.add(andy);
    }

    @Override
    protected void loadRenderables(Context context) {
        andyR = ModelRenderable.builder()
                .setSource(context, R.raw.andy)
                .build();
        completableFutures.add(andyR);
    }
}
