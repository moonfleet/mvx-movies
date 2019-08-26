package com.moonfleet.movies.view;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.HashMap;

public class Fonts {

	private static HashMap<String, Typeface> sTypefaces = new HashMap<String, Typeface>();

	public static Typeface loadTypeface(AssetManager assets, String fontName) {
		if (sTypefaces.containsKey(fontName)) {
			return sTypefaces.get(fontName);
		} else {
			Typeface t = Typeface.createFromAsset(assets, fontName);
			sTypefaces.put(fontName, t);
			return t;
		}
	}

}
