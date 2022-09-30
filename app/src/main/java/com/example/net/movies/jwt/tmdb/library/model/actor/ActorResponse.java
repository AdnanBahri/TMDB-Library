package com.example.net.movies.jwt.tmdb.library.model.actor;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ActorResponse{

	@SerializedName("also_known_as")
	private List<String> alsoKnownAs;

	@SerializedName("birthday")
	private String birthday;

	@SerializedName("gender")
	private int gender;

	@SerializedName("imdb_id")
	private String imdbId;

	@SerializedName("known_for_department")
	private String knownForDepartment;

	@SerializedName("profile_path")
	private String profilePath;

	@SerializedName("biography")
	private String biography;

	@SerializedName("deathday")
	private String deathday;

	@SerializedName("place_of_birth")
	private String placeOfBirth;

	@SerializedName("popularity")
	private double popularity;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("adult")
	private boolean adult;

	@SerializedName("homepage")
	private String homepage;

	public List<String> getAlsoKnownAs(){
		return alsoKnownAs;
	}

	public String getBirthday(){
		return birthday;
	}

	public int getGender(){
		return gender;
	}

	public String getImdbId(){
		return imdbId;
	}

	public String getKnownForDepartment(){
		return knownForDepartment;
	}

	public String getProfilePath(){
		return profilePath;
	}

	public String getBiography(){
		return biography;
	}

	public Object getDeathday(){
		return deathday;
	}

	public String getPlaceOfBirth(){
		return placeOfBirth;
	}

	public double getPopularity(){
		return popularity;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public boolean isAdult(){
		return adult;
	}

	public Object getHomepage(){
		return homepage;
	}
}