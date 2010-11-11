package org.sonatype.sitemap;

import java.util.Collection;

import org.sonatype.sitemap.io.Content;
import org.sonatype.sitemap.record.Attribute;
import org.sonatype.sitemap.record.Keyed;
import org.sonatype.sitemap.record.Path;
import org.sonatype.sitemap.record.Record;

public interface Contributor
    extends Keyed
{
    Collection<Attribute> createAttributesFor( Path key, Content content, Record cr );
}
