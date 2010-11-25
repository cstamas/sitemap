package org.sonatype.sitemap;

import java.util.Collection;
import java.util.Map;

import org.sonatype.sitemap.io.Content;
import org.sonatype.sitemap.record.Keyed;
import org.sonatype.sitemap.record.PathKey;
import org.sonatype.sitemap.record.Record;

/**
 * The sitemap, that is a "map-like" registry of Records.
 * 
 * @author cstamas
 */
public interface Sitemap
    extends Keyed
{
    boolean contains( PathKey key );

    Record get( PathKey key );

    boolean put( PathKey key, Content content );

    int putAll( Map<PathKey, Content> contents );

    boolean remove( PathKey key );

    int removeAll( Collection<PathKey> keys );

    int getSize();

    Collection<Record> getRecords();
}
