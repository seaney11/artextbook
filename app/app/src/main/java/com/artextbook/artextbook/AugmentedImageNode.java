/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.artextbook.artextbook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Node for rendering an augmented image. The image is framed by placing the virtual picture frame
 * at the corners of the augmented image trackable.
 */
@SuppressWarnings({"AndroidApiChecker"})
public class AugmentedImageNode extends AnchorNode {

  private static final String TAG = "AugmentedImageNode";
  private static final String IMAGES_JSON_PATH = "images.json";

  // The augmented image represented by this node.
  private AugmentedImage image;
  private Node viewNode;

  // Models of the 4 corners.  We use completable futures here to simplify
  // the error handling and asynchronous loading.  The loading is started with the
  // first construction of an instance, and then used when the image is set.
  private CompletableFuture<ViewRenderable> view;

  private static Map<String, String> images;

  public AugmentedImageNode(Context context, String imageName) {
    // Upon construction, start loading the models for the corners of the frame.
    if (images == null){
      images = new HashMap<>();
      try (InputStream is = context.getAssets().open(IMAGES_JSON_PATH)){
        String s = IOUtils.toString(is, "UTF-8");
        Gson g = new Gson();
        ImageJson[] imageJsons = g.fromJson(s, ImageJson[].class);
        for (ImageJson ij : imageJsons){
          images.put(ij.name, ij.image);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (view == null) {
      LayoutInflater inflater = LayoutInflater.from(context);
      View v = inflater.inflate(R.layout.graph, null, false);
      ImageView iv = v.findViewById(R.id.graph_image);
      iv.setImageResource(context.getResources().getIdentifier(images.get(imageName),"drawable", context.getPackageName()));

      view = ViewRenderable.builder()
                      .setView(context, v)
                      .build();
    }
  }

  public void remove(){
    this.removeChild(viewNode);
    viewNode.setRenderable(null);
  }

  /**
   * Called when the AugmentedImage is detected and should be rendered. A Sceneform node tree is
   * created based on an Anchor created from the image. The corners are then positioned based on the
   * extents of the image. There is no need to worry about world coordinates since everything is
   * relative to the center of the image, which is the parent node of the corners.
   */
  @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
  public void setImage(AugmentedImage image) {
    this.image = image;

    // If any of the models are not loaded, then recurse when all are loaded.
    if (!view.isDone()) {
      CompletableFuture.allOf(view)
          .thenAccept((Void aVoid) -> setImage(image))
          .exceptionally(
              throwable -> {
                Log.e(TAG, "Exception loading", throwable);
                return null;
              });
    }

    // Set the anchor based on the center of the image.
    setAnchor(image.createAnchor(image.getCenterPose()));

    // Make the 4 corner nodes.
    Vector3 localPosition = new Vector3();
    viewNode = new Node();
    viewNode.setParent(this);
    viewNode.setLocalPosition(localPosition);
    viewNode.setRenderable(view.getNow(null));
  }

  public AugmentedImage getImage() {
    return image;
  }
}
