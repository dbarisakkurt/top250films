package org.barisakkurt.top250films;

import java.io.IOException;
import java.io.InputStream;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

public class Utility {
	private Context mContext;
	
	public Utility(Context context) {
		mContext = context;
	}
	
	public Bitmap getBitmapFromAsset(String strName) {
		if(!TextUtils.isEmpty(strName)) {
	        AssetManager assetManager = mContext.getAssets();
	        InputStream istr = null;
	        try {
	            istr = assetManager.open(strName);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        Bitmap bitmap = BitmapFactory.decodeStream(istr);
	        return bitmap;
    	}
		else
		{
			return null;
		}
    }
	
	public static void openDialog(Context context, String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    builder.setTitle(title)
	    .setMessage(message)
	    .setCancelable(false)
	    .setIcon(android.R.drawable.ic_dialog_alert)
	    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        }
	     });
	    
	    AlertDialog alert = builder.create();
	    alert.show();
	}

}
