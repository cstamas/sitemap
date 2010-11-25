package org.sonatype.sitemap.core;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.sonatype.sitemap.Backend;
import org.sonatype.sitemap.Contributor;
import org.sonatype.sitemap.ContributorKeyProvider;
import org.sonatype.sitemap.Sitemap;
import org.sonatype.sitemap.io.Content;
import org.sonatype.sitemap.io.DigesterUtils;
import org.sonatype.sitemap.io.MimeTyped;
import org.sonatype.sitemap.io.MimeUtils;
import org.sonatype.sitemap.io.Sha1Hashed;
import org.sonatype.sitemap.record.Attribute;
import org.sonatype.sitemap.record.DefaultRecord;
import org.sonatype.sitemap.record.Key;
import org.sonatype.sitemap.record.PathKey;
import org.sonatype.sitemap.record.Record;
import org.sonatype.sitemap.record.UriKey;

public class DefaultSitemap
    implements Sitemap
{
    private final Backend backend;

    private final ContributorKeyProvider contributorKeyProvider;

    private final Map<Key, Contributor> contributors;

    private final Key key;

    public DefaultSitemap( final Backend backend, final ContributorKeyProvider contributorKeyProvider,
                           final Map<Key, Contributor> contributors, final Key key )
    {
        this.backend = backend;

        this.contributorKeyProvider = contributorKeyProvider;

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

    public boolean contains( PathKey PathKey )
    {
        return backend.getMap( getKey() ).containsKey( PathKey );
    }

    public Record get( PathKey key )
    {
        Record cr = (Record) backend.getMap( getKey() ).get( key );

        for ( Contributor c : contributors.values() )
        {
            // TODO!
            backend.getMap( getPartitionKey( c ) ).get( key );
        }

        return cr;
    }

    public boolean put( final PathKey PathKey, final Content content )
    {
        Record rc = createCoreRecord( PathKey, content );

        for ( Contributor c : contributors.values() )
        {
            Collection<Attribute> as = rc.getAttributes( c );

            Map<UriKey, Attribute> map = new HashMap<UriKey, Attribute>();

            for ( Attribute a : as )
            {
                map.put( a.getUri(), a );
            }

            backend.putAll( getPartitionKey( c ), map );
        }

        return backend.put( getKey(), rc.getKey(), rc );
    }

    public int putAll( Map<PathKey, Content> contents )
    {
        int result = 0;

        for ( Map.Entry<PathKey, Content> e : contents.entrySet() )
        {
            if ( put( e.getKey(), e.getValue() ) )
            {
                result++;
            }
        }

        return result;
    }

    public boolean remove( PathKey key )
    {
        return backend.remove( getKey(), key );
    }

    public int removeAll( Collection<PathKey> keys )
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
        return contributorKeyProvider.getContributorKey( getKey(), c );
    }

    protected Record createCoreRecord( final PathKey PathKey, final Content content )
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
                    : MimeUtils.getMimeType( PathKey.getPath() );

            DefaultRecord cr =
                new DefaultRecord( PathKey, content.getLastModified(), content.getLength(), contentSha1Hash,
                    contentMimeType, subRecords );

            for ( Contributor c : contributors.values() )
            {
                Collection<Attribute> sr = c.createAttributesFor( PathKey, cachedContent, cr );

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
