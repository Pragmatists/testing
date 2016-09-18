
package $2_tools.examples;

import java.util.Date;

import org.junit.Test;

import $2_tools.examples.movie.Movie;

/**
 * Date assertions examples.<br>
 */
public class DateAssertionsExamples extends AbstractAssertionsExamples {

    @Test
    public void basic_date_assertions_examples() {
        Movie theTwoTowers = new Movie("the two Towers", parse("2002-12-18"), "179 min");
        //theTwoTowers ReleaseDate "2002-12-18"

        Movie theFellowshipOfTheRing = new Movie("the fellowship of the Ring", parse("2001-12-19"), "178 min");
        //theFellowshipOfTheRing ReleaseDate < theReturnOfTheKing ReleaseDate

    }

    @Test
    public void theReturnOfTheKing_release_date_before_2004() {
        Movie theReturnOfTheKing = new Movie("the Return of the King", parse("2003-12-17"), "201 min");
    }

    @Test
    public void date_in_past() {
        Date past = new Date(new Date().getTime() - 1);
    }


}
