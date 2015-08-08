package org.barisakkurt.top250films;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class FilmDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_film_detail);
		
		//load ads
		/*AdView mAdView = (AdView) findViewById(R.id.adViewDetail);
        AdRequest adRequest = new AdRequest.Builder()
        		.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)       
			    .addTestDevice("C2E0912C1AF221EAD0B0520A3C33BA61").build();
        mAdView.loadAd(adRequest);*/
		
		Intent intent = getIntent();
		Film film = (Film)intent.getParcelableExtra("film");
		
		TextView originalName = (TextView)findViewById(R.id.textViewOriginalNameDetail);
		TextView director = (TextView)findViewById(R.id.textViewDirectorDetail);
		TextView year = (TextView)findViewById(R.id.textViewYearDetail);
		TextView shortExplanation = (TextView)findViewById(R.id.textViewShortExplanation);
		TextView englishName = (TextView)findViewById(R.id.textViewEnglishName);
		TextView turkishName = (TextView)findViewById(R.id.textViewTurkishName);
		ImageView img= (ImageView) findViewById(R.id.imageViewPosterDetail);
		
		year.setText(film.getYear());
		director.setText(film.getDirector());
		originalName.setText(film.getOriginalTitle());
		shortExplanation.setText(film.getShortExplanation());
		turkishName.setText("Türkçe adý: "+film.getTurkishTitle());
		englishName.setText("Ýngilizce adý: "+film.getEnglishTitle());
		Bitmap bitMap = new Utility(this).getBitmapFromAsset(film.getPosterPath());
		if (bitMap!=null)
			img.setImageBitmap(bitMap);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_about) {
			Utility.openDialog(this, getString(R.string.action_about), getString(R.string.about_text));
			return true;
		}
		else if (id == R.id.action_roadmap) {
			Utility.openDialog(this, getString(R.string.action_roadmap), getString(R.string.roadmap_text));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
