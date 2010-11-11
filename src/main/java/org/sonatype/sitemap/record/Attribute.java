package org.sonatype.sitemap.record;

import java.io.Serializable;

public interface Attribute
    extends Keyed, Serializable
{
    public Uri getUri();

    public String getValue();
}
