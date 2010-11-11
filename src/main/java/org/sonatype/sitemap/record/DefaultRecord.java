package org.sonatype.sitemap.record;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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

    public DefaultRecord( final Path path, final long lastModified, final long contentLength,
                          final String contentSha1Hash, final String contentMimeType,
                          final Map<Contributor, Collection<Attribute>> attributes )
    {
        super( path );

        this.lastModified = lastModified;

        this.contentLength = contentLength;

        this.contentSha1Hash = contentSha1Hash;

        this.contentMimeType = contentMimeType;

        this.attributes = attributes;
    }

    public Path getKey()
    {
        return (Path) super.getKey();
    }

    public String getPath()
    {
        return getKey().getPath();
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

    public Attribute getAttribute( Uri uri )
    {
        return getAttributes().get( uri );
    }

    public Collection<Attribute> getAttributes()
    {
        HashMap<Uri, Attribute> result = new HashMap<Uri, Attribute>( attributes.size() );

        for ( Collection<Attribute> as : attributes.values() )
        {
            for ( Attribute a : as )
            {
                result.put( a.getUri(), a );
            }
        }

        return Collections.unmodifiableCollection( attributes.values() );
    }

    // ==

    public String toString()
    {
        return getPath() + " :: " + getContentMimeType() + " @ " + getContentSha1Hash();
    }
}
