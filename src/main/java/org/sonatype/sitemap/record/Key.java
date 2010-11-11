package org.sonatype.sitemap.record;

import java.io.Serializable;

public interface Key
    extends Serializable
{
    int hashCode();

    boolean equals( Object obj );
}
