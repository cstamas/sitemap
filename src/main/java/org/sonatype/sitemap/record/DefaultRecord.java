package org.sonatype.sitemap.record;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.sonatype.sitemap.Contributor;

public class DefaultRecord
    extends AbstractRecord
{
    private static final long serialVersionUID = 1296325838089071012L;

    private final long lastModified;

    private final long contentLength;

    private final String contentSha1Hash;

    private final String contentMimeType;

    private transient final Map<Contributor, Collection<Attribute>> attributes;

    public DefaultRecord( final Key key, final long lastModified, final long contentLength,
                          final String contentSha1Hash, final String contentMimeType,
                          final Map<Contributor, Collection<Attribute>> attributes )
    {
        super( key );

        this.lastModified = lastModified;

        this.contentLength = contentLength;

        this.contentSha1Hash = contentSha1Hash;

        this.contentMimeType = contentMimeType;

        this.attributes = attributes;
    }

    public String getPath()
    {
        Key key = getKey();

        if ( key instanceof UriKey )
        {
            return ( (UriKey) key ).getLocalName();
        }
        else
        {
            return key.stringValue();
        }
    }

    public long getLastModified()
    {
        return lastModified;
    }

    public long getContentLength()
    {
        return contentLength;
    }

    public String getContentSha1Hash()
    {
        return contentSha1Hash;

    }

    public String getContentMimeType()
    {
        return contentMimeType;
    }

    public Attribute getAttribute( UriKey uri )
    {
        for ( Collection<Attribute> as : attributes.values() )
        {
            for ( Attribute a : as )
            {
                if ( uri.equals( a.getUri() ) )
                {
                    return a;
                }
            }
        }

        return null;
    }

    public Collection<Attribute> getAttributes( Contributor c )
    {
        ArrayList<Attribute> result = new ArrayList<Attribute>();

        if ( attributes.containsKey( c ) )
        {
            result.addAll( attributes.get( c ) );
        }

        return Collections.unmodifiableCollection( result );
    }

    public Collection<Attribute> getAttributes()
    {
        ArrayList<Attribute> result = new ArrayList<Attribute>();

        for ( Collection<Attribute> as : attributes.values() )
        {
            result.addAll( as );
        }

        return Collections.unmodifiableCollection( result );
    }

    // ==

    public String toString()
    {
        return getPath() + " :: " + getContentMimeType() + " @ " + getContentSha1Hash();
    }
}
