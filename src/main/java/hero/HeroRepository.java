package hero;

import static com.google.common.collect.Lists.*;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

public class HeroRepository {

    List<Hero> heroes = newArrayList();

    public List<Hero> fetchAll() {
        return Lists.newArrayList();
    }

    public Hero save(Hero hero) {
        heroes.add(hero);
        return hero;
    }

    public List<Hero> findAllByAlias(String alias) {
        return heroes.stream()
                .filter(hero -> hero.getAlias().equals(alias))
                .collect(Collectors.toList());
    }

    public Hero findByAlias(String alias) {
        return null;
    }
}
