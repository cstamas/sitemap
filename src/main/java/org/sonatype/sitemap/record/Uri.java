package org.sonatype.sitemap.record;

public class Uri
    extends CombinationalKey
{
    private static final long serialVersionUID = 5821896654784684306L;

    private static final String SEPARATOR = "#";

    public Uri( final StringKey namespace, final StringKey localName )
    {
        super( namespace, localName );
    }

    public String getNamespace()
    {
        return ( (StringKey) getKeyA() ).getValue();
    }

    public String getLocalName()
    {
        return ( (StringKey) getKeyB() ).getValue();
    }

    // ==

    // example: "urn:maven/artifact#artifactId"
    public static Uri parseUriString( final String uriString )
    {
        assert uriString != null : "URI must be specified (cannot be null)";
        assert uriString.trim().length() > 0 : "URI must be specified (cannot be empty)";

        int localNameIndex = localNameIdx( uriString );

        return new Uri( new StringKey( uriString.substring( 0, localNameIndex ) ), new StringKey(
            uriString.substring( localNameIndex ) ) );
    }

    private static int localNameIdx( final String uri )
    {
        int ide = uri.indexOf( SEPARATOR );

        if ( ide < 0 )
        {
            ide = uri.lastIndexOf( '/' );
        }
        if ( ide < 0 )
        {
            ide = uri.lastIndexOf( ':' );
        }
        if ( ide < 0 )
        {
            throw new IllegalArgumentException( String.format( "Cannot split into namespace and local name: %s", uri ) );
        }
        else
        {
            return ide + 1;
        }
    }

    // ==

    @Override
    public String toString()
    {
        return getNamespace() + getLocalName();
    }
}
