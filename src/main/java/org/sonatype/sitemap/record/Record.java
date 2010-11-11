package org.sonatype.sitemap.record;

import java.io.Serializable;
import java.util.Collection;

import org.sonatype.sitemap.Contributor;

public interface Record
    extends Keyed, Serializable
{
    String getPath();

    long getLastModified();

    long getContentLength();

    String getContentSha1Hash();

    String getContentMimeType();

    Attribute getAttribute( Uri uri );

    Collection<Attribute> getAttributes( Contributor c );

    Collection<Attribute> getAttributes();
}
