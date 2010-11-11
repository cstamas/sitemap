package org.sonatype.sitemap;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.sonatype.sitemap.io.Content;
import org.sonatype.sitemap.io.DigesterUtils;
import org.sonatype.sitemap.io.MimeTyped;
import org.sonatype.sitemap.io.MimeUtils;
import org.sonatype.sitemap.io.Sha1Hashed;
import org.sonatype.sitemap.record.Attribute;
import org.sonatype.sitemap.record.CombinationalKey;
import org.sonatype.sitemap.record.DefaultRecord;
import org.sonatype.sitemap.record.Key;
import org.sonatype.sitemap.record.Path;
import org.sonatype.sitemap.record.Record;
import org.sonatype.sitemap.record.Uri;

public class DefaultSitemap
    implements Sitemap
{
    private final Backend backend;

    private final Map<Key, Contributor> contributors;

    private final Key key;

    public DefaultSitemap( final Backend backend, final Map<Key, Contributor> contributors, final Key key )
    {
        this.backend = backend;

        this.contributors = contributors;

        this.key = key;
    }

    public Backend getBackend()
    {
        return backend;
    }

    public Key getKey()
    {
        return key;
    }

    public boolean contains( Path path )
    {
        return backend.getMap( getKey() ).containsKey( path );
    }

    public Record get( Path key )
    {
        Record cr = (Record) backend.getMap( getKey() ).get( key );

        for ( Contributor c : contributors.values() )
        {
            // TODO!
            backend.getMap( getPartitionKey( c ) ).get( key );
        }

        return cr;
    }

    public boolean put( final Path path, final Content content )
    {
        Record rc = createCoreRecord( path, content );

        for ( Contributor c : contributors.values() )
        {
            Collection<Attribute> as = rc.getAttributes( c );

            Map<Uri, Attribute> map = new HashMap<Uri, Attribute>();

            for ( Attribute a : as )
            {
                map.put( a.getUri(), a );
            }

            backend.putAll( getPartitionKey( c ), map );
        }

        return backend.put( getKey(), rc.getKey(), rc );
    }

    public int putAll( Map<Path, Content> contents )
    {
        int result = 0;

        for ( Map.Entry<Path, Content> e : contents.entrySet() )
        {
            if ( put( e.getKey(), e.getValue() ) )
            {
                result++;
            }
        }

        return result;
    }

    public boolean remove( Path key )
    {
        return backend.remove( getKey(), key );
    }

    public int removeAll( Collection<Path> keys )
    {
        return backend.removeAll( getKey(), keys );
    }

    public int getSize()
    {
        return backend.getMap( getKey() ).size();
    }

    public Collection<Record> getRecords()
    {
        // return Collections.unmodifiableCollection( backend.getMap( getKey() ).values() );
        return Collections.emptyList();
    }

    // ==

    protected Key getPartitionKey( Contributor c )
    {
        return new CombinationalKey( getKey(), c.getKey() );
    }

    protected Record createCoreRecord( final Path path, final Content content )
    {
        try
        {
            // cache content if needed
            Content cachedContent;

            if ( content.isReusable() )
            {
                cachedContent = content;
            }
            else
            {
                // File contentFile = File.createTempFile( "sitemap", "cache.tmp" );

                // TODO

                // cachedContent = new FileContent( contentFile );
                cachedContent = content;
            }

            // cruft core record
            HashMap<Contributor, Collection<Attribute>> subRecords = new HashMap<Contributor, Collection<Attribute>>();

            String contentSha1Hash =
                ( content instanceof Sha1Hashed ) ? ( (Sha1Hashed) content ).getSha1Hash()
                    : DigesterUtils.getSha1Digest( cachedContent.getContent() );

            String contentMimeType =
                ( content instanceof MimeTyped ) ? ( (MimeTyped) content ).getMimeType()
                    : MimeUtils.getMimeType( path.getPath() );

            DefaultRecord cr =
                new DefaultRecord( path, content.getLastModified(), content.getLength(), contentSha1Hash,
                    contentMimeType, subRecords );

            for ( Contributor c : contributors.values() )
            {
                Collection<Attribute> sr = c.createAttributesFor( path, cachedContent, cr );

                if ( sr != null )
                {
                    subRecords.put( c, sr );
                }
            }

            return cr;
        }
        catch ( IOException e )
        {
            throw new IllegalStateException( "UUU", e );
        }
    }
}
