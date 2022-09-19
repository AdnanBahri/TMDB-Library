package com.example.net.movies.jwt.tmdb.library.model.coming;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ComingList{

	@SerializedName("dates")
	private Dates dates;

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<ComingMovie> results;

	@SerializedName("total_results")
	private int totalResults;

	public Dates getDates(){
		return dates;
	}

	public int getPage(){
		return page;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public List<ComingMovie> getResults(){
		return results;
	}

	public int getTotalResults(){
		return totalResults;
	}
}