package com.artextbook.artextbook.image_nodes.spring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.artextbook.artextbook.R;
import com.artextbook.artextbook.image_nodes.AugmentedImageNode;
import com.artextbook.artextbook.image_nodes.RotatingNode;
import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.rendering.ViewRenderable;

import java.util.concurrent.CompletableFuture;

import co.blankkeys.animatedlinegraphview.AnimatedLineGraphView;

public class SpringAnimationImageNode extends AugmentedImageNode{

    CompletableFuture<ModelRenderable> springR;
    CompletableFuture<ViewRenderable> view;
    AnimatedLineGraphView graph;
    float y[];

    public SpringAnimationImageNode(Context context, AugmentedImage image) {
        super(context, image);
    }

    @Override
    protected void loadRenderables(Context context) {
        springR = ModelRenderable.builder()
                .setSource(context, R.raw.spring)
                .build();
        completableFutures.add(springR);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View v = inflater.inflate(R.layout.graph, null, false);
//        graph = v.findViewById(R.id.graph);
//        int n = 100;
//        y = new float[n];
//
//        for (int i = 0; i < n; i++){
//            y[i] = (float)(1 + 0.5 * Math.sin(6 * Math.PI*i/n));
//        }
//        graph.setAnimationDuration((int) (6*Math.PI / SpringNode.w));
//        graph.setData(y);
        view = ViewRenderable.builder()
                .setView(context, R.layout.graph)
                .build();
        completableFutures.add(view);
    }

    @Override
    protected void createNodes(Context context) {
        SpringNode spring = new SpringNode();
        spring.setParent(this);
        spring.setRenderable(springR.getNow(null));
        nodes.add(spring);
        Vector3 localPosition = new Vector3(0.0f, 0.2f, 0.0f);
        Node viewNode = new Node();
        viewNode.setParent(this);
        viewNode.setLocalPosition(localPosition);
        viewNode.setRenderable(view.getNow(null));
        nodes.add(viewNode);
        graph.setData(y);
    }

}
