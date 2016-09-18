package $11_mocks.bdd;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import domain.Bar;
import domain.Foo;

@RunWith(MockitoJUnitRunner.class)
public class BDDTest {

    @Mock
    Bar bar;

    @InjectMocks
    Foo foo = new Foo();

    @Test
    public void simpleStub() throws Exception {
        given(bar.getName()).willReturn("foobar");

        //execute
        String result = bar.getName();

        assertThat(result, containsString("foo"));
    }

}
