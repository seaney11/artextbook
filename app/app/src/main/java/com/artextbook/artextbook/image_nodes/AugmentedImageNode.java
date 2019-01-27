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

package com.artextbook.artextbook.image_nodes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Node for rendering an augmented image. The image is framed by placing the virtual picture frame
 * at the corners of the augmented image trackable.
 */
@SuppressWarnings({"AndroidApiChecker"})
public abstract class AugmentedImageNode extends AnchorNode {

  private static final String TAG = "AugmentedImageNode";

  // The augmented image represented by this node.
  private AugmentedImage image;
  protected List<Node> nodes;

  protected List<CompletableFuture<?>> completableFutures;

  public AugmentedImageNode(Context context, AugmentedImage image) {
      this.image = image;
      setAnchor(image.createAnchor(image.getCenterPose()));
      completableFutures = new ArrayList<>();
      nodes = new ArrayList<>();
      loadRenderables(context);
      CompletableFuture<Void> all = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]));
      all.thenAccept(a -> createNodes(context));
  }

  protected abstract void createNodes(Context context);

  protected abstract void loadRenderables(Context context);

  public void remove(){
    for (Node node : nodes) {
      this.removeChild(node);
      node.setRenderable(null);
    }
  }

  /**
   * Called when the AugmentedImage is detected and should be rendered. A Sceneform node tree is
   * created based on an Anchor created from the image. The corners are then positioned based on the
   * extents of the image. There is no need to worry about world coordinates since everything is
   * relative to the center of the image, which is the parent node of the corners.
   */

  public AugmentedImage getImage() {
    return image;
  }
}
