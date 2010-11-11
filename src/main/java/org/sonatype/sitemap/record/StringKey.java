package org.sonatype.sitemap.record;

public class StringKey
    extends AbstractKey
{
    private static final long serialVersionUID = 4668677184478267425L;

    private final String value;

    public StringKey( final String value )
    {
        assert value != null : "String value cannot be null!";

        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public int hashCode()
    {
        return getValue().hashCode();
    }

    @Override
    public String toString()
    {
        return getValue();
    }
}
