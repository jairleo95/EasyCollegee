/**
 * 
 */
package com.alphateam.EasyCollege;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 *
 */
public class Tab1Fragment extends Fragment {
	ImageView photo;
	Button btnUpload;
	Bitmap photobmp;
	private static int TAKE_PICTURE = 1;
	private static int SELECT_PICTURE = 2;
	private String name = "";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.tab_frag1_layout,container, false);
			//Controles
			btnUpload = (Button)view.findViewById(R.id.btnUpload);
			photo = (ImageView)view.findViewById(R.id.photo);
			Button btnAction = (Button)view.findViewById(R.id.btnPic);
			name = Environment.getExternalStorageDirectory() + "/test.jpg";
			btnAction.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					RadioButton rbtnFull = (RadioButton)getView().findViewById(R.id.radbtnFull);
					RadioButton rbtnGallery = (RadioButton)getView().findViewById(R.id.radbtnGall);
					Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					int code = TAKE_PICTURE;
					if (rbtnFull.isChecked()) {
						Uri output = Uri.fromFile(new File(name));
						intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
					} else if (rbtnGallery.isChecked()){
						intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
						code = SELECT_PICTURE;
					}
					startActivityForResult(intent, code);
				}
			});

			photo.setOnClickListener(new View.OnClickListener() {
						public void onClick(View view) {
							Intent intent = new Intent();
							intent.setType("image/*");
							intent.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(Intent.createChooser(intent, "Complete la acción usando..."), 1);
						}
					});
			btnUpload.setOnClickListener(new View.OnClickListener() {
						public void onClick(View view) {
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							photobmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
							byte[] imageBytes = baos.toByteArray();
							String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
							new MyAsyncTask(getActivity()).execute(encodedImage);
						}
					});
		return view;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
       /*if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            String aaa = getRealPathFromURI(selectedImageUri);
            photobmp = BitmapFactory.decodeFile(aaa);
            photo.setImageBitmap(photobmp);
        }*/

		if (requestCode == TAKE_PICTURE) {
			/**
			 * Si se reciben datos en el intent tenemos una vista previa (thumbnail)
			 */
			if (data != null) {
				/**
				 * En el caso de una vista previa, obtenemos el extra �data� del intent y
				 * lo mostramos en el ImageView
				 */
				if (data.hasExtra("data")) {
					ImageView iv = (ImageView)getView().findViewById(R.id.photo);
					iv.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
				}
			} else {
				/**
				 * A partir del nombre del archivo ya definido lo buscamos y creamos el bitmap
				 * para el ImageView
				 */
				ImageView iv = (ImageView)getView().findViewById(R.id.photo);
				photobmp= BitmapFactory.decodeFile(name);
				iv.setImageBitmap(photobmp);
				/**
				 * Para guardar la imagen en la galer�a, utilizamos una conexi�n a un MediaScanner
				 */
				new MediaScannerConnection.MediaScannerConnectionClient() {
					private MediaScannerConnection msc = null; {
						msc = new MediaScannerConnection(getActivity().getApplicationContext(), this); msc.connect();
					}
					public void onMediaScannerConnected() {
						msc.scanFile(name, null);
					}
					public void onScanCompleted(String path, Uri uri) {
						msc.disconnect();
					}
				};
			}
			/**
			 * Recibimos el URI de la imagen y construimos un Bitmap a partir de un stream de Bytes
			 */
		} else if (requestCode == SELECT_PICTURE){
			Uri selectedImage = data.getData();
			InputStream is;
			try {
				is = getActivity().getContentResolver().openInputStream(selectedImage);
				BufferedInputStream bis = new BufferedInputStream(is);
				Uri selectedImageUri = data.getData();
				String aaa = getRealPathFromURI(selectedImageUri);
				photobmp = BitmapFactory.decodeFile(aaa);
				photo.setImageBitmap(photobmp);
			} catch (FileNotFoundException e) {}
		}

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up btnUpload, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	public String getRealPathFromURI(Uri contentUri) {
		Cursor cursor = null;
		try {
			String[] proj = { MediaStore.Images.Media.DATA };
			cursor = getActivity().getApplicationContext().getContentResolver().query(contentUri,  proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
}
