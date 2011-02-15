package tokyotyrant;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class MiscListMatcher extends TypeSafeMatcher<List<byte[]>> {
	private List<byte[]> expected;

	public MiscListMatcher(List<byte[]> expected) {
		this.expected = expected;
	}
	
	@Override
	public boolean matchesSafely(List<byte[]> actual) {
		if (expected.size() != actual.size()) {
			return false;
		}
		for (int i = 0; i < actual.size(); i++) {
			if (!Arrays.equals(expected.get(i), actual.get(i))) {
				return false;
			}
		}
		return true;
	}

	public void describeTo(Description description) {
		description.appendValue(expected);
	}
}
