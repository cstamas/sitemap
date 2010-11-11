package org.sonatype.sitemap.record;

public class Path
    extends StringKey
{
    private static final long serialVersionUID = -2126474063805347886L;

    public static final String PATH_SEPARATOR = "/";

    public Path( final String path )
    {
        super( path );

        assert path != null : "Path cannot be null!";
    }

    public String getPath()
    {
        return getValue();
    }
}
