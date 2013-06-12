/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.math;

import org.solovyev.common.JBuilder;
import org.solovyev.common.JPredicate;
import org.solovyev.common.collections.SortedList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * User: serso
 * Date: 9/29/11
 * Time: 4:57 PM
 */
public abstract class AbstractMathRegistry<T extends MathEntity> implements MathRegistry<T> {

	private static final MathEntityComparator<MathEntity> MATH_ENTITY_COMPARATOR = new MathEntityComparator<MathEntity>();

	static class MathEntityComparator<T extends MathEntity> implements Comparator<T> {

		MathEntityComparator() {
		}

		@Override
		public int compare(T l, T r) {
			int result = r.getName().length() - l.getName().length();
			if (result == 0) {
				result = l.getName().compareTo(r.getName());
			}
			return result;
		}
	}

	@Nonnull
	private static Integer counter = 0;

	@Nonnull
	protected final List<T> entities = SortedList.newInstance(new ArrayList<T>(30), MATH_ENTITY_COMPARATOR);

	@Nonnull
	protected final List<T> systemEntities = SortedList.newInstance(new ArrayList<T>(30), MATH_ENTITY_COMPARATOR);

	protected AbstractMathRegistry() {
	}

	@Nonnull
	@Override
	public List<T> getEntities() {
		return java.util.Collections.unmodifiableList(new ArrayList<T>(entities));
	}

	@Nonnull
	@Override
	public List<T> getSystemEntities() {
		return java.util.Collections.unmodifiableList(new ArrayList<T>(systemEntities));
	}

	protected void add(@Nonnull T entity) {
		if (entity.isSystem()) {
			if (contains(entity.getName(), this.systemEntities)) {
				throw new IllegalArgumentException("Trying to add two system entities with same name: " + entity.getName());
			}

			this.systemEntities.add(entity);
		}

		if (!contains(entity.getName(), this.entities)) {
			addEntity(entity, this.entities);
		}
	}

	private void addEntity(@Nonnull T entity, @Nonnull List<T> list) {
		entity.setId(count());
		list.add(entity);
	}

	@Override
	public T add(@Nonnull JBuilder<? extends T> JBuilder) {
		final T entity = JBuilder.create();

		T varFromRegister;

		if (entity.isIdDefined()) {
			varFromRegister = getById(entity.getId());
		} else {
			varFromRegister = get(entity.getName());
		}

		if (varFromRegister == null) {
			varFromRegister = entity;

			addEntity(entity, this.entities);
			if (entity.isSystem()) {
				this.systemEntities.add(entity);
			}

		} else {
			varFromRegister.copy(entity);
			java.util.Collections.sort(this.entities, MATH_ENTITY_COMPARATOR);
			java.util.Collections.sort(this.systemEntities, MATH_ENTITY_COMPARATOR);
		}

		return varFromRegister;
	}

	@Override
	public void remove(@Nonnull T entity) {
		if (!entity.isSystem()) {
			org.solovyev.common.collections.Collections.removeFirst(this.entities, new MathEntity.Finder<T>(entity.getName()));
		}
	}

	@Override
	@Nonnull
	public List<String> getNames() {
		final List<String> result = new ArrayList<String>();

		for (T entity : entities) {
			result.add(entity.getName());
		}

		return result;
	}

	@Override
	@Nullable
	public T get(@Nonnull final String name) {
		return org.solovyev.common.collections.Collections.find(entities, new MathEntity.Finder<T>(name));
	}

	@Override
	public T getById(@Nonnull final Integer id) {
		return org.solovyev.common.collections.Collections.find(entities, new JPredicate<T>() {
			@Override
			public boolean apply(@Nullable T t) {
				return t != null && t.getId().equals(id);
			}
		});
	}

	@Override
	public boolean contains(@Nonnull final String name) {
		return contains(name, this.entities);
	}

	private boolean contains(final String name, @Nonnull Collection<T> entities) {
		return org.solovyev.common.collections.Collections.find(entities, new MathEntity.Finder<T>(name)) != null;
	}

	@Nonnull
	private static synchronized Integer count() {
		final Integer result = counter;
		counter++;
		return result;
	}
}
