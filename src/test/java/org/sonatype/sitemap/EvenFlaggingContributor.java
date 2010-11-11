package org.sonatype.sitemap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.sonatype.sitemap.Contributor;
import org.sonatype.sitemap.io.Content;
import org.sonatype.sitemap.record.Attribute;
import org.sonatype.sitemap.record.DefaultAttribute;
import org.sonatype.sitemap.record.Path;
import org.sonatype.sitemap.record.Record;
import org.sonatype.sitemap.record.StringKey;
import org.sonatype.sitemap.record.Uri;

/**
 * A simple contributor "flagging" every incoming record as "even" or "non-even" (odd), depending on which incoming call
 * was it requested to create attributes for. Just for demo and testing and debugging, and ultimately for fun.
 * 
 * @author cstamas
 */
public class EvenFlaggingContributor
    implements Contributor
{
    private final StringKey namespace = new StringKey( "urn:evenFlaggingContributor#" );

    private final StringKey dummyAttribute = new StringKey( "isEven" );

    private AtomicInteger ai = new AtomicInteger();

    public StringKey getKey()
    {
        return namespace;
    }

    public Collection<Attribute> createAttributesFor( Path key, Content content, Record cr )
    {
        int now = ai.getAndIncrement();

        ArrayList<Attribute> result = new ArrayList<Attribute>();

        result.add( new DefaultAttribute( new Uri( getKey(), dummyAttribute ), String.valueOf( now % 2 == 0 ) ) );

        return result;
    }
}
