package org.sonatype.sitemap;

import org.sonatype.sitemap.record.Key;

public interface ContributorKeyProvider
{
    Key getContributorKey( Key sid, Contributor contributor );
}
