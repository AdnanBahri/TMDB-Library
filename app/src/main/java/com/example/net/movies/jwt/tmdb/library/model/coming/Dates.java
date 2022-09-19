package com.example.net.movies.jwt.tmdb.library.model.coming;

import com.google.gson.annotations.SerializedName;

public class Dates{

	@SerializedName("maximum")
	private String maximum;

	@SerializedName("minimum")
	private String minimum;

	public String getMaximum(){
		return maximum;
	}

	public String getMinimum(){
		return minimum;
	}
}