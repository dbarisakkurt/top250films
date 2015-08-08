package org.barisakkurt.top250films;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.res.AssetManager;

public class XmlParser {
	List<Film> mFilmList = new ArrayList<Film>();
	private String mFileName;
	private Context mContext;
	private static String TAG_FILM="film";
	private static String TAG_ORIGINAL_NAME="originalName";
	private static String TAG_ENGLISH_NAME="englishName";
	private static String TAG_TURKISH_NAME="turkishName";
	private static String TAG_DIRECTOR="director";
	private static String TAG_YEAR="year";
	private static String TAG_POSTER="poster";
	private static String TAG_SHORT_EXPLANATION="shortExplanation";
	
	public XmlParser(String fileName, Context context) {
		this.mFileName=fileName;
		this.mContext=context;
	}
	
	public List<Film> parseFilms() {
		String originalName="";
		String englishName="";
		String turkishName="";
		String year="";
		String director="";
		String poster="";
		String shortExplanation="";
		
		AssetManager assetManager = mContext.getAssets();
		InputStream is;
		try {
			is = assetManager.open(mFileName);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(is);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName(TAG_FILM);

			for (int i = 0; i < nodeList.getLength(); i++) {

				Node node = nodeList.item(i);
				
				originalName = getTextValue(node, TAG_ORIGINAL_NAME);
				englishName = getTextValue(node, TAG_ENGLISH_NAME);
				turkishName = getTextValue(node, TAG_TURKISH_NAME);
				year = getTextValue(node, TAG_YEAR);
				poster = getTextValue(node, TAG_POSTER);
				director = getTextValue(node, TAG_DIRECTOR);
				shortExplanation = getTextValue(node, TAG_SHORT_EXPLANATION);
	
				Film chapter = new Film((i+1), originalName, englishName, turkishName, year, poster, director, shortExplanation);
				mFilmList.add(chapter);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return mFilmList;
	}
	
	private String getTextValue(Node node, String tag)
	{
		String text = "";
		Element fstElmnt = (Element) node;
		NodeList tagList = fstElmnt.getElementsByTagName(tag);
		Node textNode = tagList.item(0).getFirstChild();
		if(textNode != null)
			text = textNode.getNodeValue();
		return text;
	}

}
