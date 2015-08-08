package org.barisakkurt.top250films;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

//TODO: film isaretlemede hata var

public class MainActivity extends Activity {
	ListView mMainListView;
	Set<String> watchedFilms=new HashSet<String>();
	public static final String APP_PREFERENCES = "AppPreferenes" ;
	public static final String watchedFilmSetting = "wFilmSet" ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_main);

		getActionBar().setTitle(this.getString(R.string.app_name));
		
		//load ads
		/*AdView mAdView = (AdView) findViewById(R.id.adViewMain);
        AdRequest adRequest = new AdRequest.Builder()
        		.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)       
			    .addTestDevice("C2E0912C1AF221EAD0B0520A3C33BA61").build();
        mAdView.loadAd(adRequest);*/

		Set<String> watched = loadSettingWatchedFilm(watchedFilmSetting);
		final FilmAdapter filmAdapter = new FilmAdapter();
		setGeneralResults(watched);

		mMainListView = (ListView) findViewById(R.id.mainListView);
		mMainListView.setAdapter(filmAdapter);
		mMainListView.setOnItemClickListener(new OnItemClickListener() {
		    @Override 
		    public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
		    { 
		    	Intent myIntent = new Intent(MainActivity.this, FilmDetailActivity.class);
		    	Film film = filmAdapter.getFilmList().get(position);
		    	
		    	myIntent.putExtra("film", film);
		    	MainActivity.this.startActivity(myIntent);
		    }
		});
	}
	
	private void setGeneralResults(Set<String> watched) {
		TextView generalResult = (TextView)findViewById(R.id.textViewTotalResults);
		generalResult.setText("Ýzlediklerim:" + watched.size() + " Ýzlenecekler: " + (250 - watched.size()));
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

	public class FilmAdapter extends BaseAdapter {
		private List<Film> mFilmList = getDataForListView();
		private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();
		Set<String> watched = loadSettingWatchedFilm(watchedFilmSetting);
		
		public FilmAdapter() {
			super();
			
			for (int i = 0; i < this.getCount(); i++) {
		        itemChecked.add(i, false); // initializes all items value with false
		    }
			
			for(String item: watched)
				itemChecked.set(Integer.parseInt(item), true);
		}

		public List<Film> getDataForListView() {
			XmlParser parser=new XmlParser("films.xml", getBaseContext());
			return parser.parseFilms();
		}
		
		public List<Film> getFilmList() {
			if(mFilmList.size()==250) {
				return mFilmList;
			}
			else {
				return getDataForListView();
			}
		}

		@Override
		public int getCount() {
			return mFilmList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mFilmList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup parent) {
			ViewHolder viewHolder;
			final int pos = position;
			
			if (arg1 == null) {
				LayoutInflater inflater = (LayoutInflater) MainActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				viewHolder = new ViewHolder();
				arg1 = inflater.inflate(R.layout.list_row, parent, false);
				
				viewHolder.englishTitle = (TextView) arg1.findViewById(R.id.textViewOriginalName);
				viewHolder.year = (TextView) arg1.findViewById(R.id.textViewYear);
				viewHolder.director = (TextView) arg1.findViewById(R.id.textViewDirector);
				viewHolder.image= (ImageView) arg1.findViewById(R.id.imageViewPoster);
				viewHolder.checkBox= (CheckBox) arg1.findViewById(R.id.chekcbox);
				arg1.setTag( viewHolder);
			}
			else
				viewHolder=(ViewHolder) arg1.getTag();
			
			viewHolder.checkBox.setOnClickListener( new OnClickListener() {  
				@Override
	            public void onClick(View v)  {
						if(((CheckBox)v).isChecked()) {
							itemChecked.set(pos, true);
							((CheckBox)v).setChecked(true);
							watched.add(Integer.toString(pos));
						}
						else {
							itemChecked.set(pos, false);
							((CheckBox)v).setChecked(false);
							watched.remove(Integer.toString(pos));
						}
						saveSettingWatchedFilm(watched);
						
						setGeneralResults(watched);
					}
			});
			Film film = mFilmList.get(pos);

			viewHolder.englishTitle.setText(film.getEnglishTitle());
			viewHolder.year.setText(film.getYear());
			viewHolder.director.setText(film.getDirector());
			viewHolder.checkBox.setChecked(itemChecked.get(pos));
			
			Bitmap bitMap = new Utility(getBaseContext()).getBitmapFromAsset(film.getPosterPath());
			if (bitMap!=null)
				viewHolder.image.setImageBitmap(bitMap);
			
			return arg1;
		}
	}
	
	static class ViewHolder {
	    TextView englishTitle;
	    TextView year;
	    TextView director;
	    ImageView image;
	    CheckBox checkBox;
	}
	
	private void saveSettingWatchedFilm(Set<String> setting) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.putStringSet(watchedFilmSetting, setting);
		editor.commit();
		
	}
	
	private Set<String> loadSettingWatchedFilm(String setting) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Set<String> watched = sharedPreferences.getStringSet(setting, new HashSet<String>());
		return watched;
	}
}
