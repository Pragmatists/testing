/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2014 the original author or authors.
 */
package org.assertj.examples;

import static com.google.common.collect.Maps.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.examples.data.Race.*;

import java.util.Map;

import org.assertj.core.util.introspection.IntrospectionError;
import org.assertj.examples.data.Person;
import org.assertj.examples.data.Ring;
import org.assertj.examples.data.TolkienCharacter;
import org.assertj.examples.data.movie.Movie;
import org.junit.Test;

/**
 * Assertions available for all objects.
 *
 * @author Joel Costigliola
 */
public class BasicAssertionsExamples extends AbstractAssertionsExamples {

  // the data used are initialized in AbstractAssertionsExamples.
//    TODO mlip begginign
  @Test
  public void isEqualTo_isNotEqualTo_assertions_examples() {
    // the most simple assertion
    assertThat(frodo.age).isEqualTo(33);
    assertThat(frodo.getName()).isEqualTo("Frodo").isNotEqualTo("Frodon");
    // shows that we are no more limited by generics, if we had defined isEqualTo to take only the type of actual
    // (TolkienCharacter) the assertion below would not have compiled
    Object frodoAsObject = frodo;
    assertThat(frodo).isEqualTo(frodoAsObject);
  }

  @Test
  public void meaningful_error_with_test_description_example() {
    try {
      // set a bad age to Mr Frodo, just to see how nice is the assertion error message
      frodo.setAge(50);
      // you can specify a test description with as() method or describedAs(), it supports String format args
      assertThat(frodo.age).as("check %s's age", frodo.getName()).isEqualTo(33);
    } catch (AssertionError e) {
      assertThat(e).hasMessage("[check Frodo's age] expected:<[33]> but was:<[50]>");
    }
    // but you still can override the error message if you have a better one :
    final String frodon = "Frodon";
    try {
      assertThat(frodo.getName()).as("check Frodo's name")
        .overridingErrorMessage("Hey my name is Frodo not %s", frodon).isEqualTo(frodon);
    } catch (AssertionError e) {
      assertThat(e).hasMessage("[check Frodo's name] Hey my name is Frodo not Frodon");
    }
    // if you still can override the error message if you have a better one :
    try {
      assertThat(frodo.getName()).overridingErrorMessage("Hey my name is Frodo not (%)").isEqualTo(frodon);
    } catch (AssertionError e) {
      assertThat(e).hasMessage("Hey my name is Frodo not (%)");
    }
  }

  @Test
  public void isSame_isNotSame_assertions_examples() {
    // isSame compares objects reference
    Object jake = new Person("Jake", 43);
    Object sameJake = jake;
    Object jakeClone = new Person("Jake", 43); // equals to jake but not the same
    assertThat(jake).isSameAs(sameJake).isNotSameAs(jakeClone);
  }

  @Test
  public void isIn_isNotIn_assertions_examples() {
    assertThat(frodo).isIn(fellowshipOfTheRing);
    assertThat(frodo).isIn(sam, frodo, pippin);
    assertThat(sauron).isNotIn(fellowshipOfTheRing);
  }

  @Test
  public void isNull_isNotNull_assertions_examples() {
    Object nullObject = null;
    assertThat(nullObject).isNull();
    Object nonNullObject = new Object();
    assertThat(nonNullObject).isNotNull();
  }

  @Test
  public void isInstanceOf_assertions_examples() {
    assertThat(gandalf).isInstanceOf(TolkienCharacter.class).isInstanceOfAny(Object.class, TolkienCharacter.class);
    assertThat(gandalf).isNotInstanceOf(Movie.class).isNotInstanceOfAny(Movie.class, Ring.class);
  }

  @Test
  public void assertion_error_message_differentiates_expected_and_actual_persons() {
    // Assertion error message is built with toString description of involved objects.
    // Sometimes, objects differs but not their toString description, in that case the error message would be
    // confusing because, if toString returns "Jake" for different objects, isEqualTo would return :
    // "expected:<'Jake'> but was:<'Jake'> ... How confusing !
    // In that case, AssertJ is smart enough and differentiates objects description by adding their class and hashCode.
    Person actual = new Person("Jake", 43);
    Person expected = new Person("Jake", 47);
    try {
      assertThat(actual).isEqualTo(expected);
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualTo differentiating expected and actual having same toString representation", e);
    }
  }
    //    TODO mlip end

