package net.skcomms.lucene.ch6;

public class TestSpecialsAccessor implements SpecialsAccessor {

    private final String[] isbns;

    public TestSpecialsAccessor(String[] isbns) {
        this.isbns = isbns;
    }
    @Override
    public String[] isbns() {
        return this.isbns;
    }

}
