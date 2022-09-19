package com.example.net.movies.jwt.tmdb.library.model.popular;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PopularList {

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<PopularMovie> movies;

	@SerializedName("total_results")
	private int totalResults;

	public int getPage(){
		return page;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public List<PopularMovie> getResults(){
		return movies;
	}

	public int getTotalResults(){
		return totalResults;
	}
}