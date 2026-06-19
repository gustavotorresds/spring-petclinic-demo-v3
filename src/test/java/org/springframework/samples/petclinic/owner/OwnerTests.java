/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Owner} domain object, focusing on the stream-based getPet
 * methods.
 */
class OwnerTests {

	private Owner owner;

	private Pet savedPet;

	private Pet newPet;

	@BeforeEach
	void setUp() {
		owner = new Owner();
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("6085551023");

		PetType dog = new PetType();
		dog.setName("dog");

		savedPet = new Pet();
		savedPet.setName("Max");
		savedPet.setType(dog);
		savedPet.setBirthDate(LocalDate.of(2020, 1, 1));
		owner.addPet(savedPet);
		savedPet.setId(1);

		newPet = new Pet();
		newPet.setName("Buddy");
		newPet.setType(dog);
		newPet.setBirthDate(LocalDate.of(2023, 6, 15));
		owner.addPet(newPet);
	}

	@Test
	void getPetByIdReturnsSavedPet() {
		Pet found = owner.getPet(1);
		assertThat(found).isSameAs(savedPet);
	}

	@Test
	void getPetByIdReturnsNullForNewPet() {
		Pet found = owner.getPet(newPet.getId());
		assertThat(found).isNull();
	}

	@Test
	void getPetByIdReturnsNullWhenNotFound() {
		Pet found = owner.getPet(999);
		assertThat(found).isNull();
	}

	@Test
	void getPetByNameReturnsPetCaseInsensitive() {
		Pet found = owner.getPet("max");
		assertThat(found).isSameAs(savedPet);
	}

	@Test
	void getPetByNameIgnoringNewReturnsSavedOnly() {
		Pet found = owner.getPet("Max", true);
		assertThat(found).isSameAs(savedPet);
	}

	@Test
	void getPetByNameIgnoringNewSkipsNewPet() {
		Pet found = owner.getPet("Buddy", true);
		assertThat(found).isNull();
	}

	@Test
	void getPetByNameIncludingNewReturnsNewPet() {
		Pet found = owner.getPet("Buddy", false);
		assertThat(found).isSameAs(newPet);
	}

	@Test
	void getPetByNameReturnsNullWhenNotFound() {
		Pet found = owner.getPet("Unknown");
		assertThat(found).isNull();
	}

}
