package org.sonatype.sitemap;

import java.util.Collection;
import java.util.Map;

import org.sonatype.sitemap.io.Content;
import org.sonatype.sitemap.record.Keyed;
import org.sonatype.sitemap.record.Path;
import org.sonatype.sitemap.record.Record;

public interface Sitemap
    extends Keyed
{
    boolean contains( Path key );

    Record get( Path key );

    boolean put( Path key, Content content );

    int putAll( Map<Path, Content> contents );

    boolean remove( Path key );

    int removeAll( Collection<Path> keys );

    int getSize();

    Collection<Record> getRecords();
}
