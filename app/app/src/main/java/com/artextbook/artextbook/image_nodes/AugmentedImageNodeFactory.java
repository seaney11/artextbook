package com.artextbook.artextbook.image_nodes;

import android.content.Context;

import com.artextbook.artextbook.ImageJson;
import com.artextbook.artextbook.image_nodes.solar.SolarSystemImageNode;
import com.artextbook.artextbook.image_nodes.spring.SpringAnimationImageNode;
import com.google.ar.core.AugmentedImage;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AugmentedImageNodeFactory {

    private static Map<String, String> images;

    public AugmentedImageNodeFactory(Context context) {
        if (images == null) {
            images = new HashMap<>();
            try (InputStream is = context.getAssets().open(IMAGES_JSON_PATH)) {
                String s = IOUtils.toString(is, "UTF-8");
                Gson g = new Gson();
                ImageJson[] imageJsons = g.fromJson(s, ImageJson[].class);
                for (ImageJson ij : imageJsons) {
                    images.put(ij.name, ij.type);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static final String IMAGES_JSON_PATH = "images.json";

    public AugmentedImageNode createImageNode(Context context, AugmentedImage image) {
        switch (images.get(image.getName())) {
            case "graph":
                return new GraphImageNode(context, image);
            case "droid":
                return new DroidAnimationImageNode(context, image);
            case "spring":
                return new SpringAnimationImageNode(context, image);
            case "solar":
                return new SolarSystemImageNode(context, image);
            default:
                throw new RuntimeException("Invalid Json File");
        }
    }
}
