package org.sonatype.sitemap.core;

import org.sonatype.sitemap.Contributor;
import org.sonatype.sitemap.ContributorKeyProvider;
import org.sonatype.sitemap.record.CombinationalKey;
import org.sonatype.sitemap.record.Key;

public class DefaultContributorKeyProvider
    implements ContributorKeyProvider
{
    public Key getContributorKey( Key sitemapKey, Contributor contributor )
    {
        return new CombinationalKey( sitemapKey, contributor.getKey() );
    }
}
