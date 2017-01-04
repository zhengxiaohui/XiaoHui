package com.zdemo.activity;
import com.zdemo.R;
import com.zdemo.animation.Rotate3dAnimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class AnimationActivity extends Activity {
	private ListView mPhotosList;
    private ViewGroup mContainer;
    private ImageView mImageView;
	  private static final String[] PHOTOS_NAMES = new String[] {
          "num one",
          "num two",
          "num three",
          "num fore",
          "num five",
          "num six"
  };
	  private static final int[] PHOTOS_RESOURCES = new int[] {
          R.mipmap.photo1,
          R.mipmap.photo2,
          R.mipmap.photo3,
          R.mipmap.photo4,
          R.mipmap.photo5,
          R.mipmap.photo6
  };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animations_main_screen);
        mPhotosList = (ListView) findViewById(android.R.id.list);
        mImageView = (ImageView) findViewById(R.id.picture);
        mContainer = (ViewGroup) findViewById(R.id.container);

        // Prepare the ListView
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, PHOTOS_NAMES);

        mPhotosList.setAdapter(adapter);
        mPhotosList.setOnItemClickListener(listener);
        // Prepare the ImageView
        mImageView.setClickable(true);
        mImageView.setFocusable(true);
        mImageView.setOnClickListener(viewlistener);
    }
    private AdapterView.OnItemClickListener listener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView arg0, View arg1, int arg2,
				long arg3) {
			 mImageView.setImageResource(PHOTOS_RESOURCES[arg2]);
		        applyRotation(arg2, 0, 90);
			
		}
	};
	private View.OnClickListener viewlistener=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			 applyRotation(-1, 180, 90);
		}
	};
    private void applyRotation(int position, float start, float end) {
        // Find the center of the container
        final float centerX = mContainer.getWidth() / 2.0f;
        final float centerY = mContainer.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(position));

        mContainer.startAnimation(rotation);
    }
    
    private final class DisplayNextView implements Animation.AnimationListener {
        private final int mPosition;

        private DisplayNextView(int position) {
            mPosition = position;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            mContainer.post(new SwapViews(mPosition));
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }
    private final class SwapViews implements Runnable {
        private final int mPosition;

        public SwapViews(int position) {
            mPosition = position;
        }

        public void run() {
            final float centerX = mContainer.getWidth() / 2.0f;
            final float centerY = mContainer.getHeight() / 2.0f;
            Rotate3dAnimation rotation;
            if (mPosition > -1) {
                mPhotosList.setVisibility(View.GONE);
                mImageView.setVisibility(View.VISIBLE);
                mImageView.requestFocus();

                rotation = new Rotate3dAnimation(90, 180, centerX, centerY, 310.0f, false);
            } else {
                mImageView.setVisibility(View.GONE);
                mPhotosList.setVisibility(View.VISIBLE);
                mPhotosList.requestFocus();

                rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f, false);
            }

            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());
            mContainer.startAnimation(rotation);
        }
    }
}