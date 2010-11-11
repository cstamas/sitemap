package org.sonatype.sitemap.record;

public class Attribute
    implements Keyed
{
    private final Uri uri;

    private final String value;

    public Attribute( Uri uri, String value )
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