    //    TODO mlip begginign
  @Test
  public void basic_assertions_with_lenient_equals_examples() {

    TolkienCharacter mysteriousHobbit = new TolkienCharacter(null, 33, HOBBIT);

    // ------------------------------------------------------------------------------------
    // Lenient equality with field by field comparison
    // ------------------------------------------------------------------------------------

    // Frodo is still Frodo ...
    assertThat(frodo).isEqualToComparingFieldByField(frodo);

    TolkienCharacter frodoClone = new TolkienCharacter("Frodo", 33, HOBBIT);

    // Frodo and his clone are equals by comparing fields
    assertThat(frodo).isEqualToComparingFieldByField(frodoClone);

    // ------------------------------------------------------------------------------------
    // Lenient equality when ignoring null fields of other object
    // ------------------------------------------------------------------------------------

    // Frodo is still Frodo ...
    assertThat(frodo).isEqualToIgnoringNullFields(frodo);

    // Null fields in expected object are ignored, the mysteriousHobbit has null name
    assertThat(frodo).isEqualToIgnoringNullFields(mysteriousHobbit);
    // ... But the lenient equality is not reversible !
    try {
      assertThat(mysteriousHobbit).isEqualToIgnoringNullFields(frodo);
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualToIgnoringNullFields", e);
    }

    // ------------------------------------------------------------------------------------
    // Lenient equality with field by field comparison expect specified fields
    // ------------------------------------------------------------------------------------

    // Except name and age, frodo and sam both are hobbits, so they are lenient equals ignoring name and age
    assertThat(frodo).isEqualToIgnoringGivenFields(sam, "name", "age");

    // But not when just age is ignored
    try {
      assertThat(frodo).isEqualToIgnoringGivenFields(sam, "age");
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualToIgnoringGivenFields", e);
    }

    // Null fields are not ignored, so when expected has null field, actual must have too
    assertThat(mysteriousHobbit).isEqualToIgnoringGivenFields(mysteriousHobbit, "age");

    // ------------------------------------------------------------------------------------
    // Lenient equality with field by field comparison on given fields only
    // ------------------------------------------------------------------------------------

    // frodo and sam both are hobbits, so they are lenient equals on race
    assertThat(frodo).isEqualToComparingOnlyGivenFields(sam, "race");

    // but not when accepting name and race
    try {
      assertThat(frodo).isEqualToComparingOnlyGivenFields(sam, "name", "race", "age");
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualToComparingOnlyGivenFields", e);
    }

    // Null fields are not ignored, so when expected has null field, actual must have too
    assertThat(mysteriousHobbit).isEqualToComparingOnlyGivenFields(mysteriousHobbit, "name");

    // Specified fields must exist
    try {
      assertThat(frodo).isEqualToComparingOnlyGivenFields(sam, "hairColor");
    } catch (IntrospectionError e) {
      assertThat(e).hasMessage("No field 'hairColor' in class org.assertj.examples.data.TolkienCharacter");
    }
  }

    @Test
    public void map_contains_examples() throws Exception {

        Map<String, TolkienCharacter> characters = newLinkedHashMap();
        characters.put(frodo.getName(), frodo);
        characters.put(galadriel.getName(), galadriel);
        characters.put(gandalf.getName(), gandalf);
        characters.put(sam.getName(), sam);

        assertThat(characters).containsOnly(
                entry(sam.getName(), sam),
                entry(frodo.getName(), frodo),
                entry(gandalf.getName(), gandalf),
                entry(galadriel.getName(), galadriel));

        try {
            assertThat(characters).containsOnly(
                    entry(sam.getName(), sam),
                    entry(frodo.getName(), frodo),
                    entry(aragorn.getName(), aragorn));
        } catch (AssertionError e) {
            logAssertionErrorMessage("containsOnly with not found and not expected elements.", e);
        }

        assertThat(characters).containsExactly(
                entry(frodo.getName(), frodo),
                entry(galadriel.getName(), galadriel),
                entry(gandalf.getName(), gandalf),
                entry(sam.getName(), sam));

        try {
            assertThat(characters).containsExactly(
                    entry(frodo.getName(), frodo),
                    entry(sam.getName(), sam),
                    entry(gandalf.getName(), gandalf),
                    entry(galadriel.getName(), galadriel));
        } catch (AssertionError e) {
            logAssertionErrorMessage("containsExactly is disorder.", e);
        }
    }


    //    TODO mlip end

}
