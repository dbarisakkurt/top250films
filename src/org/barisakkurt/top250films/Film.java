package org.barisakkurt.top250films;

import android.os.Parcel;
import android.os.Parcelable;

public class Film implements Parcelable {
	private String mOriginalTitle;
	private String mEnglishTitle;
	private String mTurkishTitle;
	private String mYear;
	private String mPosterPath;
	private String mDirector;
	private String mShortExplanation;
	private int mId;
	
	public Film(int id, String originalTitle, String englishTitle, String turkishTitle, String year, String posterPath, String director,
			String shortExplanation) {
		super();
		mId = id;
		mOriginalTitle = originalTitle;
		mEnglishTitle = englishTitle;
		mTurkishTitle = turkishTitle;
		mYear = year;
		mPosterPath = posterPath;
		mDirector = director;
		mShortExplanation = shortExplanation;
	}
	
	// example constructor that takes a Parcel and gives you an object populated with it's values
    private Film(Parcel in) {
        mId = in.readInt();
        mOriginalTitle = in.readString();
        mEnglishTitle = in.readString();
        mTurkishTitle = in.readString();
        mYear = in.readString();
        mPosterPath = in.readString();
        mDirector = in.readString();
        mShortExplanation = in.readString();
    }
	
	public String getOriginalTitle() {
		return mOriginalTitle;
	}
	
	public void setOriginalTitle(String originalTitle) {
		mOriginalTitle = originalTitle;
	}
	
	public String getEnglishTitle() {
		return mEnglishTitle;
	}
	
	public void setEnglishTitle(String englishTitle) {
		mEnglishTitle = englishTitle;
	}
	
	public void setTurkishTitle(String turkishTitle) {
		mTurkishTitle = turkishTitle;
	}
	
	public String getTurkishTitle() {
		return mTurkishTitle;
	}

	public String getYear() {
		return mYear;
	}
	
	public void setYear(String year) {
		mYear = year;
	}
	
	public String getPosterPath() {
		return mPosterPath;
	}
	
	public void setPosterPath(String posterPath) {
		mPosterPath = posterPath;
	}
	
	public String getDirector() {
		return mDirector;
	}
	
	public void setDirector(String director) {
		mDirector = director;
	}
	
	public String getShortExplanation() {
		return mShortExplanation;
	}
	
	public void setShortExplanation(String shortExplanation) {
		mShortExplanation = shortExplanation;
	}
	
	
	//Parcelable members
	 // 99.9% of the time ignore this
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mId);
        out.writeString(mOriginalTitle);
        out.writeString(mEnglishTitle);
        out.writeString(mTurkishTitle);
        out.writeString(mYear);
        out.writeString(mPosterPath);
        out.writeString(mDirector);
        out.writeString(mShortExplanation);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>() {
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
    
    @Override
    public String toString() {
    	return this.getOriginalTitle();
    }

}
