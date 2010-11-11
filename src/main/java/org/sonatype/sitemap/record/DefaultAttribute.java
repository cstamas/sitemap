package org.sonatype.sitemap.record;

public class DefaultAttribute
    implements Attribute
{
    private static final long serialVersionUID = -2305182677595725567L;

    private final Uri uri;

    private final String value;

    public DefaultAttribute( Uri uri, String value )
    {
        this.uri = uri;

        this.value = value;
    }

    public Key getKey()
    {
        return getUri();
    }

    public Uri getUri()
    {
        return uri;
    }

    public String getValue()
    {
        return value;
    }

    // ==

    public String toString()
    {
        return getUri() + " = " + getValue();
    }
}
