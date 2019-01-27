package com.artextbook.artextbook.image_nodes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.artextbook.artextbook.R;
import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ViewRenderable;

import java.util.concurrent.CompletableFuture;

public class GraphImageNode extends AugmentedImageNode {

    CompletableFuture<ViewRenderable> viewRenderable;

    public GraphImageNode(Context context, AugmentedImage image) {
        super(context, image);
    }

    public void loadRenderables(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.graph, null, false);
        viewRenderable = ViewRenderable.builder()
                .setView(context, v)
                .build();
        completableFutures.add(viewRenderable);
    }

    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    public void createNodes() {
        // Make the 4 corner nodes.
        Vector3 localPosition = new Vector3();
        Node viewNode = new Node();
        viewNode.setParent(this);
        viewNode.setLocalPosition(localPosition);
        viewNode.setRenderable(viewRenderable.getNow(null));
        nodes.add(viewNode);
    }
}
