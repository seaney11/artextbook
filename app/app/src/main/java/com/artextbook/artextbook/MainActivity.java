package com.artextbook.artextbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.artextbook.artextbook.helpers.SnackbarHelper;
import com.artextbook.artextbook.image_nodes.AugmentedImageNode;
import com.artextbook.artextbook.image_nodes.AugmentedImageNodeFactory;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.Frame;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.Collection;

public class MainActivity extends AppCompatActivity {
    private ArFragment arFragment;
    private ImageView fitToScanView;

    // Augmented image and its associated center pose anchor, keyed by the augmented image in
    // the database.
    AugmentedImageNode imageNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        fitToScanView = findViewById(R.id.image_view_fit_to_scan);

        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Registered with the Sceneform Scene object, this method is called at the start of each frame.
     *
     * @param frameTime - time since last frame.
     */
    private void onUpdateFrame(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();

        // If there is no frame or ARCore is not tracking yet, just return.
        if (frame == null || frame.getCamera().getTrackingState() != TrackingState.TRACKING) {
            return;
        }

        Collection<AugmentedImage> updatedAugmentedImages =
                frame.getUpdatedTrackables(AugmentedImage.class);
        for (AugmentedImage augmentedImage : updatedAugmentedImages) {
            switch (augmentedImage.getTrackingState()) {

                case PAUSED:
                    break;

                case TRACKING:
                    // Have to switch to UI Thread to update View.
                    fitToScanView.setVisibility(View.GONE);
                    if (imageNode != null) {
                        break;
                    }
                    AugmentedImageNodeFactory factory = new AugmentedImageNodeFactory(this);
                    imageNode = factory.createImageNode(this, augmentedImage);
                    arFragment.getArSceneView().getScene().addChild(imageNode);
                    SnackbarHelper.getInstance().showMessage(this, String.format("Detected Diagram %s", augmentedImage.getName()));
                    break;

                case STOPPED:
                    if (imageNode != null) {
                        imageNode.remove();
                        imageNode = null;
                    }
                    break;
            }
        }
    }
}
