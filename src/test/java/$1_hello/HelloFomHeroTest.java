package $1_hello;

import static org.assertj.core.api.Assertions.*;

import hero.Hero;
import hero.HeroBuilder;
import org.junit.Test;

public class HelloFomHeroTest {

    @Test
    public void says_hello_with_alias() {
        Hero superman = new HeroBuilder().createHero().alias("Superman");

        String helloFromSuperman = superman.sayHello();

        assertThat(helloFromSuperman).isEqualTo("Hello from Superman");
    }

}
