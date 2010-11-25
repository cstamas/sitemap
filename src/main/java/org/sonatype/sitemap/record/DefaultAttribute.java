package org.sonatype.sitemap.record;

public class DefaultAttribute
    implements Attribute
{
    private static final long serialVersionUID = -2305182677595725567L;

    private final UriKey uri;

    private final String value;

    public DefaultAttribute( UriKey uri, String value )
    {
        this.uri = uri;

        this.value = value;
    }

    public Key getKey()
    {
        return getUri();
    }

    public UriKey getUri()
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
