package com.faisalkhatri.okhttppoc;

import lombok.Getter;
import lombok.Setter;

/**
 * @since Mar 7, 2020
 *
 */
@Getter
@Setter
public class PostData {

	private final String	name;
	private final String	job;

	/**
	 *
	 * @author Faisal Khatri
	 * @param name
	 * @param job
	 */
	public PostData (final String name, final String job) {
		this.name = name;
		this.job = job;

	}

}
