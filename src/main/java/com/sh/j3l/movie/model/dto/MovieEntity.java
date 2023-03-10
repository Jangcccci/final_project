package com.sh.j3l.movie.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieEntity {
	private int no;
	private String title;
	private Genre genre;
	private String director;
	private String actors;
	private String synopsis;
	private int limitAge;
}
