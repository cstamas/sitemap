package org.sonatype.sitemap.io;

/**
 * Marks an object that is has SHA1 hash available.
 * 
 * @author cstamas
 */
public interface Sha1Hashed
{
    /**
     * Returns the SHA1 hash, as string, hex encoded.
     * 
     * @return
     */
    String getSha1Hash();
}
