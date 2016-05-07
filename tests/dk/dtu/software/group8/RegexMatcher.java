package dk.dtu.software.group8;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class RegexMatcher extends BaseMatcher<String>{
    private final String regex;

    /**
     * Created by Morten
     */
    public RegexMatcher(String regex){
        this.regex = regex;
    }

    /**
     * Created by Tobias
     */
    public boolean matches(Object o){
        return ((String)o).matches(regex);

    }

    /**
     * Created by Marcus
     */
    public void describeTo(Description description){
        description.appendText("matches regex=");
    }

    /**
     * Created by Morten
     */
    public static RegexMatcher matches(String regex){
        return new RegexMatcher(regex);
    }
}