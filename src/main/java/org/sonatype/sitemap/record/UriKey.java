package org.sonatype.sitemap.record;

public class UriKey
    extends CombinationalKey
{
    private static final long serialVersionUID = 5821896654784684306L;

    private static final String SEPARATOR = "#";

    public UriKey( final StringKey namespace, final StringKey localName )
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

    @Override
    public String stringValue()
    {
        return getNamespace() + getLocalName();
    }

    // ==

    // example: "urn:maven/artifact#artifactId"
    public static UriKey parseUriString( final String uriString )
    {
        assert uriString != null : "URI must be specified (cannot be null)";
        assert uriString.trim().length() > 0 : "URI must be specified (cannot be empty)";

        int localNameIndex = localNameIdx( uriString );

        return new UriKey( new StringKey( uriString.substring( 0, localNameIndex ) ), new StringKey(
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
}
