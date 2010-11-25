package org.sonatype.sitemap.record;

import java.io.Serializable;

public interface Attribute
    extends Keyed, Serializable
{
    public UriKey getUri();

    public String getValue();
}
