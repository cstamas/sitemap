package org.sonatype.sitemap.record;

public class PathKey
    extends StringKey
{
    private static final long serialVersionUID = -2126474063805347886L;

    public static final String PATH_SEPARATOR = "/";

    public PathKey( final String path )
    {
        super( path );

        assert path != null : "Path cannot be null!";
    }

    public String getPath()
    {
        return getValue();
    }
}
