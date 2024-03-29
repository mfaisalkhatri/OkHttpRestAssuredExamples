/*
        Copyright (c) 2022 Mohammad Faisal Khatri

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/

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