package fail_readability_13;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.Test;

public class OrganizationUsers {


    private HeroesRepository repository = new HeroesRepository();

//CO mozna powiedziec o tym tescie czy jest czytelny?
//test have two lives
//            - 1 when it is written
//            - 2 - when it is run and fails

    @Test
    public void find_users_by_name() {
        Hero spiderMan = repository.save(new Hero("spiderman"));
        Hero superMan = repository.save(new Hero( "superman"));

        List<Hero> marvellHeroes = repository.findByName("spiderman");

        assertThat(marvellHeroes).containsOnly(spiderMan);
    }


    @Test
    public void find_users_by_name_better() {
        Hero spiderMan = repository.save(namedHero("spiderman"));
        Hero superMan = repository.save(namedHero( "superman"));

        List<Hero> marvellHeroes = repository.findByName("spiderman");

        assertThat(marvellHeroes).containsOnly(spiderMan);
    }

    private Hero namedHero(final String name) {
        return new Hero(name){
            @Override
            public String toString() {
                return name;
            }
        };
    }

}
