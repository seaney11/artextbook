package com.artextbook.artextbook.image_nodes.solar;

import android.content.Context;

import com.artextbook.artextbook.R;
import com.artextbook.artextbook.image_nodes.AugmentedImageNode;
import com.artextbook.artextbook.image_nodes.spring.SpringNode;
import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.util.concurrent.CompletableFuture;

public class SolarSystemImageNode extends AugmentedImageNode {

    CompletableFuture<ModelRenderable> sun;
    CompletableFuture<ModelRenderable> mercury;
    CompletableFuture<ModelRenderable> venus;
    CompletableFuture<ModelRenderable> earth;
    CompletableFuture<ModelRenderable> mars;
    CompletableFuture<ModelRenderable> jupiter;
    CompletableFuture<ModelRenderable> saturn;
    CompletableFuture<ModelRenderable> neptune;
    CompletableFuture<ModelRenderable> uranus;

    public SolarSystemImageNode(Context context, AugmentedImage image) {
        super(context, image);
    }

    @Override
    protected void loadRenderables(Context context) {
        sun = ModelRenderable.builder()
                .setSource(context, R.raw.sol)
                .build();
        completableFutures.add(sun);
        mercury = ModelRenderable.builder()
                .setSource(context, R.raw.mercury)
                .build();
        completableFutures.add(mercury);
        venus = ModelRenderable.builder()
                .setSource(context, R.raw.venus)
                .build();
        completableFutures.add(venus);
        earth = ModelRenderable.builder()
                .setSource(context, R.raw.earth)
                .build();
        completableFutures.add(earth);
        mars = ModelRenderable.builder()
                .setSource(context, R.raw.mars)
                .build();
        completableFutures.add(mars);
        jupiter = ModelRenderable.builder()
                .setSource(context, R.raw.jupiter)
                .build();
        completableFutures.add(jupiter);
        saturn = ModelRenderable.builder()
                .setSource(context, R.raw.saturn)
                .build();
        completableFutures.add(saturn);
        neptune = ModelRenderable.builder()
                .setSource(context, R.raw.neptune)
                .build();
        completableFutures.add(neptune);
        uranus = ModelRenderable.builder()
                .setSource(context, R.raw.uranus)
                .build();
        completableFutures.add(uranus);
    }

    @Override
    protected void createNodes(Context context) {
        Node sunNode = new Node();
        sunNode.setLocalScale(new Vector3(0.35f, 0.35f, 0.35f));
        sunNode.setParent(this);
        sunNode.setRenderable(sun.getNow(null));
        nodes.add(sunNode);
        PlanetNode mercuryNode = new PlanetNode(0.4, 0.2, 5, 0.02f);
        mercuryNode.setParent(this);
        mercuryNode.setRenderable(mercury.getNow(null));
        nodes.add(mercuryNode);
        PlanetNode venusNode = new PlanetNode(0.7, 0.8, 1.6, 0.05f);
        venusNode.setParent(this);
        venusNode.setRenderable(venus.getNow(null));
        nodes.add(venusNode);
        PlanetNode earthNode = new PlanetNode(1, 0.5, 1, 0.05f);
        earthNode.setParent(this);
        earthNode.setRenderable(earth.getNow(null));
        nodes.add(earthNode);
        PlanetNode marsNode = new PlanetNode(1.5, 1.0, 0.53, 0.027f);
        marsNode.setParent(this);
        marsNode.setRenderable(mars.getNow(null));
        nodes.add(marsNode);
        PlanetNode jupiterNode = new PlanetNode(2.2, 0.7, 0.084, 0.16f);
        jupiterNode.setParent(this);
        jupiterNode.setRenderable(jupiter.getNow(null));
        nodes.add(jupiterNode);
        PlanetNode saturnNode = new PlanetNode(3.5, 1.2, 0.034, 0.133f);
        saturnNode.setParent(this);
        saturnNode.setRenderable(saturn.getNow(null));
        nodes.add(saturnNode);
        PlanetNode uranusNode = new PlanetNode(5.2, 2.4, 0.0119, 0.1f);
        uranusNode.setParent(this);
        uranusNode.setRenderable(uranus.getNow(null));
        nodes.add(uranusNode);
        PlanetNode neptuneNode = new PlanetNode(6.1, 3, 0.006, 0.74f);
        neptuneNode.setParent(this);
        neptuneNode.setRenderable(neptune.getNow(null));
        nodes.add(neptuneNode);
    }

}
