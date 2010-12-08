package org.sonatype.sitemap;

import java.util.Collection;
import java.util.Map;

import org.sonatype.sitemap.io.Content;
import org.sonatype.sitemap.record.Key;
import org.sonatype.sitemap.record.Keyed;
import org.sonatype.sitemap.record.Record;

/**
 * The sitemap, that is a "map-like" registry of Records.
 * 
 * @author cstamas
 */
public interface Sitemap
    extends Keyed
{
    boolean contains( Key key );

    Record get( Key key );

    boolean put( Key key, Content content );

    int putAll( Map<Key, Content> contents );

    boolean remove( Key key );

    int removeAll( Collection<Key> keys );

    int getSize();

    Collection<Record> getRecords();
}
