package org.sonatype.sitemap;

import java.util.Collection;

import org.sonatype.sitemap.io.Content;
import org.sonatype.sitemap.record.Attribute;
import org.sonatype.sitemap.record.Keyed;
import org.sonatype.sitemap.record.PathKey;
import org.sonatype.sitemap.record.Record;

/**
 * A "contributor" that is able to decorate the Sitemap core Record with attributes. Every contributor automatically
 * goes into separate "partition" (storage map), and it's up to client to decide would it use it or not.
 * 
 * @author cstamas
 */
public interface Contributor
    extends Keyed
{
    Collection<Attribute> createAttributesFor( PathKey key, Content content, Record cr );
}
