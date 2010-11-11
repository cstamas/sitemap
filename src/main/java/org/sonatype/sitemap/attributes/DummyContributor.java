package org.sonatype.sitemap.attributes;

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

public class DummyContributor
    implements Contributor
{
    private final StringKey namespace = new StringKey( "urn:dummy#" );

    private final StringKey dummyAttribute = new StringKey( "dummyAttribute" );

    private AtomicInteger ai = new AtomicInteger();

    public StringKey getKey()
    {
        return namespace;
    }

    public Collection<Attribute> createAttributesFor( Path key, Content content, Record cr )
    {
        int now = ai.getAndIncrement();

        if ( now % 2 == 0 )
        {
            ArrayList<Attribute> result = new ArrayList<Attribute>();

            result.add( new DefaultAttribute( new Uri( getKey(), dummyAttribute ),
                String.valueOf( System.currentTimeMillis() ) ) );

            return result;
        }
        else
        {
            return null;
        }
    }
}
