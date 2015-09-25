/**
 * 
 */
package com.alphateam.EasyCollege;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;


/**
 *
 */
public class Tab2Fragment extends Fragment {

	Integer[] imageIDs = {
			R.drawable.abc_btn_borderless_material,
			R.drawable.abc_btn_check_to_on_mtrl_015,
			R.drawable.abc_btn_check_to_on_mtrl_015,
			R.drawable.abc_btn_check_material,
			R.drawable.abc_btn_rating_star_off_mtrl_alpha,
			R.drawable.abc_item_background_holo_light,
			R.drawable.abc_ab_share_pack_mtrl_alpha,
			R.drawable.abc_btn_borderless_material
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_frag2_layout,container, false);
		// Note that Gallery view is deprecated in Android 4.1---
		Gallery gallery = (Gallery) view.findViewById(R.id.gallery1);
		gallery.setAdapter(new ImageAdapter(getActivity().getApplicationContext()));
		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position,long id)
			{
				Toast.makeText(getActivity().getBaseContext(),"pic" + (position + 1) + " selected",
						Toast.LENGTH_SHORT).show();
				// display the images selected
				ImageView imageView = (ImageView)getView(). findViewById(R.id.image1);
				imageView.setImageResource(imageIDs[position]);
			}
		});

		return view;
	}


	public class ImageAdapter extends BaseAdapter {
		private Context context;
		private int itemBackground;
		public ImageAdapter(Context c){
			context = c;
			// sets a grey background; wraps around the images
			TypedArray a =getActivity().obtainStyledAttributes(R.styleable.MyGallery);
			itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
			a.recycle();
		}
		// returns the number of images
		public int getCount() {
			return imageIDs.length;
		}
		// returns the ID of an item
		public Object getItem(int position) {
			return position;
		}
		// returns the ID of an item
		public long getItemId(int position) {
			return position;
		}
		// returns an ImageView view
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(imageIDs[position]);
			imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
			imageView.setBackgroundResource(itemBackground);
			return imageView;
		}
	}

	

}
