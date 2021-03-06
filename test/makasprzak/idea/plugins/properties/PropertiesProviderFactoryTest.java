package makasprzak.idea.plugins.properties;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesProviderFactoryTest {

    @Mock private PsiClass psiPojo;
    @InjectMocks private PropertiesProviderFactory chooser;

    @Test
    public void shouldChooseFieldInjectionForNoExplicitConstructor_nullArray() throws Exception {
        given(psiPojo.getConstructors()).willReturn(null);
        assertThat(chooser.createFor(psiPojo)).isEqualTo(PropertiesProviderFactories.FROM_SETTERS.get());
    }

    @Test
    public void shouldChooseFieldInjectionForNoExplicitConstructor_emptyArray() throws Exception {
        given(psiPojo.getConstructors()).willReturn(new PsiMethod[]{});
        assertThat(chooser.createFor(psiPojo)).isEqualTo(PropertiesProviderFactories.FROM_SETTERS.get());
    }

    @Test
    public void shouldChooseFieldInjectionForJustDefaultConstructor() throws Exception {
        given(constructor.getParameterList().getParametersCount()).willReturn(0);
        given(psiPojo.getConstructors()).willReturn(new PsiMethod[]{constructor});
        assertThat(chooser.createFor(psiPojo)).isEqualTo(PropertiesProviderFactories.FROM_SETTERS.get());
    }

    @Test
    public void shouldChooseConstructorInjectionForConstructorWithArgs() throws Exception {
        given(constructor.getParameterList().getParametersCount()).willReturn(2);
        given(psiPojo.getConstructors()).willReturn(new PsiMethod[]{constructor});
        assertThat(chooser.createFor(psiPojo)).isEqualTo(PropertiesProviderFactories.FROM_CONSTRUCTOR_ARGS.get());
    }

    @Test
    public void shouldChooseConstructorInjectionForConstructorWithArgsEvenHavingAlsoDefaultOne() throws Exception {
        given(constructor.getParameterList().getParametersCount()).willReturn(0);
        given(anotherConstructor.getParameterList().getParametersCount()).willReturn(2);
        given(psiPojo.getConstructors()).willReturn(new PsiMethod[]{constructor, anotherConstructor});
        assertThat(chooser.createFor(psiPojo)).isEqualTo(PropertiesProviderFactories.FROM_CONSTRUCTOR_ARGS.get());
    }

    @Mock(answer = Answers.RETURNS_DEEP_STUBS) private PsiMethod constructor;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) private PsiMethod anotherConstructor;

}